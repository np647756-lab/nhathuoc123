package com.hkcare.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "loai", defaultImpl = DuocSi.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = QuanTriVien.class, name = "admin"),
    @JsonSubTypes.Type(value = DuocSiTruong.class, name = "duocsitruong"),
    @JsonSubTypes.Type(value = DuocSi.class, name = "duocsi")
})
public abstract class NguoiDung {
    protected String maND;
    protected String hoTen;
    protected String gioiTinh;
    protected LocalDate ngaySinh;
    protected String diaChi;
    protected String email;
    protected String sdt;
    protected String trangThai;

    public NguoiDung() {}
    public NguoiDung(String maND, String hoTen, String gioiTinh, LocalDate ngaySinh,
                     String diaChi, String email, String sdt, String trangThai) {
        this.maND = maND; this.hoTen = hoTen; this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh; this.diaChi = diaChi; this.email = email;
        this.sdt = sdt; this.trangThai = trangThai;
    }

    public abstract boolean themNguoiDung();
    public abstract boolean capNhatNguoiDung();
    public abstract boolean xoaNguoiDung();

    public String getMaND() { return maND; }
    public void setMaND(String maND) { this.maND = maND; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
