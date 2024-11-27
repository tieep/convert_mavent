package dto;

import java.time.LocalDate;

public class BaoHanhDTO {
    private String idBaoHanh;
    private String idKhachHang;
    private String tenSanPham;
    private String serial;
    private LocalDate ngayBaoHanh;
    private LocalDate ngayTraMay;

    public BaoHanhDTO(String idBaoHanh, String idKhachHang, String tenSanPham, String serial, LocalDate ngayBaoHanh, LocalDate ngayTraMay) {
        this.idKhachHang = idKhachHang;
        this.tenSanPham = tenSanPham;
        this.serial = serial;
        this.ngayBaoHanh = ngayBaoHanh;
        this.ngayTraMay = ngayTraMay;
        this.idBaoHanh = idBaoHanh;
    }
    
    public BaoHanhDTO() {
        
    }

    public String getIdBaoHanh() {
        return idBaoHanh;
    }

    public void setIdBaoHanh(String idBaoHanh) {
        this.idBaoHanh = idBaoHanh;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public LocalDate getNgayBaoHanh() {
        return ngayBaoHanh;
    }

    public void setNgayBaoHanh(LocalDate ngayBaoHanh) {
        this.ngayBaoHanh = ngayBaoHanh;
    }

    public LocalDate getNgayTraMay() {
        return ngayTraMay;
    }

    public void setNgayTraMay(LocalDate ngayTraMay) {
        this.ngayTraMay = ngayTraMay;
    }

}
