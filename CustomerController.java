package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.HoaDon;
import com.hkcare.model.KhachHang;
import com.hkcare.service.AuthService;
import com.hkcare.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Quản lý khách hàng: Quản trị viên, Dược sĩ trưởng, Dược sĩ. */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    private final AuthService auth;

    public CustomerController(CustomerService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<KhachHang>> list(@RequestParam(required = false) String q,
                                             @RequestParam(required = false) String phone, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.danhSach(q, phone));
    }

    @PostMapping
    public ApiResponse<KhachHang> create(@RequestBody KhachHang body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.them(body));
    }

    @GetMapping("/{ma}")
    public ApiResponse<KhachHang> get(@PathVariable String ma, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.tim(ma));
    }

    @PutMapping("/{ma}")
    public ApiResponse<KhachHang> update(@PathVariable String ma, @RequestBody KhachHang body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.capNhat(ma, body));
    }

    @DeleteMapping("/{ma}")
    public ApiResponse<Void> delete(@PathVariable String ma, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2);
        service.xoa(ma);
        return ApiResponse.ok(null);
    }

    @GetMapping("/{ma}/history")
    public ApiResponse<List<HoaDon>> history(@PathVariable String ma, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2, 3);
        return ApiResponse.ok(service.lichSuMua(ma));
    }
}
