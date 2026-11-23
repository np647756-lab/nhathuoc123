package com.hkcare.repository;

import com.hkcare.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Kho dữ liệu trong bộ nhớ (thay cho CSDL) kèm dữ liệu mẫu.
 * Dữ liệu mất khi tắt ứng dụng. Các danh sách là public để các Service truy cập trực tiếp.
 */
@Component
public class DataStore {
    public final List<PhanQuyen> phanQuyens = new CopyOnWriteArrayList<>();
    public final List<NguoiDung> nguoiDungs = new CopyOnWriteArrayList<>();
    public final List<TaiKhoan> taiKhoans = new CopyOnWriteArrayList<>();
    public final List<CuaHang> cuaHangs = new CopyOnWriteArrayList<>();
    public final List<DanhMuc> danhMucs = new CopyOnWriteArrayList<>();
    public final List<NhaCungCap> nhaCungCaps = new CopyOnWriteArrayList<>();
    public final List<SanPham> sanPhams = new CopyOnWriteArrayList<>();
    public final List<PhieuNhapKho> phieuNhaps = new CopyOnWriteArrayList<>();
    public final List<DonDatHang> donDatHangs = new CopyOnWriteArrayList<>();
    public final List<KhachHang> khachHangs = new CopyOnWriteArrayList<>();
    public final List<HoaDon> hoaDons = new CopyOnWriteArrayList<>();
    public final List<DonHangOnline> donHangOnlines = new CopyOnWriteArrayList<>();

    public final List<KhuyenMai> khuyenMais = new CopyOnWriteArrayList<>();
    public final List<Payment> payments = new CopyOnWriteArrayList<>();

    public DataStore() { seed(); }

    private void seed() {
        // ---- Phân quyền
        phanQuyens.add(new PhanQuyen(1, "Quản trị viên", "Toàn quyền hệ thống"));
        phanQuyens.add(new PhanQuyen(2, "Dược sĩ trưởng", "Quản lý cửa hàng, kho, KH"));
        phanQuyens.add(new PhanQuyen(3, "Dược sĩ", "Bán hàng, KH, hàng hóa"));
        phanQuyens.add(new PhanQuyen(4, "Khách hàng", "Mua hàng online"));
        phanQuyens.add(new PhanQuyen(5, "Nhà cung cấp", "Xem đơn đặt hàng"));

        // ---- Cửa hàng & danh mục
        cuaHangs.add(new CuaHang("CH01", "HKCARE Pharmacy - Cơ sở 1", "Hà Nội", "0243000001"));
        danhMucs.add(new DanhMuc("DM01", "Thuốc"));
        danhMucs.add(new DanhMuc("DM02", "TPCN"));
        danhMucs.add(new DanhMuc("DM03", "Mỹ phẩm"));
        danhMucs.add(new DanhMuc("DM04", "Dụng cụ y tế"));

        // ---- Nhà cung cấp
        nhaCungCaps.add(ncc("NCC01", "Cty Dược phẩm Minh Dược", "0101234567", "minhduoc@ncc.vn", 120_000_000L));
        nhaCungCaps.add(ncc("NCC02", "Cty TNHH Sức khỏe Việt", "0101234568", "suckhoe@ncc.vn", 45_000_000L));
        nhaCungCaps.add(ncc("NCC03", "Cty CP Dược TW Mediplantex", "0101234569", "medi@ncc.vn", 0L));

        // ---- Sản phẩm (mã SP|tên|đơn vị|giá|tồn|nhóm|NCC|mã ảnh flaticon)
        String[] sp = {
            "SP0001|Paracetamol 500mg|Hộp|25000|120|DM01|NCC01|2934972",
            "SP0004|Amoxicillin 500mg|Hộp|45000|8|DM01|NCC01|2934972",
            "SP0007|Omeprazole 20mg|Hộp|85000|60|DM01|NCC02|2934972",
            "SP0002|Vitamin C 1000mg|Lọ|180000|45|DM02|NCC02|3004455",
            "SP0008|Omega 3 Fish Oil|Lọ|320000|30|DM02|NCC02|3004455",
            "SP0006|Sữa rửa mặt Cetaphil|Lọ|250000|20|DM03|NCC02|2553691",
            "SP0009|Kem chống nắng Anessa|Tuýp|450000|15|DM03|NCC02|2553691",
            "SP0003|Khẩu trang y tế 4 lớp|Hộp|70000|300|DM04|NCC03|2966486",
            "SP0005|Nước muối sinh lý 0.9%|Chai|15000|60|DM04|NCC03|3004455",
            "SP0010|Bông y tế gói 100g|Gói|12000|150|DM04|NCC03|2966486",
            "SP0011|Băng keo cá nhân (hộp 20)|Hộp|20000|90|DM04|NCC03|2966486",
            "SP0012|Cồn sát khuẩn 70 độ 500ml|Chai|35000|75|DM04|NCC03|2966486",
            "SP0013|Nhiệt kế điện tử|Cái|180000|25|DM04|NCC01|3075955",
            "SP0014|Máy đo huyết áp Omron|Cái|850000|10|DM04|NCC01|3075955",
            "SP0015|Máy đo đường huyết|Cái|650000|8|DM04|NCC01|3075955",
            "SP0016|Que thử thai (hộp 2)|Hộp|40000|50|DM04|NCC02|3075955",
            "SP0017|Găng tay y tế (hộp 100)|Hộp|85000|40|DM04|NCC03|2966486",
            "SP0018|Ống nghe y tế|Cái|220000|12|DM04|NCC01|3075955"
        };
        for (String row : sp) {
            String[] a = row.split("\\|");
            SanPham p = new SanPham();
            p.setMaSP(a[0]); p.setTenThuoc(a[1]); p.setDonViTinh(a[2]);
            p.setGiaBan(Long.parseLong(a[3])); p.setTonKho(Integer.parseInt(a[4]));
            p.setMaDM(a[5]); p.setMaNCC(a[6]);
            p.setHinhAnh("/images/" + a[0] + ".svg");   // ảnh nằm trong static/images
            sanPhams.add(p);
        }

        // ---- Nhân viên (kế thừa NguoiDung)
        QuanTriVien q = new QuanTriVien();
        q.setMaND("NV001"); q.setHoTen("Vũ Văn Huy"); q.setEmail("huy@hkcare.vn"); q.setTrangThai("active");
        q.setNgayBatDau(LocalDate.of(2024, 1, 1)); q.setGhiChuQuyen("Toàn quyền");
        DuocSiTruong dst = new DuocSiTruong();
        dst.setMaND("NV002"); dst.setHoTen("Nguyễn Thị Lan"); dst.setEmail("lan@hkcare.vn"); dst.setTrangThai("active");
        dst.setMaCHQuanLy("CH01");
        DuocSi ds = new DuocSi();
        ds.setMaND("NV003"); ds.setHoTen("Phạm Minh Dũng"); ds.setEmail("dung@hkcare.vn"); ds.setTrangThai("active");
        ds.setSoChungChi("CCHN-0003");
        nguoiDungs.add(q); nguoiDungs.add(dst); nguoiDungs.add(ds);

        // ---- Khách hàng
        khachHangs.add(kh("KH0001", "Nguyễn Văn A", "0912000001", "Nam", "a@mail.com", 120));
        khachHangs.add(kh("KH0002", "Trần Thị B", "0987000002", "Nữ", "b@mail.com", 45));

        // ---- Tài khoản (mật khẩu mẫu: 123456)
        taiKhoans.add(tk("TK001", "huy", "NV001", 1));
        taiKhoans.add(tk("TK002", "lan", "NV002", 2));
        taiKhoans.add(tk("TK003", "dung", "NV003", 3));
        taiKhoans.add(tk("TK004", "khachhang", "KH0001", 4));
        taiKhoans.add(tk("TK005", "nhacungcap", "NCC01", 5));

        // ---- Khuyến mãi
        khuyenMais.add(km("KM001", "Mua 2 tặng 1 Paracetamol", "Mua N tặng M", "Đang chạy"));
        khuyenMais.add(km("KM002", "Giảm 10% Vitamin C", "Giảm giá %", "Nháp"));

        // ---- Phiếu nhập & đơn đặt hàng mẫu
        PhieuNhapKho pn = new PhieuNhapKho();
        pn.setMaPN("PN0001"); pn.setMaNCC("NCC01"); pn.setMaCH("CH01");
        pn.setNgayNhap(LocalDate.of(2026, 4, 20)); pn.setTongTien(15_000_000L);
        ChiTietNhap ct = new ChiTietNhap();
        ct.setMaSP("SP0001"); ct.setSoLuong(600); ct.setDonGiaNhap(25_000L); ct.setThanhTien(15_000_000L);
        pn.setChiTiet(new ArrayList<>(List.of(ct)));
        phieuNhaps.add(pn);

        donDatHangs.add(po("PO20260501", "NCC01", LocalDate.of(2026, 5, 1), "Chờ duyệt"));
        donDatHangs.add(po("PO20260502", "NCC02", LocalDate.of(2026, 5, 2), "Đã duyệt"));
    }

    private static NhaCungCap ncc(String ma, String ten, String mst, String email, long congNo) {
        NhaCungCap n = new NhaCungCap();
        n.setMaNCC(ma); n.setTenNCC(ten); n.setMaSoThue(mst); n.setDiaChi("Hà Nội");
        n.setSdt("0243000000"); n.setEmail(email); n.setCongNo(congNo);
        return n;
    }

    private static KhachHang kh(String ma, String ten, String sdt, String gt, String email, int diem) {
        KhachHang k = new KhachHang();
        k.setMaKH(ma); k.setHoTen(ten); k.setSdt(sdt); k.setGioiTinh(gt); k.setEmail(email); k.setDiemTichLuy(diem);
        return k;
    }

    private static TaiKhoan tk(String ma, String user, String maND, int quyen) {
        TaiKhoan t = new TaiKhoan();
        t.setMaTK(ma); t.setTenDangNhap(user); t.setMatKhau("123456");
        t.setMaND(maND); t.setMaQuyen(quyen); t.setTrangThai(true);
        return t;
    }

    private static KhuyenMai km(String ma, String moTa, String loai, String tt) {
        KhuyenMai k = new KhuyenMai();
        k.setMaKM(ma); k.setMoTa(moTa); k.setLoaiKM(loai); k.setTrangThai(tt);
        return k;
    }

    private static DonDatHang po(String ma, String ncc, LocalDate ngay, String tt) {
        DonDatHang d = new DonDatHang();
        d.setMaPO(ma); d.setMaNCC(ncc); d.setNgayDat(ngay); d.setTrangThai(tt);
        d.setChiTiet(new ArrayList<>());
        return d;
    }

    /** Sinh mã tiếp theo = số lớn nhất đang có + 1 (an toàn khi đã xóa bản ghi). Ví dụ taoMa("KH", 4, ids) -> KH0003. */
    public static String taoMa(String prefix, int width, java.util.stream.Stream<String> ids) {
        int max = ids.filter(id -> id != null && id.startsWith(prefix))
                .mapToInt(id -> {
                    try { return Integer.parseInt(id.substring(prefix.length())); }
                    catch (NumberFormatException e) { return 0; }
                }).max().orElse(0);
        return prefix + String.format("%0" + width + "d", max + 1);
    }
}
