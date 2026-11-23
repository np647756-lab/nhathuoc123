package com.hkcare.service;

import com.hkcare.model.SanPham;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    public static final int NGUONG_SAP_HET = 20;
    private final DataStore db;

    public ProductService(DataStore db) { this.db = db; }

    private static String lc(String s) { return s == null ? "" : s.toLowerCase(Locale.ROOT); }

    public List<SanPham> danhSach(String q, String nhom) {
        String k = lc(q).trim();
        return db.sanPhams.stream()
                .filter(p -> k.isEmpty() || lc(p.getTenThuoc()).contains(k) || lc(p.getMaSP()).contains(k))
                .filter(p -> nhom == null || nhom.isBlank() || nhom.equals(p.getMaDM()))
                .collect(Collectors.toList());
    }

    public SanPham tim(String maSP) {
        return db.sanPhams.stream().filter(p -> p.getMaSP().equals(maSP)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm " + maSP));
    }

    /** Thống kê theo nhóm hàng: {group, maDM, count, totalStock, totalValue, lowStock}. */
    public List<Map<String, Object>> thongKeNhom() {
        List<Map<String, Object>> out = new ArrayList<>();
        db.danhMucs.forEach(dm -> {
            List<SanPham> l = db.sanPhams.stream().filter(p -> dm.getMaDM().equals(p.getMaDM())).collect(Collectors.toList());
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("group", dm.getTenDM());
            m.put("maDM", dm.getMaDM());
            m.put("count", l.size());
            m.put("totalStock", l.stream().mapToInt(SanPham::getTonKho).sum());
            m.put("totalValue", l.stream().mapToLong(p -> p.getGiaBan() * p.getTonKho()).sum());
            m.put("lowStock", l.stream().filter(p -> p.getTonKho() < NGUONG_SAP_HET).count());
            out.add(m);
        });
        return out;
    }

    public long demSapHet() {
        return db.sanPhams.stream().filter(p -> p.getTonKho() < NGUONG_SAP_HET).count();
    }

    /** Trừ tồn kho cho nhiều sản phẩm cùng lúc: hoặc trừ hết, hoặc không trừ gì nếu có SP thiếu hàng. */
    public synchronized void truTon(Map<String, Integer> yeuCau) {
        for (Map.Entry<String, Integer> e : yeuCau.entrySet()) {
            SanPham p = tim(e.getKey());
            if (p.getTonKho() < e.getValue())
                throw new IllegalArgumentException("Không đủ tồn kho: " + p.getTenThuoc() + " (còn " + p.getTonKho() + ")");
        }
        yeuCau.forEach((ma, sl) -> { SanPham p = tim(ma); p.setTonKho(p.getTonKho() - sl); });
    }

    public synchronized void hoanTon(Map<String, Integer> yeuCau) {
        yeuCau.forEach((ma, sl) -> db.sanPhams.stream().filter(p -> p.getMaSP().equals(ma)).findFirst()
                .ifPresent(p -> p.setTonKho(p.getTonKho() + sl)));
    }
}
