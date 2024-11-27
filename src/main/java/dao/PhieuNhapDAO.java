package dao;

import dto.PhieuNhapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhieuNhapDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<PhieuNhapDTO> list() {
        ArrayList<PhieuNhapDTO> hdList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM phieunhap";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_pn = rs.getString("id_pn");
                String id_ncc = rs.getString("id_ncc");
                String id_user = rs.getString("id_user");
                java.sql.Date sqlDate = rs.getDate("ngay_nhap");
                LocalDate ngay_nhap = sqlDate.toLocalDate();
                int tong_tien = rs.getInt("tong_tien");
                
                PhieuNhapDTO hoaDon = new PhieuNhapDTO(id_pn, id_ncc, id_user, ngay_nhap, tong_tien);
                hdList.add(hoaDon);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hdList;
    } 
    
    public void addDB(PhieuNhapDTO pn) {
        String sql = "INSERT INTO phieunhap VALUES (";
        sql += "'" + pn.getIdPhieuNHap() + "', ";
        sql += "'" + pn.getIdNhaCungCap() + "', ";
        sql += "'" + pn.getIdUser() + "', ";
        sql += "'" + pn.getNgayNhap() + "', ";
        sql += "'" + pn.getTongTien() + "')";
        db.executeUpdate(sql);
    }
}
