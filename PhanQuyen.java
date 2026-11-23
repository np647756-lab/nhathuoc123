package com.hkcare.model;

public class PhanQuyen {
    private int maQuyen;
    private String tenQuyen;
    private String moTa;

    public PhanQuyen() {}
    public PhanQuyen(int maQuyen, String tenQuyen, String moTa) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.moTa = moTa;
    }

    public int getMaQuyen() { return maQuyen; }
    public void setMaQuyen(int maQuyen) { this.maQuyen = maQuyen; }
    public String getTenQuyen() { return tenQuyen; }
    public void setTenQuyen(String tenQuyen) { this.tenQuyen = tenQuyen; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}
