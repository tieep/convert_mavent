package dto;

public class SanPhamDTO {
    private String idSanPham;
    private String tenSanPham;
    private int soLuong;
    private int giaNhap;
    private int giaBan;
    private String hang;
    private String imgSanPham;
    private boolean enable;

    public SanPhamDTO() {
        
    }
    
    public SanPhamDTO(String idSanPham, String tenSanPham, int giaNhap, int giaBan, boolean enable) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.enable = enable;
    }

    public SanPhamDTO(String idSanPham, String tenSanPham, int soLuong, int giaNhap, int giaBan, String hang, String imgSanPham, boolean enable) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.hang = hang;
        this.imgSanPham = imgSanPham;
        this.enable = enable;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
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

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getImgSanPham() {
        return imgSanPham;
    }

    public void setImgSanPham(String imgSanPham) {
        this.imgSanPham = imgSanPham;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
