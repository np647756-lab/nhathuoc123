package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.KhuyenMai;
import com.hkcare.service.AuthService;
import com.hkcare.service.PromotionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    private final PromotionService service;
    private final AuthService auth;

    public PromotionController(PromotionService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<KhuyenMai>> list(HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.danhSach());
    }
}
