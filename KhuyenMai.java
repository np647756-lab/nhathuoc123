package com.hkcare.model;

import java.time.LocalDate;
import java.util.List;

public class KhuyenMai {
    private String maKM;
    private String moTa;
    private String loaiKM;
    private String trangThai;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private List<ChiTietKhuyenMai> chiTiet;

    public KhuyenMai() {}

    public String getMaKM() { return maKM; }
    public void setMaKM(String maKM) { this.maKM = maKM; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getLoaiKM() { return loaiKM; }
    public void setLoaiKM(String loaiKM) { this.loaiKM = loaiKM; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public List<ChiTietKhuyenMai> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietKhuyenMai> chiTiet) { this.chiTiet = chiTiet; }
}
