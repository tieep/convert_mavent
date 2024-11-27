package dao;

import dto.CTSanPhamDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTSanPhamDAO {
    private MySQLConnect db = new MySQLConnect();

    public ArrayList<CTSanPhamDTO> list() {
        ArrayList<CTSanPhamDTO> ctList = new ArrayList<>();
        
        try {            
            String sql = "SELECT * FROM ctsanpham";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                String id_sp = rs.getString("id_sp");
                String serial = rs.getString("serial");
                
                CTSanPhamDTO ctsp = new CTSanPhamDTO(id_sp, serial);
                ctList.add(ctsp);
            }
            rs.close();
            db.disConnect();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ctList;
    }
    
    public void addDB(CTSanPhamDTO ctsp) {
        String sql = "INSERT INTO ctsanpham VALUES (";
        sql += "'" + ctsp.getIdSanPham() + "', ";
        sql += "'" + ctsp.getSerial() + "')";
        db.executeUpdate(sql);
    }
}
