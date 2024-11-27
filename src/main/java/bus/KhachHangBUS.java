package bus;

import dao.KhachHangDAO;
import dto.KhachHangDTO;
import java.util.ArrayList;

public class KhachHangBUS {

    private ArrayList<KhachHangDTO> khList;

    public KhachHangBUS() {

    }

    public ArrayList<KhachHangDTO> getKhList() {
        return khList;
    }

    public void setKhList(ArrayList<KhachHangDTO> khList) {
        this.khList = khList;
    }

    public void list() {
        KhachHangDAO khDAO = new KhachHangDAO();
        khList = new ArrayList<KhachHangDTO>();
        khList = khDAO.list();
    }

    public void updateKhachHang(KhachHangDTO kh) {
        for (int i = 0; i < khList.size(); i++) {
            if (khList.get(i).getIdKhachHang().equals(kh.getIdKhachHang())) {
                khList.set(i, kh);
                KhachHangDAO khachHangDAO = new KhachHangDAO();
                khachHangDAO.updateDB(kh);
                return;
            }
        }
    }

    public void addKhachHang(KhachHangDTO kh) {
        khList.add(kh);
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        khachHangDAO.addDB(kh);
    }

    public void deleteKhachHang(String id) {
        for (KhachHangDTO kh : khList) {
            if (kh.getIdKhachHang().equals(id)) {
                kh.setEnable(false);
                KhachHangDAO khachHangDAO = new KhachHangDAO();
                khachHangDAO.deleteDB(id);
                return;
            }
        }
    }

    public boolean isExisted(String id) {
        for (KhachHangDTO kh : khList) {
            if (kh.getIdKhachHang().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public String createNewId() {
        String id = "KH";
        int new_id = khList.size() + 1;

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