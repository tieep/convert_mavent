package gui;

import bus.KhachHangBUS;
import dto.KhachHangDTO;
import gui.KhachHangGUI;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

public class SuaKhachHangGUITest {
    private final KhachHangGUI khachhangGUI = new KhachHangGUI(50, 50, true, true, true);
   

    //Yêu cầu: Địa chỉ giới hạn từ 10 đến 50 kí tự
    //Test case 1: Kiểm tra địa chỉ khi thực hiện bỏ trống ô địa chỉ
    @Test
    public void test1() {
        String inputDiaChi = "";
        boolean result = inputDiaChi.length() >= 10 && inputDiaChi.length() <= 50;
        System.out.println("TC: Dự kiến trả về: false" + "| Thực tế trả về: " + result);
        assertFalse(result,"Địa chỉ giới hạn từ 10 đến 50 kí tự");
    }
    
    //Yêu cầu: Địa chỉ giới hạn từ 10 đến 50 kí tự
    //Test case 2: Kiểm tra địa chỉ khi thực hiện nhập dữ liệu hợp lệ
    @Test
    public void test2() {
        String inputDiaChi = "Phong Xích Lan";
        boolean result = inputDiaChi.length() >= 10 && inputDiaChi.length() <= 50;
        System.out.println("TC2: Dự kiến trả về: true" + "| Thực tế trả về: " + result);
        assertTrue(result,"Địa chỉ giới hạn từ 10 đến 50 kí tự");
    }
    
    //Yêu cầu: Sau khi chỉnh sửa, thông tin khách hàng phải được cập nhật trên database
    //Test case 3: Thực hiện chỉnh sửa khách hàng, kiểm tra thông tin địa chỉ của khách hàng trước và sau khi chỉnh sửa
    @Test
    public void test3(){
        String idKH = "KH001";
        KhachHangDTO old = khachhangGUI.getKhachHangBUS().getKhachHangByID(idKH);
        String updateDiaChi = "Phan Xích Long";
        if(!updateDiaChi.isEmpty()){
            KhachHangDTO update = new KhachHangDTO(old.getIdKhachHang(), old.getTenKhachHang(),
                    updateDiaChi,
                    old.getSdt(), true );
            khachhangGUI.getKhachHangBUS().updateKhachHang(update);
        }
        KhachHangDTO updated = khachhangGUI.getKhachHangBUS().getKhachHangByID(idKH);
        String updatedDiaChi = updated.getDiachi();
        boolean result = updatedDiaChi.equals(updateDiaChi);
        System.out.println("TC3: Dự kiến trả về: true" + "| Thực tế trả về: " + result);
        assertEquals(updatedDiaChi,updateDiaChi,"Sau khi chỉnh sửa, thông tin đã được cập nhật");
    }
    
}
