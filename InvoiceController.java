package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.HoaDon;
import com.hkcare.service.AuthService;
import com.hkcare.service.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Hóa đơn bán tại quầy: Quản trị viên, Dược sĩ trưởng, Dược sĩ. */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService service;
    private final AuthService auth;

    public InvoiceController(InvoiceService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<HoaDon>> list(HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.danhSach());
    }

    @PostMapping
    public ApiResponse<HoaDon> create(@RequestBody HoaDon body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        String maNV = auth.layThongTin(AuthInterceptor.token(req)).getMaND();   // nhân viên lấy từ phiên đăng nhập
        return ApiResponse.ok(service.tao(body, maNV));
    }
}
