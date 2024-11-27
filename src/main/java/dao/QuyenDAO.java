package dao;

import dto.QuyenDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuyenDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<QuyenDTO> list() {
        ArrayList<QuyenDTO> quyenList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM quyen";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String ten = rs.getString("ten");
                boolean enable = rs.getBoolean("enable");
                
                QuyenDTO quyen = new QuyenDTO(id, ten, enable);
                quyenList.add(quyen);
            }
            rs.close();
            db.disConnect();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(QuyenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quyenList;
    }
    
    public void updateDB(QuyenDTO quyen) {
        String sql = "UPDATE quyen SET ";
        sql += "ten='" + quyen.getTenQuyen() + "' ";
        sql += "WHERE id ='" + quyen.getIdQuyen() + "'";
        db.executeUpdate(sql);
    }
    
    public void addDB(QuyenDTO quyen) {
        String sql = "INSERT INTO quyen VALUES (";
        sql += "'" + quyen.getIdQuyen() + "', ";
        sql += "'" + quyen.getTenQuyen() + "', ";
        sql += "'1')";
        db.executeUpdate(sql);
    }
    
    public void deleteDB(String id) {
        String sql = "UPDATE quyen SET enable = 0 WHERE id='" + id + "'";
        db.executeUpdate(sql);
    }
}
