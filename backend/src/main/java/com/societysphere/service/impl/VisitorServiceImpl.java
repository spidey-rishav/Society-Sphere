package com.societysphere.service.impl;

import com.societysphere.dto.visitor.AddDeliveryVisitorRequest;
import com.societysphere.dto.visitor.GuestVerifyRequest;
import com.societysphere.dto.visitor.VisitorResponse;
import com.societysphere.entity.Flat;
import com.societysphere.entity.GuestPreApproval;
import com.societysphere.entity.Notification;
import com.societysphere.entity.Resident;
import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.Visitor;
import com.societysphere.enums.NotificationPriority;
import com.societysphere.enums.NotificationStatus;
import com.societysphere.enums.NotificationType;
import com.societysphere.enums.VisitorStatus;
import com.societysphere.enums.VisitorType;
import com.societysphere.repository.FlatRepository;
import com.societysphere.repository.GuestPreApprovalRepository;
import com.societysphere.repository.NotificationRepository;
import com.societysphere.repository.SecurityGuardRepository;
import com.societysphere.repository.VisitorRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final FlatRepository flatRepository;
    private final SecurityGuardRepository securityGuardRepository;
    private final GuestPreApprovalRepository guestPreApprovalRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public ApiResponse<VisitorResponse> addDeliveryVisitor(AddDeliveryVisitorRequest request, Long guardId) {
        Optional<Flat> flatOpt = flatRepository.findById(request.getFlatId());
        if (flatOpt.isEmpty()) {
            return ApiResponse.error("Flat not found");
        }
        Flat flat = flatOpt.get();

        Optional<SecurityGuard> guardOpt = securityGuardRepository.findById(guardId);
        if (guardOpt.isEmpty()) {
            return ApiResponse.error("Security guard not found");
        }
        SecurityGuard guard = guardOpt.get();

        Visitor visitor = Visitor.builder()
                .society(flat.getSociety())
                .flat(flat)
                .securityGuard(guard)
                .visitorType(VisitorType.DELIVERY)
                .visitorName(request.getVisitorName())
                .mobileNumber(request.getMobileNumber())
                .companyName(request.getCompanyName())
                .vehicleType(request.getVehicleType())
                .vehicleNumber(request.getVehicleNumber())
                .visitorStatus(VisitorStatus.PENDING)
                .build();

        visitor = visitorRepository.save(visitor);

        // Notify residents of the flat
        for (Resident resident : flat.getResidents()) {
            Notification notification = Notification.builder()
                    .society(flat.getSociety())
                    .user(resident.getUser())
                    .notificationType(NotificationType.VISITOR)
                    .title("New Delivery Visitor")
                    .message(request.getVisitorName() + " from " + request.getCompanyName() + " is at the gate.")
                    .priority(NotificationPriority.HIGH)
                    .status(NotificationStatus.UNREAD)
                    .build();
            notificationRepository.save(notification);
        }

        return ApiResponse.success("Delivery visitor added successfully", mapToResponse(visitor));
    }

    @Override
    @Transactional
    public ApiResponse<String> approveVisitor(Long visitorId, boolean approved) {
        Optional<Visitor> visitorOpt = visitorRepository.findById(visitorId);
        if (visitorOpt.isEmpty()) {
            return ApiResponse.error("Visitor not found");
        }
        
        Visitor visitor = visitorOpt.get();
        if (visitor.getVisitorStatus() != VisitorStatus.PENDING) {
            return ApiResponse.error("Visitor is not in pending status");
        }

        if (approved) {
            visitor.setVisitorStatus(VisitorStatus.APPROVED);
            visitor.setEntryTime(LocalDateTime.now());
        } else {
            visitor.setVisitorStatus(VisitorStatus.REJECTED);
        }
        
        visitorRepository.save(visitor);
        
        return ApiResponse.success("Visitor " + (approved ? "approved" : "rejected") + " successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> recordExit(Long visitorId) {
        Optional<Visitor> visitorOpt = visitorRepository.findById(visitorId);
        if (visitorOpt.isEmpty()) {
            return ApiResponse.error("Visitor not found");
        }
        
        Visitor visitor = visitorOpt.get();
        if (visitor.getVisitorType() != VisitorType.DELIVERY) {
            return ApiResponse.error("Exit time is only recorded for delivery visitors");
        }
        if (visitor.getVisitorStatus() != VisitorStatus.APPROVED && visitor.getVisitorStatus() != VisitorStatus.ENTERED) {
             return ApiResponse.error("Visitor has not entered");
        }
        
        visitor.setExitTime(LocalDateTime.now());
        visitorRepository.save(visitor);
        
        return ApiResponse.success("Exit recorded successfully", null);
    }

    @Override
    public ApiResponse<VisitorResponse> verifyGuestBarcode(String barcode) {
        Optional<GuestPreApproval> preApprovalOpt = guestPreApprovalRepository.findAll().stream()
                .filter(p -> p.getBarcode() != null && p.getBarcode().equals(barcode))
                .findFirst();
                
        if (preApprovalOpt.isEmpty()) {
            return ApiResponse.error("Invalid barcode");
        }
        
        GuestPreApproval preApproval = preApprovalOpt.get();
        if (preApproval.getBarcodeExpiryTime() != null && LocalDateTime.now().isAfter(preApproval.getBarcodeExpiryTime())) {
            return ApiResponse.error("Barcode expired");
        }
        
        VisitorResponse response = VisitorResponse.builder()
                .visitorName(preApproval.getGuestName())
                .visitorType(VisitorType.GUEST)
                .flatNumber(preApproval.getResident().getFlat().getFlatNumber())
                .blockName(preApproval.getResident().getFlat().getBlock())
                .build();
                
        return ApiResponse.success("Barcode verified", response);
    }

    @Override
    @Transactional
    public ApiResponse<VisitorResponse> checkInGuest(GuestVerifyRequest request, Long guardId) {
        Optional<GuestPreApproval> preApprovalOpt = guestPreApprovalRepository.findAll().stream()
                .filter(p -> p.getBarcode() != null && p.getBarcode().equals(request.getBarcode()))
                .findFirst();
                
        if (preApprovalOpt.isEmpty()) {
            return ApiResponse.error("Invalid barcode");
        }
        
        GuestPreApproval preApproval = preApprovalOpt.get();
        if (preApproval.getBarcodeExpiryTime() != null && LocalDateTime.now().isAfter(preApproval.getBarcodeExpiryTime())) {
            return ApiResponse.error("Barcode expired");
        }
        
        Optional<SecurityGuard> guardOpt = securityGuardRepository.findById(guardId);
        if (guardOpt.isEmpty()) return ApiResponse.error("Guard not found");

        Visitor visitor = Visitor.builder()
                .society(preApproval.getResident().getFlat().getSociety())
                .flat(preApproval.getResident().getFlat())
                .securityGuard(guardOpt.get())
                .guestPreApproval(preApproval)
                .visitorType(VisitorType.GUEST)
                .visitorName(preApproval.getGuestName())
                .mobileNumber(preApproval.getGuestMobileNumber())
                .vehicleType(request.getVehicleType())
                .vehicleNumber(request.getVehicleNumber())
                .visitorStatus(VisitorStatus.ENTERED)
                .entryTime(LocalDateTime.now())
                .build();
                
        visitor = visitorRepository.save(visitor);
        return ApiResponse.success("Guest checked in", mapToResponse(visitor));
    }

    @Override
    public ApiResponse<List<VisitorResponse>> getVisitorsForFlat(Long flatId) {
        List<VisitorResponse> responses = visitorRepository.findAll().stream()
                .filter(v -> v.getFlat() != null && v.getFlat().getId().equals(flatId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Visitors fetched", responses);
    }

    @Override
    public ApiResponse<List<VisitorResponse>> getAllVisitors(Long societyId) {
        List<VisitorResponse> responses = visitorRepository.findAll().stream()
                .filter(v -> v.getSociety().getId().equals(societyId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("All visitors fetched", responses);
    }

    @Override
    public ApiResponse<List<VisitorResponse>> getPendingApprovals(Long residentId) {
        List<VisitorResponse> responses = visitorRepository.findAll().stream()
                .filter(v -> v.getFlat() != null && v.getFlat().getResidents().stream().anyMatch(r -> r.getId().equals(residentId)))
                .filter(v -> v.getVisitorStatus() == VisitorStatus.PENDING)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Pending approvals fetched", responses);
    }

    private VisitorResponse mapToResponse(Visitor visitor) {
        return VisitorResponse.builder()
                .id(visitor.getId())
                .visitorName(visitor.getVisitorName())
                .visitorType(visitor.getVisitorType())
                .companyName(visitor.getCompanyName())
                .vehicleType(visitor.getVehicleType())
                .vehicleNumber(visitor.getVehicleNumber())
                .visitorStatus(visitor.getVisitorStatus())
                .entryTime(visitor.getEntryTime())
                .exitTime(visitor.getExitTime())
                .flatNumber(visitor.getFlat() != null ? visitor.getFlat().getFlatNumber() : null)
                .blockName(visitor.getFlat() != null ? visitor.getFlat().getBlock() : null)
                .guardName(visitor.getSecurityGuard() != null ? visitor.getSecurityGuard().getFullName() : null)
                .createdAt(visitor.getCreatedAt())
                .build();
    }
}
