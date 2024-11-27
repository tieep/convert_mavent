package bus;

import dao.PhieuBaoHanhDAO;
import dto.PhieuBaoHanhDTO;
import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuBaoHanhBUS {
    private ArrayList<PhieuBaoHanhDTO> bhList;

    public ArrayList<PhieuBaoHanhDTO> getBhList() {
        return bhList;
    }

    public void setBhList(ArrayList<PhieuBaoHanhDTO> bhList) {
        this.bhList = bhList;
    }
    
    public void list() {
        PhieuBaoHanhDAO bhDAO = new PhieuBaoHanhDAO();
        bhList = new ArrayList<>();
        bhList = bhDAO.list();
    }
    
    public void addBH(PhieuBaoHanhDTO ctsp) {
        bhList.add(ctsp);
        PhieuBaoHanhDAO bhDAO = new PhieuBaoHanhDAO();
        bhDAO.addDB(ctsp);
    }
    
    public ArrayList<PhieuBaoHanhDTO> filter(LocalDate dateFrom, LocalDate dateTo) {
        ArrayList<PhieuBaoHanhDTO> res = new ArrayList<>();
        
        for (PhieuBaoHanhDTO bh : bhList) {
            if ((bh.getNgayMua().isAfter(dateFrom) || bh.getNgayMua().isEqual(dateFrom)) && (bh.getNgayMua().isBefore(dateTo) || bh.getNgayMua().isEqual(dateTo))) {
                res.add(bh);
            }
        }
        
        return res;
    }
    
    public boolean checkTime(String serial, LocalDate date) {
        for (PhieuBaoHanhDTO bh : bhList) {
            if (bh.getSerial().equals(serial)) {
                if(bh.getNgayHetHan().isAfter(date)){
                    return true;
                }
            }
        }
        return false;
    }
}