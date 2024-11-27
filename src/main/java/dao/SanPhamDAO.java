package dao;

import dto.SanPhamDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanPhamDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<SanPhamDTO> list() {
        ArrayList<SanPhamDTO> spList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM sanpham";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String ten = rs.getString("ten");
                int so_luong = rs.getInt("so_luong");
                int gia_nhap = rs.getInt("gia_nhap");
                int gia_ban = rs.getInt("gia_ban");
                String hang = rs.getString("hang");
                String img = rs.getString("img");
                boolean enable = rs.getBoolean("enable");
                
                SanPhamDTO sanPham = new SanPhamDTO(id, ten, so_luong, gia_nhap, gia_ban, hang, img, enable);
                spList.add(sanPham);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return spList;
    }
    
    public void updateDB(SanPhamDTO sp) {
        String sql = "UPDATE sanpham SET ";
        sql += "ten='" + sp.getTenSanPham() + "', ";
        sql += "so_luong='" + sp.getSoLuong() + "', ";
        sql += "gia_nhap='" + sp.getGiaNhap() + "', ";
        sql += "gia_ban='" + sp.getGiaBan() + "', ";
        sql += "hang='" + sp.getHang() + "', ";
        sql += "img='" + sp.getImgSanPham() + "' ";
        sql += "WHERE id='" + sp.getIdSanPham() + "'";
        db.executeUpdate(sql);
    }
    
    public void addDB(SanPhamDTO sp) {
        String sql = "INSERT INTO sanpham VALUES (";
        sql += "'" + sp.getIdSanPham() + "', ";
        sql += "N'" + sp.getTenSanPham() + "', ";
        sql += "'" + sp.getSoLuong() + "', ";
        sql += "'" + sp.getGiaNhap() + "', ";
        sql += "'" + sp.getGiaBan() + "', ";
        sql += "'" + sp.getHang() + "', ";
        sql += "'" + sp.getImgSanPham() + "', ";
        sql += "'1')";
        db.executeUpdate(sql);
    }
    
    public void deleteDB(String id) {
        String sql = "UPDATE sanpham SET enable = 0 WHERE id='" + id + "'";
        db.executeUpdate(sql);
    }
}
