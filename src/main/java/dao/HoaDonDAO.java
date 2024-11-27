package dao;

import dto.HoaDonDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoaDonDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<HoaDonDTO> list() {
        ArrayList<HoaDonDTO> hdList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM hoadon";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_hd = rs.getString("id_hd");
                String id_kh = rs.getString("id_kh");
                String id_user = rs.getString("id_user");
                java.sql.Date sqlDate = rs.getDate("ngay_xuat");
                LocalDate ngay_xuat = sqlDate.toLocalDate();
                int tong_tien = rs.getInt("tong_tien");
                
                HoaDonDTO hoaDon = new HoaDonDTO(id_hd, id_kh, id_user, ngay_xuat, tong_tien);
                hdList.add(hoaDon);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hdList;
    } 
    
    public void addDB(HoaDonDTO hd) {
        String sql = "INSERT INTO hoadon VALUES (";
        sql += "'" + hd.getIdHoaDon() + "', ";
        sql += "'" + hd.getIdKhachHang() + "', ";
        sql += "'" + hd.getIdUser() + "', ";
        sql += "'" + hd.getNgayXuat() + "', ";
        sql += "'" + hd.getTongTien() + "')";
        db.executeUpdate(sql);
    }
}
