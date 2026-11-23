package com.hkcare.service;

import com.hkcare.model.*;
import com.hkcare.repository.DataStore;
import com.hkcare.repository.SessionStore;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AuthService {
    private final DataStore db;
    private final SessionStore sessions;

    public AuthService(DataStore db, SessionStore sessions) {
        this.db = db; this.sessions = sessions;
    }

    private static String nz(String s) { return s == null ? "" : s; }

    private String tenQuyen(int maQuyen) {
        return db.phanQuyens.stream().filter(p -> p.getMaQuyen() == maQuyen)
                .findFirst().map(PhanQuyen::getTenQuyen).orElse("");
    }

    /** Trả về {hoTen, email, loại} theo nhóm quyền: 1-3 nhân viên, 4 khách hàng, 5 nhà cung cấp. */
    private String[] thongTin(String maND, int maQuyen) {
        if (maQuyen == 4) {
            return db.khachHangs.stream().filter(k -> k.getMaKH().equals(maND)).findFirst()
                    .map(k -> new String[]{nz(k.getHoTen()), nz(k.getEmail()), "KhachHang"})
                    .orElse(new String[]{"Khách hàng", "", "KhachHang"});
        }
        if (maQuyen == 5) {
            return db.nhaCungCaps.stream().filter(n -> n.getMaNCC().equals(maND)).findFirst()
                    .map(n -> new String[]{nz(n.getTenNCC()), nz(n.getEmail()), "NhaCungCap"})
                    .orElse(new String[]{"Nhà cung cấp", "", "NhaCungCap"});
        }
        return db.nguoiDungs.stream().filter(u -> u.getMaND().equals(maND)).findFirst()
                .map(u -> new String[]{nz(u.getHoTen()), nz(u.getEmail()), u.getClass().getSimpleName()})
                .orElse(new String[]{"Người dùng", "", "User"});
    }

    public AuthResponse dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || matKhau == null)
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ");
        TaiKhoan tk = db.taiKhoans.stream().filter(t -> t.getTenDangNhap().equals(tenDangNhap)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tên đăng nhập không tồn tại"));
        if (!tk.isTrangThai()) throw new IllegalArgumentException("Tài khoản đã bị khóa");
        if (!tk.getMatKhau().equals(matKhau)) throw new IllegalArgumentException("Mật khẩu không đúng");

        tk.setLanDangNhapCuoi(LocalDateTime.now());
        SessionStore.Session s = sessions.taoSession(tk.getMaND(), tk.getMaTK(), tk.getMaQuyen());
        String[] info = thongTin(tk.getMaND(), tk.getMaQuyen());
        return new AuthResponse(s.token, tk.getMaND(), info[0], info[1], info[2], tk.getMaQuyen(), tenQuyen(tk.getMaQuyen()));
    }

    public AuthResponse dangKy(RegisterRequest req) {
        if (req.getTenDangNhap() == null || req.getTenDangNhap().isEmpty())
            throw new IllegalArgumentException("Tên đăng nhập không được trống");
        if (req.getMatKhau() == null || req.getMatKhau().length() < 6)
            throw new IllegalArgumentException("Mật khẩu phải ≥ 6 ký tự");
        if (db.taiKhoans.stream().anyMatch(t -> t.getTenDangNhap().equals(req.getTenDangNhap())))
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");

        int maQuyen = req.getMaQuyen() > 0 ? req.getMaQuyen() : 3;
        if (maQuyen > 5) throw new IllegalArgumentException("Vai trò không hợp lệ");
        String maND;

        if (maQuyen == 4) {
            String sdt = nz(req.getSdt());
            if (!sdt.isEmpty() && db.khachHangs.stream().anyMatch(k -> sdt.equals(k.getSdt())))
                throw new IllegalArgumentException("SĐT đã tồn tại");
            KhachHang kh = new KhachHang();
            maND = nextId("KH", 4, id -> db.khachHangs.stream().anyMatch(k -> k.getMaKH().equals(id)), db.khachHangs.size());
            kh.setMaKH(maND); kh.setHoTen(req.getHoTen()); kh.setSdt(sdt);
            kh.setGioiTinh(req.getGioiTinh()); kh.setEmail(req.getEmail()); kh.setDiemTichLuy(0);
            db.khachHangs.add(kh);
        } else if (maQuyen == 5) {
            NhaCungCap n = new NhaCungCap();
            maND = nextId("NCC", 2, id -> db.nhaCungCaps.stream().anyMatch(x -> x.getMaNCC().equals(id)), db.nhaCungCaps.size());
            n.setMaNCC(maND); n.setTenNCC(req.getHoTen()); n.setSdt(nz(req.getSdt()));
            n.setEmail(req.getEmail()); n.setDiaChi(req.getDiaChi()); n.setCongNo(0);
            db.nhaCungCaps.add(n);
        } else {
            NguoiDung nd;
            switch (maQuyen) {
                case 1 -> { QuanTriVien q = new QuanTriVien(); q.setNgayBatDau(LocalDate.now()); nd = q; }
                case 2 -> nd = new DuocSiTruong();
                default -> nd = new DuocSi();
            }
            maND = nextId("NV", 3, id -> db.nguoiDungs.stream().anyMatch(u -> u.getMaND().equals(id)), db.nguoiDungs.size());
            nd.setMaND(maND); nd.setHoTen(req.getHoTen()); nd.setEmail(req.getEmail());
            nd.setSdt(req.getSdt()); nd.setGioiTinh(req.getGioiTinh());
            nd.setNgaySinh(req.getNgaySinh()); nd.setDiaChi(req.getDiaChi());
            nd.setTrangThai("active");
            db.nguoiDungs.add(nd);
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setMaTK(nextId("TK", 3, id -> db.taiKhoans.stream().anyMatch(t -> t.getMaTK().equals(id)), db.taiKhoans.size()));
        tk.setTenDangNhap(req.getTenDangNhap()); tk.setMatKhau(req.getMatKhau());
        tk.setMaND(maND); tk.setMaQuyen(maQuyen); tk.setTrangThai(true);
        tk.setLanDangNhapCuoi(LocalDateTime.now());
        db.taiKhoans.add(tk);

        return dangNhap(req.getTenDangNhap(), req.getMatKhau());
    }

    private String nextId(String prefix, int width, Predicate<String> exists, int start) {
        int i = start + 1;
        String id = prefix + String.format("%0" + width + "d", i);
        while (exists.test(id)) { i++; id = prefix + String.format("%0" + width + "d", i); }
        return id;
    }

    public void dangXuat(String token) { sessions.xoaSession(token); }

    public AuthResponse layThongTin(String token) {
        SessionStore.Session s = sessions.laySession(token);
        if (s == null) throw new IllegalArgumentException("Phiên đăng nhập hết hạn");
        String[] info = thongTin(s.maND, s.maQuyen);
        return new AuthResponse(token, s.maND, info[0], info[1], info[2], s.maQuyen, tenQuyen(s.maQuyen));
    }

    public boolean coQuyen(String token, int... maQuyensChoPhep) {
        SessionStore.Session s = sessions.laySession(token);
        if (s == null) return false;
        for (int q : maQuyensChoPhep) if (s.maQuyen == q) return true;
        return false;
    }

    public List<PhanQuyen> danhSachQuyen() { return db.phanQuyens; }

    public List<TaiKhoan> danhSachTaiKhoan() { return db.taiKhoans; }

    private TaiKhoan timTaiKhoan(String maTK) {
        return db.taiKhoans.stream().filter(t -> t.getMaTK().equals(maTK)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));
    }

    public void capNhatQuyen(String maTK, int maQuyenMoi) {
        TaiKhoan tk = timTaiKhoan(maTK);
        if (db.phanQuyens.stream().noneMatch(p -> p.getMaQuyen() == maQuyenMoi))
            throw new IllegalArgumentException("Quyền không tồn tại");
        boolean cuLaNhanVien = tk.getMaQuyen() <= 3, moiLaNhanVien = maQuyenMoi <= 3;
        if (cuLaNhanVien != moiLaNhanVien || (!cuLaNhanVien && tk.getMaQuyen() != maQuyenMoi))
            throw new IllegalArgumentException("Chỉ được đổi quyền trong nhóm nhân viên (1-3)");
        tk.setMaQuyen(maQuyenMoi);
    }

    public void doiTrangThai(String maTK, boolean trangThai) {
        timTaiKhoan(maTK).setTrangThai(trangThai);
    }
}
