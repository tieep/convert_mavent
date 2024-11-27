package dao;

import dto.UserDTO;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private MySQLConnect db = new MySQLConnect();
    
    public ArrayList<UserDTO> list() {
        ArrayList<UserDTO> userList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM user";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                String ten = rs.getString("ten");
                String gioi_tinh = rs.getString("gioi_tinh");
                String sdt = rs.getString("sdt");
                String quyen = rs.getString("quyen");
                String img = rs.getString("img");
                boolean enable = rs.getBoolean("enable");
                
                UserDTO user = new UserDTO(id, password, ten, gioi_tinh, sdt, quyen, img, enable);
                userList.add(user);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userList;
    }
    

    public void updateDB(UserDTO us) {
        String sql = "UPDATE user SET ";
        sql += "password='" + us.getPassword() + "', ";
        sql += "ten='" + us.getTenUser() + "', ";
        sql += "gioi_tinh='" + us.getGioiTinh() + "', ";
        sql += "sdt='" + us.getSdt() + "', ";
        sql += "quyen='" + us.getQuyen() + "', ";
        sql += "img='" + us.getImgUser() + "' ";
        sql += "WHERE id='" + us.getIdUser() + "'";
        db.executeUpdate(sql);
    }
    
    public void addDB(UserDTO us) {
        String sql = "INSERT INTO user VALUES (";
        sql += "'" +  us.getIdUser() + "', ";
        sql += "'" +  us.getPassword() + "', ";
        sql += "N'" + us.getTenUser() + "', ";
        sql += "'" +  us.getGioiTinh() + "', ";
        sql += "'" +  us.getSdt() + "', ";
        sql += "'" +  us.getQuyen() + "', ";
        sql += "'" +  us.getImgUser() + "', ";
        sql += "'1')";

        db.executeUpdate(sql);
    }
    
    public void deleteDB(String id) {
        String sql = "UPDATE user SET enable = 0 WHERE id='" + id + "'";
        db.executeUpdate(sql);
    }
}
