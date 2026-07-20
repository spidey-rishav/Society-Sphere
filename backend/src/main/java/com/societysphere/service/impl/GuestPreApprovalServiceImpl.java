package com.societysphere.service.impl;

import com.societysphere.dto.guestpreapproval.GuestPreApprovalResponse;
import com.societysphere.dto.guestpreapproval.GuestPreBookRequest;
import com.societysphere.entity.Flat;
import com.societysphere.entity.GuestPreApproval;
import com.societysphere.entity.Notification;
import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.enums.GuestApprovalStatus;
import com.societysphere.enums.NotificationPriority;
import com.societysphere.enums.NotificationStatus;
import com.societysphere.enums.NotificationType;
import com.societysphere.repository.FlatRepository;
import com.societysphere.repository.GuestPreApprovalRepository;
import com.societysphere.repository.NotificationRepository;
import com.societysphere.repository.ResidentRepository;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.GuestPreApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestPreApprovalServiceImpl implements GuestPreApprovalService {

    private final GuestPreApprovalRepository guestPreApprovalRepository;
    private final SocietyRepository societyRepository;
    private final ResidentRepository residentRepository;
    private final FlatRepository flatRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public ApiResponse<GuestPreApprovalResponse> preBookGuest(GuestPreBookRequest request) {
        Optional<Society> societyOpt = societyRepository.findBySocietyCode(request.getSocietyCode());
        if (societyOpt.isEmpty()) {
            return ApiResponse.error("Society not found");
        }
        Society society = societyOpt.get();

        // Finding resident by flatNumber and society
        Optional<Flat> flatOpt = flatRepository.findBySocietyAndFlatNumber(society, request.getFlatNumber());
        if (flatOpt.isEmpty()) {
            return ApiResponse.error("Flat not found");
        }
        Flat flat = flatOpt.get();
        List<Resident> residents = residentRepository.findByFlat(flat);
        if (residents.isEmpty()) {
            return ApiResponse.error("No residents found for this flat");
        }
        Resident resident = residents.get(0); // Assigning to first resident

        GuestPreApproval preApproval = GuestPreApproval.builder()
                .resident(resident)
                .guestName(request.getGuestName())
                .guestMobileNumber(request.getGuestMobileNumber())
                .purpose(request.getPurpose())
                .expectedArrivalTime(request.getExpectedArrivalTime())
                .approvalStatus(GuestApprovalStatus.PENDING)
                .barcode(UUID.randomUUID().toString()) // Initial placeholder or actual barcode
                .build();
        
        preApproval = guestPreApprovalRepository.save(preApproval);

        return ApiResponse.success("Guest pre-booking successful, pending resident approval", mapToResponse(preApproval));
    }

    @Override
    @Transactional
    public ApiResponse<String> approveGuestPreBook(Long preApprovalId, boolean approved) {
        Optional<GuestPreApproval> preApprovalOpt = guestPreApprovalRepository.findById(preApprovalId);
        if (preApprovalOpt.isEmpty()) {
            return ApiResponse.error("Guest pre-approval not found");
        }
        
        GuestPreApproval preApproval = preApprovalOpt.get();
        
        if (approved) {
            preApproval.setApprovalStatus(GuestApprovalStatus.APPROVED);
            preApproval.setBarcode(UUID.randomUUID().toString()); // Generate actual barcode
            preApproval.setBarcodeGeneratedAt(LocalDateTime.now());
            preApproval.setBarcodeExpiryTime(LocalDateTime.now().plusHours(24));
            
            // Note: In real world, send SMS to guest with this barcode
        } else {
            preApproval.setApprovalStatus(GuestApprovalStatus.REJECTED);
        }
        
        guestPreApprovalRepository.save(preApproval);
        
        return ApiResponse.success("Guest pre-booking " + (approved ? "approved" : "rejected") + " successfully", null);
    }

    @Override
    public ApiResponse<List<GuestPreApprovalResponse>> getMyGuestPreApprovals(Long residentId) {
        Optional<Resident> residentOpt = residentRepository.findById(residentId);
        if (residentOpt.isEmpty()) {
            return ApiResponse.error("Resident not found");
        }
        
        List<GuestPreApproval> approvals = guestPreApprovalRepository.findByResident(residentOpt.get());
        List<GuestPreApprovalResponse> responses = approvals.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
                
        return ApiResponse.success("Guest pre-approvals fetched", responses);
    }
    
    private GuestPreApprovalResponse mapToResponse(GuestPreApproval approval) {
        return GuestPreApprovalResponse.builder()
                .id(approval.getId())
                .guestName(approval.getGuestName())
                .guestMobileNumber(approval.getGuestMobileNumber())
                .purpose(approval.getPurpose())
                .expectedArrivalTime(approval.getExpectedArrivalTime())
                .approvalStatus(approval.getApprovalStatus())
                .barcode(approval.getBarcode())
                .barcodeExpiryTime(approval.getBarcodeExpiryTime())
                .build();
    }
}
