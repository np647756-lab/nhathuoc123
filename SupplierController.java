package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.NhaCungCap;
import com.hkcare.model.PhieuNhapKho;
import com.hkcare.service.AuthService;
import com.hkcare.service.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Quản lý nhà cung cấp: Quản trị viên, Dược sĩ trưởng (chỉ Quản trị viên được xóa). */
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService service;
    private final AuthService auth;

    public SupplierController(SupplierService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<NhaCungCap>> list(@RequestParam(required = false) String q, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2);
        return ApiResponse.ok(service.danhSach(q));
    }

    @PostMapping
    public ApiResponse<NhaCungCap> create(@RequestBody NhaCungCap body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2);
        return ApiResponse.ok(service.them(body));
    }

    @GetMapping("/{ma}")
    public ApiResponse<NhaCungCap> get(@PathVariable String ma, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2);
        return ApiResponse.ok(service.tim(ma));
    }

    @PutMapping("/{ma}")
    public ApiResponse<NhaCungCap> update(@PathVariable String ma, @RequestBody NhaCungCap body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2);
        return ApiResponse.ok(service.capNhat(ma, body));
    }

    @DeleteMapping("/{ma}")
    public ApiResponse<Void> delete(@PathVariable String ma, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1);
        service.xoa(ma);
        return ApiResponse.ok(null);
    }

    @GetMapping("/{ma}/history")
    public ApiResponse<List<PhieuNhapKho>> history(@PathVariable String ma, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1, 2);
        return ApiResponse.ok(service.lichSuNhap(ma));
    }
}
