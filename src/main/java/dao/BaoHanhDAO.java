package dao;

import dto.BaoHanhDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaoHanhDAO {
    private MySQLConnect db = new MySQLConnect();

    public ArrayList<BaoHanhDTO> list() {
        ArrayList<BaoHanhDTO> bhList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM baohanh";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_bh = rs.getString("id_bh");
                String id_kh = rs.getString("id_kh");
                String ten_sp = rs.getString("ten_sp");
                String serial = rs.getString("serial");
                java.sql.Date sqlDate1 = rs.getDate("ngay_baohanh");
                LocalDate ngay_baohanh = sqlDate1.toLocalDate();
                java.sql.Date sqlDate2 = rs.getDate("ngay_tramay");
                LocalDate ngay_tramay = sqlDate2.toLocalDate();
                
                BaoHanhDTO bh = new BaoHanhDTO(id_bh, id_kh, ten_sp, serial, ngay_baohanh, ngay_tramay);
                bhList.add(bh);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuBaoHanhDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bhList;
    }
    
    public void addDB(BaoHanhDTO bh) {
        String sql = "INSERT INTO baohanh VALUES (";
        sql += "'" + bh.getIdBaoHanh() + "', ";
        sql += "'" + bh.getIdKhachHang() + "', ";
        sql += "N'" + bh.getTenSanPham() + "', ";
        sql += "'" + bh.getSerial() + "', ";
        sql += "'" + bh.getNgayBaoHanh() + "', ";
        sql += "'" + bh.getNgayTraMay() + "') ";
        db.executeUpdate(sql);
    }
}
