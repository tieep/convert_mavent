package bus;

import dao.CTHoaDonDAO;
import dto.CTHoaDonDTO;
import java.util.ArrayList;

public class CTHoaDonBUS {
    private ArrayList<CTHoaDonDTO> cthdList;

    public ArrayList<CTHoaDonDTO> getCthdList() {
        return cthdList;
    }

    public void setCthdList(ArrayList<CTHoaDonDTO> cthdList) {
        this.cthdList = cthdList;
    }
    
    public void list() {
        CTHoaDonDAO cthdDAO = new CTHoaDonDAO(); 
        cthdList = new ArrayList<>();
        cthdList = cthdDAO.list();
    }
    
    public ArrayList<CTHoaDonDTO> listId(String id) {
        ArrayList<CTHoaDonDTO> arr = new ArrayList<>();
        
        for (CTHoaDonDTO cthd : cthdList) {
            if (cthd.getIdHoaDon().equals(id)) {
                arr.add(cthd);
            }
        }
        
        return arr;
    }
    
    public void addCTHD(CTHoaDonDTO cthd) {
        cthdList = new ArrayList<>();
        cthdList.add(cthd);
        CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO();
        ctHoaDonDAO.addDB(cthd);
    }
}
