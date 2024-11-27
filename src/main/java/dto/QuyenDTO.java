package dto;

public class QuyenDTO {
    private String idQuyen;
    private String tenQuyen;
    private boolean enable;

    public QuyenDTO() {
    
    }
    
    public QuyenDTO(String idQuyen, String tenQuyen, boolean enable) {
        this.idQuyen = idQuyen;
        this.tenQuyen = tenQuyen;
        this.enable = enable;
    }

    public String getIdQuyen() {
        return idQuyen;
    }

    public void setIdQuyen(String idQuyen) {
        this.idQuyen = idQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return this.tenQuyen;
    }
}
