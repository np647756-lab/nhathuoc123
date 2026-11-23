package com.hkcare.controller;

import com.hkcare.model.ApiResponse;
import com.hkcare.model.Payment;
import com.hkcare.repository.DataStore;
import com.hkcare.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final DataStore db;
    private final ProductService products;

    public StatsController(DataStore db, ProductService products) {
        this.db = db; this.products = products;
    }

    @GetMapping("/dashboard")
    public ApiResponse<Map<String, Object>> dashboard() {
        LocalDate homNay = LocalDate.now();
        long doanhThu = db.payments.stream()
                .filter(p -> "paid".equals(p.getStatus()) && p.getPaidAt() != null && p.getPaidAt().toLocalDate().equals(homNay))
                .mapToLong(Payment::getAmount).sum();

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("totalProducts", db.sanPhams.size());
        m.put("totalCustomers", db.khachHangs.size());
        m.put("totalSuppliers", db.nhaCungCaps.size());
        m.put("totalOrders", db.donHangOnlines.size());
        m.put("totalInvoices", db.hoaDons.size());
        m.put("todayRevenue", doanhThu);
        m.put("lowStockProducts", products.demSapHet());
        return ApiResponse.ok(m);
    }
}
