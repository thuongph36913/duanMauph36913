package com.example.duanph36913.Model;

public class thuThu {
    private int maTT;
    private String username, hoTen, matKhau, role;

    public thuThu() {
    }

    public thuThu(int maTT, String username, String hoTen, String matKhau) {
        this.maTT = maTT;
        this.username = username;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public thuThu(String username, String hoTen, String matKhau, String role) {
        this.username = username;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.role = role;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
