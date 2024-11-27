package bus;

import dao.QuyenDAO;
import dto.QuyenDTO;
import java.util.ArrayList;

public class QuyenBUS {
    private ArrayList<QuyenDTO> quyenList;

    public ArrayList<QuyenDTO> getQuyenList() {
        return quyenList;
    }

    public void setQuyenList(ArrayList<QuyenDTO> quyenList) {
        this.quyenList = quyenList;
    }
    
    public void list() {
        QuyenDAO quyenDAO = new QuyenDAO();
        quyenList = new ArrayList<>();
        quyenList = quyenDAO.list();
    }
    
    public void updateQuyen(QuyenDTO quyen) {
        for (int i = 0; i < quyenList.size(); i++) {
            if (quyenList.get(i).getIdQuyen().equals(quyen.getIdQuyen())) {
                quyenList.set(i, quyen);
                QuyenDAO quyenDAO = new QuyenDAO();
                quyenDAO.updateDB(quyen);
                return;
            }
        }
    }
    
    public void addQuyen(QuyenDTO quyen) {
        quyenList.add(quyen);
        QuyenDAO quyenDAO = new QuyenDAO();
        quyenDAO.addDB(quyen);
    }
    
    public void deleteQuyen(String id) {
        for (QuyenDTO quyen : quyenList) {
            if (quyen.getIdQuyen().equals(id)) {
                quyen.setEnable(false);
                QuyenDAO quyenDAO = new QuyenDAO();
                quyenDAO.deleteDB(id);
                return;
            }
        }
    }
    
    public String createNewId() {
        String id = "QU";
        int new_id = quyenList.size() + 1;
        
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
