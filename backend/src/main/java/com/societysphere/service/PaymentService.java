package com.societysphere.service;

import com.societysphere.dto.payment.CreatePaymentRequest;
import com.societysphere.dto.payment.PaymentResponse;
import com.societysphere.response.ApiResponse;

public interface PaymentService {
    ApiResponse<PaymentResponse> processPayment(CreatePaymentRequest request, String userEmail);
}
