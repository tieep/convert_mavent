package dao;

import dto.CTPhieuNhapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTPhieuNhapDAO {

    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<CTPhieuNhapDTO> list() {
        ArrayList<CTPhieuNhapDTO> ctpnList = new ArrayList<>();

        try {
            
            String sql = "SELECT * FROM ctphieunhap";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_pn = rs.getString("id_pn");
                String id_sp = rs.getString("id_sp");
                String ten_sp = rs.getString("ten_sp");
                int so_luong = rs.getInt("so_luong");
                int don_gia = rs.getInt("don_gia");
                
                CTPhieuNhapDTO ctPhieuNhap = new CTPhieuNhapDTO(id_pn, id_sp, ten_sp, so_luong, don_gia);
                ctpnList.add(ctPhieuNhap);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(CTHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctpnList;
    }
    
    public void addDB(CTPhieuNhapDTO ctpn) {
        String sql = "INSERT INTO ctphieunhap VALUES (";
        sql += "'" + ctpn.getIdPhieuNhap() + "', ";
        sql += "'" + ctpn.getIdSanPham() + "', ";
        sql += "N'" + ctpn.getTenSanPham() + "', ";
        sql += "'" + ctpn.getSoLuong() + "', ";
        sql += "'" + ctpn.getDonGia() + "')";
        db.executeUpdate(sql);
    }
}