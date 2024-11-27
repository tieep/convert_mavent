package dao;

import dto.PhieuBaoHanhDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhieuBaoHanhDAO {
    private MySQLConnect db = new MySQLConnect();

    public ArrayList<PhieuBaoHanhDTO> list() {
        ArrayList<PhieuBaoHanhDTO> bhList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM phieubaohanh";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_kh = rs.getString("id_kh");
                String ten_sp = rs.getString("ten_sp");
                String serial = rs.getString("serial");
                java.sql.Date sqlDate1 = rs.getDate("ngay_mua");
                LocalDate ngay_mua = sqlDate1.toLocalDate();
                java.sql.Date sqlDate2 = rs.getDate("ngay_het_han");
                LocalDate ngay_het_han = sqlDate2.toLocalDate();
                String id_hd = rs.getString("id_hd");
                
                PhieuBaoHanhDTO bh = new PhieuBaoHanhDTO(id_hd, id_kh, ten_sp, serial, ngay_mua, ngay_het_han);
                bhList.add(bh);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuBaoHanhDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bhList;
    }
    
    public void addDB(PhieuBaoHanhDTO bh) {
        String sql = "INSERT INTO phieubaohanh VALUES (";
        sql += "'" + bh.getIdKhachHang() + "', ";
        sql += "N'" + bh.getTenSanPham() + "', ";
        sql += "'" + bh.getSerial() + "', ";
        sql += "'" + bh.getNgayMua() + "', ";
        sql += "'" + bh.getNgayHetHan() + "', ";
        sql += "'" + bh.getIdHoaDon() + "') ";
        db.executeUpdate(sql);
    }
}
