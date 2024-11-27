package dto;

import java.time.LocalDate;

public class HoaDonDTO {
    private String idHoaDon;
    private String idKhachHang;
    private String idUser;
    private LocalDate ngayXuat;
    private int tongTien;

    public HoaDonDTO() {
        
    }

    public HoaDonDTO(String idHoaDon, String idKhachHang, String idUser, LocalDate ngayXuat, int tongTien) {
        this.idHoaDon = idHoaDon;
        this.idKhachHang = idKhachHang;
        this.idUser = idUser;
        this.ngayXuat = ngayXuat;
        this.tongTien = tongTien;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public LocalDate getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(LocalDate ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
