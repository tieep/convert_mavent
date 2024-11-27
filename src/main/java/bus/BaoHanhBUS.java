package bus;

import dao.BaoHanhDAO;
import dto.BaoHanhDTO;
import java.time.LocalDate;
import java.util.ArrayList;

public class BaoHanhBUS {
    private ArrayList<BaoHanhDTO> bhList;

    public ArrayList<BaoHanhDTO> getBhList() {
        return bhList;
    }

    public void setBhList(ArrayList<BaoHanhDTO> bhList) {
        this.bhList = bhList;
    }
    
    public void list() {
        BaoHanhDAO bhDAO = new BaoHanhDAO();
        bhList = new ArrayList<>();
        bhList = bhDAO.list();
    }
    
    public void addBH(BaoHanhDTO ctsp) {
        bhList.add(ctsp);
        BaoHanhDAO bhDAO = new BaoHanhDAO();
        bhDAO.addDB(ctsp);
    }
    
    public ArrayList<BaoHanhDTO> filter(LocalDate dateFrom, LocalDate dateTo) {
        ArrayList<BaoHanhDTO> res = new ArrayList<>();
        
        for (BaoHanhDTO bh : bhList) {
            if ((bh.getNgayBaoHanh().isAfter(dateFrom) || bh.getNgayBaoHanh().isEqual(dateFrom)) && (bh.getNgayBaoHanh().isBefore(dateTo) || bh.getNgayBaoHanh().isEqual(dateTo))) {
                res.add(bh);
            }
        }
        
        return res;
    }
    
    public boolean checkTime(String serial, LocalDate date) {
        for (BaoHanhDTO bh : bhList) {
            if (bh.getSerial().equals(serial)) {
                if(bh.getNgayTraMay().isAfter(date)){
                    return false;
                }
            }
        }
        return true;
    }
    
    public String createNewId() {
        String id = "BH";
        int new_id = bhList.size() + 1;
        
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