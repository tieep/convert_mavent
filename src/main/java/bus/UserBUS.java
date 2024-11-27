package bus;

import dao.UserDAO;
import dto.UserDTO;
import java.util.ArrayList;
import java.util.Arrays;

public class UserBUS {
    private ArrayList<UserDTO> userList;
    
    public UserBUS() {
        
    }

    public ArrayList<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserDTO> userList) {
        this.userList = userList;
    }
    
    public void list() {
        UserDAO userDAO = new UserDAO();
        userList = new ArrayList<>();
        userList = userDAO.list();
    }
    
    public UserDTO checkUser(String userId, char[] password) {
        for (UserDTO user : userList) {
            char[] correctPass = user.getPassword().toCharArray();
            if (user.getIdUser().equals(userId) && Arrays.equals(password, correctPass) && user.isEnable()) {
                return user;
            }
        }
        return null;
    }
    
    public UserDTO checkId(String userId) {
        for (UserDTO user : userList) {
            if (user.getIdUser().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
    public void updateUser(UserDTO user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getIdUser().equals(user.getIdUser())) {
                userList.set(i, user);
                UserDAO userDAO = new UserDAO();
                userDAO.updateDB(user);
                return;
            }
        }
    }
    
    public void addUser(UserDTO user) {
        userList.add(user);
        UserDAO userDAO = new UserDAO();
        userDAO.addDB(user);
    }
    
    public void deleteUser(String id) {
        for (UserDTO user : userList) {
            if (user.getIdUser().equals(id)) {
                user.setEnable(false);
                UserDAO userDAO = new UserDAO();
                userDAO.deleteDB(id);
                return;
            }
        }
    }
    
    public String createNewId() {
        String id = "US";
        int new_id = userList.size() + 1;
        
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
    
    public ArrayList<UserDTO> filter(String gioiTinh, String quyen) {
        ArrayList<UserDTO> res = new ArrayList<>();
        
        for (UserDTO user : userList) {
            if (user.getGioiTinh().contains(gioiTinh) && user.getQuyen().contains(quyen)) {
                res.add(user);
            }
        }
        
        return res;
    }
}
