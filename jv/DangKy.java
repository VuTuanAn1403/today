package com.example.myapplication;

public class DangKy {
    private String ten;
    private String tuoi;
    private String linhVuc;
    private String ngayHen;

    public DangKy() {
    }

    public DangKy(String ten, String tuoi, String linhVuc, String ngayHen) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.linhVuc = linhVuc;
        this.ngayHen = ngayHen;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public String getLinhVuc() {
        return linhVuc;
    }

    public void setLinhVuc(String linhVuc) {
        this.linhVuc = linhVuc;
    }

    public String getNgayHen() {
        return ngayHen;
    }

    public void setNgayHen(String ngayHen) {
        this.ngayHen = ngayHen;
    }
}
