package com.societysphere.controller;

import com.societysphere.dto.guestpreapproval.GuestPreApprovalResponse;
import com.societysphere.dto.guestpreapproval.GuestPreBookRequest;
import com.societysphere.dto.visitor.GuestVerifyRequest;
import com.societysphere.dto.visitor.VisitorResponse;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.GuestPreApprovalService;
import com.societysphere.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestPreApprovalService guestService;
    private final VisitorService visitorService;

    @PostMapping("/prebook")
    public ApiResponse<GuestPreApprovalResponse> preBookGuest(@RequestBody GuestPreBookRequest request) {
        return guestService.preBookGuest(request);
    }

    @PostMapping("/verify")
    public ApiResponse<VisitorResponse> verifyGuestBarcode(@RequestParam String barcode) {
        return visitorService.verifyGuestBarcode(barcode);
    }

    @PostMapping("/checkin")
    public ApiResponse<VisitorResponse> checkInGuest(@RequestBody GuestVerifyRequest request) {
        Long guardId = 1L; // Placeholder for authenticated guard ID
        return visitorService.checkInGuest(request, guardId);
    }

    @PutMapping("/{id}/approve")
    public ApiResponse<String> approveGuestPreBook(@PathVariable("id") Long id, @RequestParam boolean approved) {
        return guestService.approveGuestPreBook(id, approved);
    }

    @GetMapping("/my")
    public ApiResponse<List<GuestPreApprovalResponse>> getMyGuestPreApprovals() {
        Long residentId = 1L; // Placeholder for authenticated resident ID
        return guestService.getMyGuestPreApprovals(residentId);
    }
}
