package bus;

import dao.NhaCungCapDAO;
import dto.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBUS {

    private ArrayList<NhaCungCapDTO> nccList;

    public NhaCungCapBUS() {

    }

    public ArrayList<NhaCungCapDTO> getNccList() {
        return nccList;
    }

    public void setNccList(ArrayList<NhaCungCapDTO> nccList) {
        this.nccList = nccList;
    }

    public void list() {
        NhaCungCapDAO nccDAO = new NhaCungCapDAO();
        nccList = new ArrayList<>();
        nccList = nccDAO.list();
    }

    public void updateNhaCungCap(NhaCungCapDTO ncc) {
        for (int i = 0; i < nccList.size(); i++) {
            if (nccList.get(i).getIdNhaCungCap().equals(ncc.getIdNhaCungCap())) {
                nccList.set(i, ncc);
                NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
                nhaCungCapDAO.updateDB(ncc);
                return;
            }
        }
    }

    public void addNhaCungCap(NhaCungCapDTO ncc) {
        nccList.add(ncc);
        NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
        nhaCungCapDAO.addDB(ncc);
    }

    public void deleteNhaCungCap(String id) {
        for (NhaCungCapDTO ncc : nccList) {
            if (ncc.getIdNhaCungCap().equals(id)) {
                ncc.setEnable(false);
                NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
                nhaCungCapDAO.deleteDB(id);
                return;
            }
        }
    }

    public boolean isExisted(String id) {
        for (NhaCungCapDTO ncc : nccList) {
            if (ncc.getIdNhaCungCap().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public String createNewId() {
        String id = "CC";
        int new_id = nccList.size() + 1;

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
