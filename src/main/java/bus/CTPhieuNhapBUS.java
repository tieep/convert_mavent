package bus;

import dao.CTPhieuNhapDAO;
import dto.CTPhieuNhapDTO;
import java.util.ArrayList;

public class CTPhieuNhapBUS {

    private ArrayList<CTPhieuNhapDTO> ctpnList;

    public ArrayList<CTPhieuNhapDTO> getCtpnList() {
        return ctpnList;
    }

    public void setCthdList(ArrayList<CTPhieuNhapDTO> ctpnList) {
        this.ctpnList = ctpnList;
    }
    
    public void list() {
        CTPhieuNhapDAO ctpnDAO = new CTPhieuNhapDAO(); 
        ctpnList = new ArrayList<>();
        ctpnList = ctpnDAO.list();
    }
    
    public ArrayList<CTPhieuNhapDTO> listId(String id) {
        ArrayList<CTPhieuNhapDTO> arr = new ArrayList<>();
        
        for (CTPhieuNhapDTO ctpn : ctpnList) {
            if (ctpn.getIdPhieuNhap().equals(id)) {
                arr.add(ctpn);
            }
        }
        
        return arr;
    }
    
    public void addCTPN(CTPhieuNhapDTO ctpn) {
        ctpnList = new ArrayList<>();
        ctpnList.add(ctpn);
        CTPhieuNhapDAO ctPhieuNhapDAO = new CTPhieuNhapDAO();
        ctPhieuNhapDAO.addDB(ctpn);
    }
}
