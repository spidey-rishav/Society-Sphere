package com.societysphere.controller;
import com.societysphere.dto.society.SocietyRegistrationRequest;
import com.societysphere.entity.Society;
import com.societysphere.enums.RegistrationStatus;
import com.societysphere.exception.BadRequestException;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/societies") @RequiredArgsConstructor
public class SocietyRegistrationController {
 private final SocietyRepository societies;
 @GetMapping("/search") public ApiResponse<List<Map<String,String>>> search() {
  List<Map<String,String>> results=societies.findAll().stream()
    .filter(s -> Boolean.TRUE.equals(s.getActive()) && s.getRegistrationStatus() == RegistrationStatus.APPROVED)
    .map(s -> Map.of("name", s.getSocietyName(), "code", s.getSocietyCode()))
    .toList();
  return ApiResponse.success("Active societies retrieved", results);
 }
 @PostMapping("/registration-request") public ApiResponse<Map<String,String>> submit(@Valid @RequestBody SocietyRegistrationRequest r) {
  String email=r.getAdminEmail().trim().toLowerCase(Locale.ROOT);
  if(societies.existsByEmail(email)) throw new BadRequestException("A registration already exists for this admin email");
  Society s=Society.builder().societyName(r.getSocietyName().trim()).societyCode("SOC-"+UUID.randomUUID().toString().substring(0,8).toUpperCase()).registrationNumber(r.getRegistrationNumber().trim()).email(email).phoneNumber(r.getAdminMobile()).addressLine1(r.getAddress()).city(Optional.ofNullable(r.getCity()).orElse("Not provided")).state(Optional.ofNullable(r.getState()).orElse("Not provided")).country("India").pincode(Optional.ofNullable(r.getPincode()).orElse("000000")).totalFlats(0).active(false).applicantAdminName(r.getAdminName().trim()).registrationStatus(RegistrationStatus.PENDING).build();
  societies.save(s); return ApiResponse.success("Registration request submitted for approval",Map.of("societyCode",s.getSocietyCode()));
 }
}
