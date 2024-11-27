package dao;

import dto.KhachHangDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO {
    private MySQLConnect db = new MySQLConnect();


    public ArrayList<KhachHangDTO> list() {
        ArrayList<KhachHangDTO> khList = new ArrayList<>();

        try {            
            String sql = "SELECT * FROM khachhang";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String ten = rs.getString("ten");
                String dia_chi = rs.getString("dia_chi");
                String sdt = rs.getString("sdt");
                boolean enable = rs.getBoolean("enable");

                KhachHangDTO khachHang = new KhachHangDTO(id, ten, dia_chi, sdt, enable);
                khList.add(khachHang);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        return khList;
    }

    public void updateDB(KhachHangDTO kh) {
        String sql = "UPDATE khachhang SET ";
        sql += "ten='" + kh.getTenKhachHang() + "', ";
        sql += "dia_chi='" + kh.getDiachi() + "', ";
        sql += "sdt='" + kh.getSdt() + "' "; 
        sql += "WHERE id='" + kh.getIdKhachHang() + "'";
        db.executeUpdate(sql);
    }

    public void addDB(KhachHangDTO kh) {
        String sql = "INSERT INTO khachhang VALUES (";
        sql += "'" +  kh.getIdKhachHang() + "', ";
        sql += "N'" + kh.getTenKhachHang() + "', ";
        sql += "N'" + kh.getDiachi() + "', ";
        sql += "'" +  kh.getSdt() + "', ";
        sql += "'1')";
        db.executeUpdate(sql);
    }

    public void deleteDB(String id) {
        String sql = "UPDATE khachhang SET enable = 0 WHERE id='" + id + "'";
        db.executeUpdate(sql);
    }
}