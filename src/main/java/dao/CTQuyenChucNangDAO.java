package dao;

import dto.CTQuyenChucNangDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTQuyenChucNangDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<CTQuyenChucNangDTO> list() {
        ArrayList<CTQuyenChucNangDTO> ctList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM ctquyenchucnang";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_quyen = rs.getString("id_quyen");
                String id_chuc_nang = rs.getString("id_chuc_nang");
                
                CTQuyenChucNangDTO ct = new CTQuyenChucNangDTO(id_quyen, id_chuc_nang);
                ctList.add(ct);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(CTQuyenChucNangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ctList;
    }
    
    public void addDB(CTQuyenChucNangDTO ct) {
        String sql = "INSERT INTO ctquyenchucnang VALUES (";
        sql += "'" + ct.getIdQuyen() + "', ";
        sql += "'" + ct.getIdChucNang() + "')";        
        db.executeUpdate(sql);
    }
    
    public void deleteDB(String id_quyen, String id_chuc_nang) {
        String sql = "DELETE FROM ctquyenchucnang WHERE id_quyen='" + id_quyen + "' AND id_chuc_nang='" + id_chuc_nang +"'";
        db.executeUpdate(sql);
    }
}
