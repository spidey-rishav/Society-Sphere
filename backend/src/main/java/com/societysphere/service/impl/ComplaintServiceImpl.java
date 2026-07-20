package com.societysphere.service.impl;

import com.societysphere.dto.complaint.ComplaintResponse;
import com.societysphere.dto.complaint.CreateComplaintRequest;
import com.societysphere.dto.complaint.UpdateComplaintRequest;
import com.societysphere.entity.Complaint;
import com.societysphere.entity.Resident;
import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.Society;
import com.societysphere.enums.ComplaintRaisedBy;
import com.societysphere.enums.ComplaintStatus;
import com.societysphere.repository.ComplaintRepository;
import com.societysphere.repository.ResidentRepository;
import com.societysphere.repository.SecurityGuardRepository;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.ComplaintService;
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
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ResidentRepository residentRepository;
    private final SecurityGuardRepository securityGuardRepository;
    private final SocietyRepository societyRepository;

    @Override
    @Transactional
    public ApiResponse<ComplaintResponse> raiseComplaint(CreateComplaintRequest request, String userEmail) {
        Complaint complaint = Complaint.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(ComplaintStatus.PENDING)
                .build();

        // Need to find if userEmail belongs to Resident or SecurityGuard
        // For simplicity, we check Resident first
        Optional<Resident> residentOpt = residentRepository.findAll().stream()
                .filter(r -> r.getUser().getEmail().equals(userEmail))
                .findFirst(); // You'd normally use a proper findByUser_Email query

        if (residentOpt.isPresent()) {
            Resident resident = residentOpt.get();
            complaint.setResident(resident);
            complaint.setFlat(resident.getFlat());
            complaint.setSociety(resident.getFlat().getSociety());
            complaint.setRaisedBy(ComplaintRaisedBy.RESIDENT);
        } else {
            Optional<SecurityGuard> guardOpt = securityGuardRepository.findAll().stream()
                    .filter(g -> g.getUser().getEmail().equals(userEmail))
                    .findFirst();
            if (guardOpt.isPresent()) {
                SecurityGuard guard = guardOpt.get();
                complaint.setSecurityGuard(guard);
                complaint.setSociety(guard.getSociety());
                complaint.setRaisedBy(ComplaintRaisedBy.SECURITY_GUARD);
            } else {
                return ApiResponse.error("User not found as Resident or Guard");
            }
        }

        complaint = complaintRepository.save(complaint);
        return ApiResponse.success("Complaint raised successfully", mapToResponse(complaint));
    }

    @Override
    public ApiResponse<List<ComplaintResponse>> getMyComplaints(String userEmail) {
        // ... Check resident, then guard
        Optional<Resident> residentOpt = residentRepository.findAll().stream()
                .filter(r -> r.getUser().getEmail().equals(userEmail)).findFirst();
        
        List<Complaint> complaints;
        if (residentOpt.isPresent()) {
            complaints = complaintRepository.findByResident(residentOpt.get());
        } else {
            Optional<SecurityGuard> guardOpt = securityGuardRepository.findAll().stream()
                    .filter(g -> g.getUser().getEmail().equals(userEmail)).findFirst();
            if (guardOpt.isPresent()) {
                // Here we'd need findBySecurityGuard, assuming it's filtering by guard for now.
                // Just use findAll and filter for simplicity or add method later
                complaints = complaintRepository.findAll().stream()
                        .filter(c -> c.getSecurityGuard() != null && c.getSecurityGuard().getId().equals(guardOpt.get().getId()))
                        .collect(Collectors.toList());
            } else {
                return ApiResponse.error("User not found");
            }
        }
        
        return ApiResponse.success("Complaints fetched", complaints.stream().map(this::mapToResponse).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<List<ComplaintResponse>> getResidentComplaints(Long societyId) {
        Optional<Society> societyOpt = societyRepository.findById(societyId);
        if (societyOpt.isEmpty()) return ApiResponse.error("Society not found");
        
        List<ComplaintResponse> responses = complaintRepository.findBySociety(societyOpt.get()).stream()
                .filter(c -> c.getRaisedBy() == ComplaintRaisedBy.RESIDENT)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Resident complaints fetched", responses);
    }

    @Override
    public ApiResponse<List<ComplaintResponse>> getGuardComplaints(Long societyId) {
        Optional<Society> societyOpt = societyRepository.findById(societyId);
        if (societyOpt.isEmpty()) return ApiResponse.error("Society not found");
        
        List<ComplaintResponse> responses = complaintRepository.findBySociety(societyOpt.get()).stream()
                .filter(c -> c.getRaisedBy() == ComplaintRaisedBy.SECURITY_GUARD)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success("Guard complaints fetched", responses);
    }

    @Override
    @Transactional
    public ApiResponse<ComplaintResponse> updateComplaint(Long complaintId, UpdateComplaintRequest request) {
        Optional<Complaint> complaintOpt = complaintRepository.findById(complaintId);
        if (complaintOpt.isEmpty()) return ApiResponse.error("Complaint not found");
        
        Complaint complaint = complaintOpt.get();
        if (request.getStatus() != null) complaint.setStatus(request.getStatus());
        if (request.getAdminRemarks() != null) complaint.setAdminRemarks(request.getAdminRemarks());
        if (request.getResolutionSummary() != null) complaint.setResolutionSummary(request.getResolutionSummary());
        if (request.getPaymentRequired() != null) complaint.setPaymentRequired(request.getPaymentRequired());
        if (request.getEstimatedAmount() != null) complaint.setEstimatedAmount(request.getEstimatedAmount());
        
        if (request.getStatus() == ComplaintStatus.RESOLVED || request.getStatus() == ComplaintStatus.CLOSED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        
        complaint = complaintRepository.save(complaint);
        return ApiResponse.success("Complaint updated", mapToResponse(complaint));
    }

    private ComplaintResponse mapToResponse(Complaint complaint) {
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .publicId(complaint.getPublicId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .status(complaint.getStatus())
                .raisedBy(complaint.getRaisedBy())
                .paymentRequired(complaint.getPaymentRequired())
                .estimatedAmount(complaint.getEstimatedAmount())
                .adminRemarks(complaint.getAdminRemarks())
                .resolutionSummary(complaint.getResolutionSummary())
                .resolvedAt(complaint.getResolvedAt())
                .createdAt(complaint.getCreatedAt())
                .flatNumber(complaint.getFlat() != null ? complaint.getFlat().getFlatNumber() : null)
                .blockName(complaint.getFlat() != null ? complaint.getFlat().getBlock() : null)
                .raisedByName(complaint.getResident() != null ? complaint.getResident().getFullName() : 
                        (complaint.getSecurityGuard() != null ? complaint.getSecurityGuard().getFullName() : null))
                .build();
    }
}
