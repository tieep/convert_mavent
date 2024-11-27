package bus;

import dao.PhieuNhapDAO;
import dto.PhieuNhapDTO;
import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuNhapBUS {

    private ArrayList<PhieuNhapDTO> pnList;

    public ArrayList<PhieuNhapDTO> getPnList() {
        return pnList;
    }

    public void setPnList(ArrayList<PhieuNhapDTO> pnList) {
        this.pnList = pnList;
    }
    
    public void list() {
        PhieuNhapDAO pnDAO = new PhieuNhapDAO();
        pnList = new ArrayList<>();
        pnList = pnDAO.list();
    }
    
    public void addPhieuNhap(PhieuNhapDTO pn) {
        pnList.add(pn);
        PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
        phieuNhapDAO.addDB(pn);
    }
    
    public String createNewId() {
        String id = "PN";
        int new_id = pnList.size() + 1;
        
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
    
    public ArrayList<PhieuNhapDTO> filter(LocalDate dateBegin, LocalDate dateEnd) {
        ArrayList<PhieuNhapDTO> res = new ArrayList<>();
        
        for (PhieuNhapDTO pn : pnList) {
            if ((pn.getNgayNhap().isAfter(dateBegin) || pn.getNgayNhap().isEqual(dateBegin)) && (pn.getNgayNhap().isBefore(dateEnd) || pn.getNgayNhap().isEqual(dateEnd))) {
                res.add(pn);
            }
        }
        
        return res;
    }
    
    public int tinhTienNhap(LocalDate dateBegin, LocalDate dateEnd) {
        int sum = 0;
        
        for (PhieuNhapDTO pn : pnList) {
            if ((pn.getNgayNhap().isAfter(dateBegin) || pn.getNgayNhap().isEqual(dateBegin)) && (pn.getNgayNhap().isBefore(dateEnd) || pn.getNgayNhap().isEqual(dateEnd))) {
                sum += pn.getTongTien();
            }
        }
        
        return sum;
    }
}
