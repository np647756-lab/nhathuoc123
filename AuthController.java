package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.*;
import com.hkcare.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth;

    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest req) {
        return ApiResponse.ok(auth.dangNhap(req.getTenDangNhap(), req.getMatKhau()));
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest req) {
        return ApiResponse.ok(auth.dangKy(req));
    }

    @GetMapping("/me")
    public ApiResponse<AuthResponse> me(HttpServletRequest req) {
        return ApiResponse.ok(auth.layThongTin(AuthInterceptor.token(req)));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest req) {
        auth.dangXuat(AuthInterceptor.token(req));
        return ApiResponse.ok(null);
    }

    @GetMapping("/permissions")
    public ApiResponse<List<PhanQuyen>> permissions() {
        return ApiResponse.ok(auth.danhSachQuyen());
    }

    // ---------- Quản lý tài khoản (chỉ Quản trị viên) ----------
    @GetMapping("/accounts")
    public ApiResponse<List<TaiKhoan>> accounts(HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1);
        return ApiResponse.ok(auth.danhSachTaiKhoan());
    }

    @PatchMapping("/accounts/{maTK}/role")
    public ApiResponse<Void> doiQuyen(@PathVariable String maTK, @RequestBody Map<String, Object> body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1);
        if (laTaiKhoanHienTai(maTK, req)) throw new IllegalArgumentException("Không thể đổi quyền của chính mình");
        if (!(body.get("maQuyen") instanceof Number)) throw new IllegalArgumentException("Thiếu mã quyền");
        auth.capNhatQuyen(maTK, ((Number) body.get("maQuyen")).intValue());
        return ApiResponse.ok(null);
    }

    @PatchMapping("/accounts/{maTK}/status")
    public ApiResponse<Void> doiTrangThai(@PathVariable String maTK, @RequestBody Map<String, Object> body, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1);
        if (!(body.get("trangThai") instanceof Boolean)) throw new IllegalArgumentException("Thiếu trạng thái");
        boolean moi = (Boolean) body.get("trangThai");
        if (!moi && laTaiKhoanHienTai(maTK, req)) throw new IllegalArgumentException("Không thể khóa chính tài khoản đang đăng nhập");
        auth.doiTrangThai(maTK, moi);
        return ApiResponse.ok(null);
    }

    private boolean laTaiKhoanHienTai(String maTK, HttpServletRequest req) {
        String maND = auth.layThongTin(AuthInterceptor.token(req)).getMaND();
        return auth.danhSachTaiKhoan().stream().anyMatch(t -> t.getMaTK().equals(maTK) && t.getMaND().equals(maND));
    }
}
