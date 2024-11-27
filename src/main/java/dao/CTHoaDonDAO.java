package dao;

import dto.CTHoaDonDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTHoaDonDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<CTHoaDonDTO> list() {
        ArrayList<CTHoaDonDTO> cthdList = new ArrayList<>();

        try {
            
            String sql = "SELECT * FROM cthoadon";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_hd = rs.getString("id_hd");
                String id_sp = rs.getString("id_sp");
                String ten_sp = rs.getString("ten_sp");
                int so_luong = rs.getInt("so_luong");
                int don_gia = rs.getInt("don_gia");
                
                CTHoaDonDTO ctHoaDon = new CTHoaDonDTO(id_hd, id_sp, ten_sp, so_luong, don_gia);
                cthdList.add(ctHoaDon);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(CTHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cthdList;
    }
    
    public void addDB(CTHoaDonDTO cthd) {
        String sql = "INSERT INTO cthoadon VALUES (";
        sql += "'" + cthd.getIdHoaDon() + "', ";
        sql += "'" + cthd.getIdSanPham() + "', ";
        sql += "N'" + cthd.getTenSanPham() + "', ";
        sql += "'" + cthd.getSoLuong() + "', ";
        sql += "'" + cthd.getDonGia() + "')";
        db.executeUpdate(sql);
    }
}
