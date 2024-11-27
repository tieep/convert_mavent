package dto;

import java.time.LocalDate;

public class PhieuNhapDTO {
    private String idPhieuNHap;
    private String idNhaCungCap;
    private String idUser;
    private LocalDate ngayNhap;
    private int tongTien;

    public PhieuNhapDTO() {
    }

    public PhieuNhapDTO(String idPhieuNHap, String idNhaCungCap, String idUser, LocalDate ngayNhap, int tongTien) {
        this.idPhieuNHap = idPhieuNHap;
        this.idNhaCungCap = idNhaCungCap;
        this.idUser = idUser;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
    }

    public String getIdPhieuNHap() {
        return idPhieuNHap;
    }

    public void setIdPhieuNHap(String idPhieuNHap) {
        this.idPhieuNHap = idPhieuNHap;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
