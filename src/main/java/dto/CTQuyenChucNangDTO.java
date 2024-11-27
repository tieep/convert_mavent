package dto;

public class CTQuyenChucNangDTO {
    private String idQuyen;
    private String idChucNang;

    public CTQuyenChucNangDTO() {
    
    }

    public CTQuyenChucNangDTO(String idQuyen, String idChucNang) {
        this.idQuyen = idQuyen;
        this.idChucNang = idChucNang;
    }

    public String getIdQuyen() {
        return idQuyen;
    }

    public void setIdQuyen(String idQuyen) {
        this.idQuyen = idQuyen;
    }

    public String getIdChucNang() {
        return idChucNang;
    }

    public void setIdChucNang(String idChucNang) {
        this.idChucNang = idChucNang;
    }
}
