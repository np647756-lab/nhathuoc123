package com.hkcare.service;

import com.hkcare.model.NhaCungCap;
import com.hkcare.model.PhieuNhapKho;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    private final DataStore db;

    public SupplierService(DataStore db) { this.db = db; }

    private static String lc(String s) { return s == null ? "" : s.toLowerCase(Locale.ROOT); }
    private static boolean co(String s) { return s != null && !s.isBlank(); }

    public List<NhaCungCap> danhSach(String q) {
        String k = lc(q).trim();
        return db.nhaCungCaps.stream()
                .filter(n -> k.isEmpty() || lc(n.getTenNCC()).contains(k) || lc(n.getMaNCC()).contains(k)
                        || lc(n.getSdt()).contains(k) || lc(n.getEmail()).contains(k))
                .collect(Collectors.toList());
    }

    public NhaCungCap tim(String maNCC) {
        return db.nhaCungCaps.stream().filter(n -> n.getMaNCC().equals(maNCC)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy NCC"));
    }

    public synchronized NhaCungCap them(NhaCungCap req) {
        if (!co(req.getTenNCC())) throw new IllegalArgumentException("Tên NCC không được trống");
        NhaCungCap n = new NhaCungCap();
        n.setMaNCC(DataStore.taoMa("NCC", 2, db.nhaCungCaps.stream().map(NhaCungCap::getMaNCC)));
        n.setTenNCC(req.getTenNCC().trim());
        n.setMaSoThue(req.getMaSoThue());
        n.setDiaChi(req.getDiaChi());
        n.setSdt(req.getSdt());
        n.setEmail(req.getEmail());
        n.setGhiChu(req.getGhiChu());
        n.setCongNo(0);
        db.nhaCungCaps.add(n);
        return n;
    }

    public synchronized NhaCungCap capNhat(String maNCC, NhaCungCap req) {
        NhaCungCap n = tim(maNCC);
        if (co(req.getTenNCC())) n.setTenNCC(req.getTenNCC().trim());
        if (co(req.getMaSoThue())) n.setMaSoThue(req.getMaSoThue());
        if (co(req.getDiaChi())) n.setDiaChi(req.getDiaChi());
        if (co(req.getSdt())) n.setSdt(req.getSdt());
        if (co(req.getEmail())) n.setEmail(req.getEmail());
        if (co(req.getGhiChu())) n.setGhiChu(req.getGhiChu());
        return n;
    }

    public synchronized void xoa(String maNCC) {
        NhaCungCap n = tim(maNCC);
        long sp = db.sanPhams.stream().filter(s -> maNCC.equals(s.getMaNCC())).count();
        if (sp > 0) throw new IllegalArgumentException("Không thể xóa: NCC đang cung cấp " + sp + " sản phẩm");
        if (db.taiKhoans.stream().anyMatch(t -> t.getMaQuyen() == 5 && maNCC.equals(t.getMaND())))
            throw new IllegalArgumentException("Không thể xóa: NCC đang có tài khoản đăng nhập");
        db.nhaCungCaps.remove(n);
    }

    /** Lịch sử giao dịch = các phiếu nhập kho từ NCC này. */
    public List<PhieuNhapKho> lichSuNhap(String maNCC) {
        tim(maNCC);
        return db.phieuNhaps.stream().filter(p -> maNCC.equals(p.getMaNCC())).collect(Collectors.toList());
    }
}
