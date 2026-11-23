package com.hkcare.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class TaiKhoan {
    private String maTK;
    private String tenDangNhap;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)   // không trả mật khẩu ra API
    private String matKhau;
    private LocalDateTime lanDangNhapCuoi;
    private boolean trangThai;
    private String maND;
    private int maQuyen;

    public boolean dangNhap(String tenDangNhap, String matKhau) { return true; }
    public boolean dangXuat() { return true; }
    public boolean doiMatKhau() { return true; }

    public String getMaTK() { return maTK; }
    public void setMaTK(String maTK) { this.maTK = maTK; }
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public LocalDateTime getLanDangNhapCuoi() { return lanDangNhapCuoi; }
    public void setLanDangNhapCuoi(LocalDateTime lanDangNhapCuoi) { this.lanDangNhapCuoi = lanDangNhapCuoi; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public String getMaND() { return maND; }
    public void setMaND(String maND) { this.maND = maND; }
    public int getMaQuyen() { return maQuyen; }
    public void setMaQuyen(int maQuyen) { this.maQuyen = maQuyen; }
}
