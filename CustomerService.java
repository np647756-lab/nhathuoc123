package com.hkcare.service;

import com.hkcare.model.HoaDon;
import com.hkcare.model.KhachHang;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final DataStore db;

    public CustomerService(DataStore db) { this.db = db; }

    private static String lc(String s) { return s == null ? "" : s.toLowerCase(Locale.ROOT); }
    private static boolean co(String s) { return s != null && !s.isBlank(); }

    public List<KhachHang> danhSach(String q, String phone) {
        String k = lc(q).trim();
        return db.khachHangs.stream()
                .filter(c -> !co(phone) || (c.getSdt() != null && c.getSdt().contains(phone.trim())))
                .filter(c -> k.isEmpty() || lc(c.getHoTen()).contains(k) || lc(c.getSdt()).contains(k)
                        || lc(c.getMaKH()).contains(k) || lc(c.getEmail()).contains(k))
                .collect(Collectors.toList());
    }

    public KhachHang tim(String maKH) {
        return db.khachHangs.stream().filter(c -> c.getMaKH().equals(maKH)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));
    }

    public synchronized KhachHang them(KhachHang req) {
        if (!co(req.getHoTen())) throw new IllegalArgumentException("Họ tên không được trống");
        if (!co(req.getSdt())) throw new IllegalArgumentException("SĐT không được trống");
        if (db.khachHangs.stream().anyMatch(c -> req.getSdt().trim().equals(c.getSdt())))
            throw new IllegalArgumentException("SĐT đã tồn tại");
        KhachHang c = new KhachHang();
        c.setMaKH(DataStore.taoMa("KH", 4, db.khachHangs.stream().map(KhachHang::getMaKH)));
        c.setHoTen(req.getHoTen().trim());
        c.setSdt(req.getSdt().trim());
        c.setGioiTinh(req.getGioiTinh());
        c.setEmail(req.getEmail());
        c.setDiemTichLuy(0);
        db.khachHangs.add(c);
        return c;
    }

    public synchronized KhachHang capNhat(String maKH, KhachHang req) {
        KhachHang c = tim(maKH);
        if (co(req.getSdt()) && !req.getSdt().trim().equals(c.getSdt())
                && db.khachHangs.stream().anyMatch(k -> req.getSdt().trim().equals(k.getSdt())))
            throw new IllegalArgumentException("SĐT đã tồn tại");
        if (co(req.getHoTen())) c.setHoTen(req.getHoTen().trim());
        if (co(req.getSdt())) c.setSdt(req.getSdt().trim());
        if (co(req.getGioiTinh())) c.setGioiTinh(req.getGioiTinh());
        if (co(req.getEmail())) c.setEmail(req.getEmail());
        return c;
    }

    public synchronized void xoa(String maKH) {
        KhachHang c = tim(maKH);
        long n = db.hoaDons.stream().filter(h -> maKH.equals(h.getMaKH())).count()
                + db.donHangOnlines.stream().filter(o -> maKH.equals(o.getMaKH())).count();
        if (n > 0) throw new IllegalArgumentException("Không thể xóa: KH có " + n + " giao dịch");
        if (db.taiKhoans.stream().anyMatch(t -> t.getMaQuyen() == 4 && maKH.equals(t.getMaND())))
            throw new IllegalArgumentException("Không thể xóa: KH đang có tài khoản đăng nhập");
        db.khachHangs.remove(c);
    }

    public List<HoaDon> lichSuMua(String maKH) {
        tim(maKH);
        return db.hoaDons.stream().filter(h -> maKH.equals(h.getMaKH())).collect(Collectors.toList());
    }
}
