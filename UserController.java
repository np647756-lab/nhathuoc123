package com.hkcare.controller;

import com.hkcare.config.AuthInterceptor;
import com.hkcare.model.ApiResponse;
import com.hkcare.model.NguoiDung;
import com.hkcare.service.AuthService;
import com.hkcare.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Danh sách nhân viên - chỉ Quản trị viên. */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final AuthService auth;

    public UserController(UserService service, AuthService auth) {
        this.service = service; this.auth = auth;
    }

    @GetMapping
    public ApiResponse<List<NguoiDung>> list(HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1);
        return ApiResponse.ok(service.danhSach());
    }

    @GetMapping("/{maND}")
    public ApiResponse<NguoiDung> get(@PathVariable String maND, HttpServletRequest req) {
        AuthInterceptor.yeuCauQuyen(auth, req, 1);
        return ApiResponse.ok(service.tim(maND));
    }
}
