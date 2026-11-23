package com.hkcare.service;

import com.hkcare.model.ChiTietHoaDon;
import com.hkcare.model.HoaDon;
import com.hkcare.model.KhachHang;
import com.hkcare.model.SanPham;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/** Hóa đơn bán tại quầy (POS - offline). */
@Service
public class InvoiceService {
    private static final Set<String> HINH_THUC = Set.of("cash", "qr", "bank");
    private final DataStore db;
    private final ProductService products;

    public InvoiceService(DataStore db, ProductService products) {
        this.db = db; this.products = products;
    }

    public List<HoaDon> danhSach() { return new ArrayList<>(db.hoaDons); }

    public synchronized HoaDon tao(HoaDon req, String maNV) {
        if (req.getChiTiet() == null || req.getChiTiet().isEmpty())
            throw new IllegalArgumentException("Hóa đơn rỗng");
        String ht = req.getHinhThucThanhToan() == null ? "cash" : req.getHinhThucThanhToan();
        if (!HINH_THUC.contains(ht)) throw new IllegalArgumentException("Hình thức thanh toán không hợp lệ");

        Map<String, Integer> yeuCau = new LinkedHashMap<>();
        List<ChiTietHoaDon> dong = new ArrayList<>();
        long tong = 0;
        for (ChiTietHoaDon c : req.getChiTiet()) {
            if (c.getSoLuong() <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
            SanPham p = products.tim(c.getMaSP());           // giá lấy từ máy chủ, không tin giá client
            ChiTietHoaDon d = new ChiTietHoaDon();
            d.setMaSP(p.getMaSP()); d.setSoLuong(c.getSoLuong());
            d.setDonGia(p.getGiaBan()); d.setThanhTien(p.getGiaBan() * c.getSoLuong());
            dong.add(d);
            tong += d.getThanhTien();
            yeuCau.merge(p.getMaSP(), c.getSoLuong(), Integer::sum);
        }
        products.truTon(yeuCau);

        HoaDon h = new HoaDon();
        h.setMaHD(DataStore.taoMa("HD", 4, db.hoaDons.stream().map(HoaDon::getMaHD)));
        h.setMaNV(maNV);
        h.setMaKH(req.getMaKH());
        h.setNgayLap(LocalDateTime.now());
        h.setTongTien(tong);
        h.setHinhThucThanhToan(ht);
        h.setTrangThai("Hoàn tất");
        h.setChiTiet(dong);
        db.hoaDons.add(h);

        // tích điểm: 10.000đ = 1 điểm
        if (h.getMaKH() != null) {
            for (KhachHang k : db.khachHangs)
                if (k.getMaKH().equals(h.getMaKH())) { k.setDiemTichLuy(k.getDiemTichLuy() + (int) (tong / 10_000)); break; }
        }
        return h;
    }
}
