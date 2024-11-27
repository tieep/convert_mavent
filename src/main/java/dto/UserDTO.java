package dto;

public class UserDTO {
    private String idUser;
    private String password;
    private String tenUser;
    private String gioiTinh;
    private String sdt;
    private String quyen;
    private String imgUser;
    private boolean enable;

    public UserDTO() {
        
    }

    public UserDTO(String idUser, String password, String tenUser, String gioiTinh, String sdt, String quyen, String imgUser, boolean enable) {
        this.idUser = idUser;
        this.password = password;
        this.tenUser = tenUser;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.quyen = quyen;
        this.imgUser = imgUser;
        this.enable = enable;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
