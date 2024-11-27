package dto;

import java.time.LocalDate;

public class PhieuBaoHanhDTO {
    private String idHoaDon;
    private String idKhachHang;
    private String tenSanPham;
    private String serial;
    private LocalDate ngayMua;
    private LocalDate ngayHetHan;

    public PhieuBaoHanhDTO(String idHoaDon, String idKhachHang, String tenSanPham, String serial, LocalDate ngayMua, LocalDate ngayHetHan) {
        this.idKhachHang = idKhachHang;
        this.tenSanPham = tenSanPham;
        this.serial = serial;
        this.ngayMua = ngayMua;
        this.ngayHetHan = ngayHetHan;
        this.idHoaDon = idHoaDon;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    

    public PhieuBaoHanhDTO() {
        
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public String getSerial() {
        return serial;
    }

    public LocalDate getNgayMua() {
        return ngayMua;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setNgayMua(LocalDate ngayMua) {
        this.ngayMua = ngayMua;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
}
