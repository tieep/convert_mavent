package dto;

public class CTPhieuNhapDTO {
    private String idPhieuNhap;
    private String idSanPham;
    private String tenSanPham;
    private int soLuong;
    private int donGia;

    public CTPhieuNhapDTO() {
    }

    public CTPhieuNhapDTO(String idPhieuNhap, String idSanPham, String tenSanPham, int soLuong, int donGia) {
        this.idPhieuNhap = idPhieuNhap;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getIdPhieuNhap() {
        return idPhieuNhap;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setIdPhieuNhap(String idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}
