package dto;

public class NhaCungCapDTO {
    private String idNhaCungCap;
    private String tenNhaCungCap;
    private String Diachi;
    private String Sdt;
    private boolean enable;

    public NhaCungCapDTO() {

    }

    public NhaCungCapDTO(String idNhaCungCap, String tenNhaCungCap, String Diachi, String Sdt, boolean enable) {
        this.idNhaCungCap = idNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.Diachi = Diachi;
        this.Sdt = Sdt;
        this.enable = enable;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public String getDiachi() {
        return Diachi;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }

    public void setSdt(String Sdt) {
        this.Sdt = Sdt;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
