package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.Payment;
import com.hkcare.service.AuthService;
import com.hkcare.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    private final AuthService auth;

    public PaymentController(PaymentService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<Payment>> list(HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.danhSach());
    }

    @PostMapping
    public ApiResponse<Payment> create(@RequestBody Payment body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3, 4);
        return ApiResponse.ok(service.tao(body));
    }

    @PostMapping("/{id}/confirm")
    public ApiResponse<Void> confirm(@PathVariable String id, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3, 4);
        service.xacNhan(id);
        return ApiResponse.ok(null);
    }
}
