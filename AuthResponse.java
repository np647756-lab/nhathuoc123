package com.hkcare.model;

public class AuthResponse {
    private String token;
    private String maND;
    private String hoTen;
    private String email;
    private String role;
    private int maQuyen;
    private String tenQuyen;

    public AuthResponse() {}
    public AuthResponse(String token, String maND, String hoTen, String email, String role, int maQuyen, String tenQuyen) {
        this.token = token; this.maND = maND; this.hoTen = hoTen; this.email = email;
        this.role = role; this.maQuyen = maQuyen; this.tenQuyen = tenQuyen;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getMaND() { return maND; }
    public void setMaND(String maND) { this.maND = maND; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getMaQuyen() { return maQuyen; }
    public void setMaQuyen(int maQuyen) { this.maQuyen = maQuyen; }
    public String getTenQuyen() { return tenQuyen; }
    public void setTenQuyen(String tenQuyen) { this.tenQuyen = tenQuyen; }
}
