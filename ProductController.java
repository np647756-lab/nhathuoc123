package com.hkcare.controller;

import com.hkcare.model.ApiResponse;
import com.hkcare.model.SanPham;
import com.hkcare.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<SanPham>> list(@RequestParam(required = false) String q,
                                           @RequestParam(required = false) String group) {
        return ApiResponse.ok(service.danhSach(q, group));
    }

    @GetMapping("/groups")
    public ApiResponse<List<Map<String, Object>>> groups() {
        return ApiResponse.ok(service.thongKeNhom());
    }

    @GetMapping("/{maSP}")
    public ApiResponse<SanPham> get(@PathVariable String maSP) {
        return ApiResponse.ok(service.tim(maSP));
    }
}
