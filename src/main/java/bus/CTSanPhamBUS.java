package bus;

import dao.CTSanPhamDAO;
import dto.CTSanPhamDTO;
import java.util.ArrayList;

public class CTSanPhamBUS {
    private ArrayList<CTSanPhamDTO> ctspList;

    public ArrayList<CTSanPhamDTO> getCtspList() {
        return ctspList;
    }

    public void setCtspList(ArrayList<CTSanPhamDTO> ctspList) {
        this.ctspList = ctspList;
    }
    
    public void list() {
        CTSanPhamDAO ctspDAO = new CTSanPhamDAO();
        ctspList = new ArrayList<>();
        ctspList = ctspDAO.list();
    }
    
    public void addCTSP(CTSanPhamDTO ctsp) {
        ctspList.add(ctsp);
        CTSanPhamDAO ctspDAO = new CTSanPhamDAO();
        ctspDAO.addDB(ctsp);
    }
    
    public int getLenSP(String id) {
        int len = 1;
        for (CTSanPhamDTO ctsp : ctspList) {
            if (ctsp.getIdSanPham().equals(id)) {
                len++;
            }
        }
        return len;
    }
    
    public String createNewId(String idsp) {
        String id = idsp;
        int new_id = getLenSP(idsp);
        
        if (new_id <= 9) {
            id += "00" + new_id;
        }
        else if (new_id <= 99) {
            id += "0" + new_id;
        }
        else {
            id += new_id;
        }
        
        return id;
    }
}
