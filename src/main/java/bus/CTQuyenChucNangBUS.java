package bus;

import dao.CTQuyenChucNangDAO;
import dto.CTQuyenChucNangDTO;
import java.util.ArrayList;

public class CTQuyenChucNangBUS {
    private ArrayList<CTQuyenChucNangDTO> ctList;

    public ArrayList<CTQuyenChucNangDTO> getCtList() {
        return ctList;
    }

    public void setCtList(ArrayList<CTQuyenChucNangDTO> ctList) {
        this.ctList = ctList;
    }
    
    public void list() {
        CTQuyenChucNangDAO ctDAO = new CTQuyenChucNangDAO();
        ctList = new ArrayList<>();
        ctList = ctDAO.list();
    }
    
    public void addCTQCN(CTQuyenChucNangDTO ct) {
        ctList.add(ct);
        CTQuyenChucNangDAO ctDAO = new CTQuyenChucNangDAO();
        ctDAO.addDB(ct);
    }
    
    public void deleteCTQCN(String id_quyen, String id_chuc_nang) {
        for (CTQuyenChucNangDTO ct : ctList) {
            if (ct.getIdQuyen().equals(id_quyen) && ct.getIdChucNang().equals(id_chuc_nang)) {
                ctList.remove(ct);
                CTQuyenChucNangDAO ctDAO = new CTQuyenChucNangDAO();
                ctDAO.deleteDB(id_quyen, id_chuc_nang);
                return;
            }
        }
    }
    
    public ArrayList<String> listId(String id) {
        ArrayList<String> arr = new ArrayList<>();
        
        for (CTQuyenChucNangDTO ct : ctList) {
            if (ct.getIdQuyen().equals(id)) {
                arr.add(ct.getIdChucNang());
            }
        }
        
        return arr;
    }
}
