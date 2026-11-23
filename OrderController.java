package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.AuthResponse;
import com.hkcare.model.DonHangOnline;
import com.hkcare.service.AuthService;
import com.hkcare.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** Đơn hàng online: khách hàng và nhân viên xem/tạo; chỉ nhân viên đổi trạng thái. */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final AuthService auth;

    public OrderController(OrderService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<DonHangOnline>> list(HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3, 4);
        AuthResponse me = auth.layThongTin(AuthInterceptor.token(req));
        return ApiResponse.ok(service.danhSach(me.getMaQuyen(), me.getMaND()));
    }

    @PostMapping
    public ApiResponse<DonHangOnline> create(@RequestBody DonHangOnline body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3, 4);
        return ApiResponse.ok(service.tao(body, auth.layThongTin(AuthInterceptor.token(req))));
    }

    @PatchMapping("/{maDon}/status")
    public ApiResponse<Void> status(@PathVariable String maDon, @RequestBody Map<String, String> body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        service.capNhatTrangThai(maDon, body.get("status"));
        return ApiResponse.ok(null);
    }
}
