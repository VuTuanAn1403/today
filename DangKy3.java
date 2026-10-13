package com.example.myapplication;

public class DangKy {
    private String ten;
    private String doiTuong;
    private String sdt;

    public DangKy() {
    }

    public DangKy(String ten, String doiTuong, String sdt) {
        this.ten = ten;
        this.doiTuong = doiTuong;
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
