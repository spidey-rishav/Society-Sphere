package com.societysphere.controller;

import com.societysphere.dto.visitor.AddDeliveryVisitorRequest;
import com.societysphere.dto.visitor.VisitorResponse;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    // TODO: Extract guardId/residentId from authenticated user context, passing placeholder for now
    
    @PostMapping("/delivery")
    public ApiResponse<VisitorResponse> addDeliveryVisitor(@RequestBody AddDeliveryVisitorRequest request) {
        Long guardId = 1L; // Placeholder for authenticated guard ID
        return visitorService.addDeliveryVisitor(request, guardId);
    }

    @PutMapping("/{id}/approve")
    public ApiResponse<String> approveVisitor(@PathVariable("id") Long id, @RequestParam boolean approved) {
        return visitorService.approveVisitor(id, approved);
    }

    @PutMapping("/{id}/exit")
    public ApiResponse<String> recordExit(@PathVariable("id") Long id) {
        return visitorService.recordExit(id);
    }

    @GetMapping("/flat/{flatId}")
    public ApiResponse<List<VisitorResponse>> getVisitorsForFlat(@PathVariable("flatId") Long flatId) {
        return visitorService.getVisitorsForFlat(flatId);
    }

    @GetMapping("/pending")
    public ApiResponse<List<VisitorResponse>> getPendingApprovals() {
        Long residentId = 1L; // Placeholder for authenticated resident ID
        return visitorService.getPendingApprovals(residentId);
    }

    @GetMapping("/all")
    public ApiResponse<List<VisitorResponse>> getAllVisitors() {
        Long societyId = 1L; // Placeholder for authenticated society ID
        return visitorService.getAllVisitors(societyId);
    }
}
