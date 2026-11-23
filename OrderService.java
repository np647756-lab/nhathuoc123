package com.hkcare.service;

import com.hkcare.model.*;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/** Đơn hàng online. */
@Service
public class OrderService {
    public static final List<String> TRANG_THAI = List.of("Chờ xác nhận", "Đã xác nhận", "Đang giao", "Hoàn tất", "Hủy");
    private final DataStore db;
    private final ProductService products;

    public OrderService(DataStore db, ProductService products) {
        this.db = db; this.products = products;
    }

    /** Khách hàng (quyền 4) chỉ thấy đơn của mình; nhân viên thấy tất cả. */
    public List<DonHangOnline> danhSach(int maQuyen, String maND) {
        return db.donHangOnlines.stream()
                .filter(o -> maQuyen != 4 || maND.equals(o.getMaKH()))
                .collect(Collectors.toList());
    }

    public synchronized DonHangOnline tao(DonHangOnline req, AuthResponse nguoiDat) {
        if (req.getChiTiet() == null || req.getChiTiet().isEmpty())
            throw new IllegalArgumentException("Đơn hàng rỗng");
        if (req.getDiaChiGiao() == null || req.getDiaChiGiao().isBlank())
            throw new IllegalArgumentException("Nhập địa chỉ giao hàng");

        String maKH = nguoiDat.getMaQuyen() == 4 ? nguoiDat.getMaND() : req.getMaKH();
        if (maKH == null || db.khachHangs.stream().noneMatch(k -> k.getMaKH().equals(maKH)))
            throw new IllegalArgumentException("Không tìm thấy khách hàng");

        Map<String, Integer> yeuCau = new LinkedHashMap<>();
        List<ChiTietDonHangOnline> dong = new ArrayList<>();
        long tong = 0;
        for (ChiTietDonHangOnline c : req.getChiTiet()) {
            if (c.getSoLuong() <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
            SanPham p = products.tim(c.getMaSP());
            ChiTietDonHangOnline d = new ChiTietDonHangOnline();
            d.setMaSP(p.getMaSP()); d.setSoLuong(c.getSoLuong());
            d.setDonGia(p.getGiaBan()); d.setThanhTien(p.getGiaBan() * c.getSoLuong());
            dong.add(d);
            tong += d.getThanhTien();
            yeuCau.merge(p.getMaSP(), c.getSoLuong(), Integer::sum);
        }
        products.truTon(yeuCau);

        DonHangOnline o = new DonHangOnline();
        o.setMaDon(DataStore.taoMa("ONL", 4, db.donHangOnlines.stream().map(DonHangOnline::getMaDon)));
        o.setMaKH(maKH);
        o.setMaND(nguoiDat.getMaND());
        o.setDiaChiGiao(req.getDiaChiGiao().trim());
        o.setPaymentId(req.getPaymentId());
        o.setThanhTien(tong);
        o.setTrangThai("Chờ xác nhận");
        o.setNgayDat(LocalDateTime.now());
        o.setChiTiet(dong);
        db.donHangOnlines.add(o);
        return o;
    }

    public synchronized void capNhatTrangThai(String maDon, String status) {
        if (status == null || !TRANG_THAI.contains(status))
            throw new IllegalArgumentException("Trạng thái không hợp lệ");
        DonHangOnline o = db.donHangOnlines.stream().filter(x -> x.getMaDon().equals(maDon)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn"));
        if (o.getTrangThai().equals("Hoàn tất") || o.getTrangThai().equals("Hủy"))
            throw new IllegalArgumentException("Đơn đã kết thúc, không thể đổi trạng thái");

        if (status.equals("Hủy")) {                       // hoàn lại tồn kho
            Map<String, Integer> hoan = new LinkedHashMap<>();
            o.getChiTiet().forEach(c -> hoan.merge(c.getMaSP(), c.getSoLuong(), Integer::sum));
            products.hoanTon(hoan);
        }
        if (status.equals("Hoàn tất")) {                  // tích điểm: 10.000đ = 1 điểm
            db.khachHangs.stream().filter(k -> k.getMaKH().equals(o.getMaKH())).findFirst()
                    .ifPresent(k -> k.setDiemTichLuy(k.getDiemTichLuy() + (int) (o.getThanhTien() / 10_000)));
        }
        o.setTrangThai(status);
    }
}
