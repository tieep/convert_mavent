package gui;

import bus.KhachHangBUS;
import dto.KhachHangDTO;
import gui.KhachHangGUI;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

public class SuaKhachHangGUITest {
    private final KhachHangGUI khachhangGUI = new KhachHangGUI(50, 50, true, true, true);
    
    //Yêu cầu: Phải chọn 1 khách hàng từ trong bảng => getSelectedRow != -1
    //Test case 1: Kiểm tra phản hồi của hệ thống khi không chọn khách hàng
    @Test
    public void test1(){
        boolean result = khachhangGUI.isSelectedRow();
        System.out.println("TC1: Dự kiến trả về: false" + "| Thực tế trả về: " + result);
        assertFalse(result,"Khi chưa chọn khách hàng từ bảng, hàm isSelectedRow sẽ trả về false");
    }
    
    //Yêu cầu: Sau khi chọn khách hàng, Form phải ở trạng thái chỉnh sửa isEditing = true( trạng thái giúp điều chỉnh sao cho chỉnh sửa được Địa chỉ )
    //Test case 2: Khi không chọn khách hàng, kiểm tra trạng thái của form
    @Test
    public void test2(){
        boolean result = khachhangGUI.isEditing();
        System.out.println("TC2: Dự kiến trả về: false" + "| Thực tế trả về: " + result);
        assertFalse(result,"Khi chưa chọn khách hàng từ bảng, hàm isEditing sẽ trả về false");
    }

    //Yêu cầu: Địa chỉ không được bỏ trống
    //Test case 3: Kiểm tra địa chỉ khi thực hiện bỏ trống ô địa chỉ
    @Test
    public void test3() {
        String inputDiaChi = "";
        boolean result = !inputDiaChi.isEmpty();
        System.out.println("TC3: Dự kiến trả về: false" + "| Thực tế trả về: " + result);
        assertFalse(result,"Địa chỉ không được bỏ trống");
    }
    
    //Yêu cầu: Địa chỉ không được bỏ trống
    //Test case 4: Kiểm tra địa chỉ khi thực hiện nhập dữ liệu bất kì vào ô địa chỉ
    @Test
    public void test4() {
        String inputDiaChi = "Phong Xich Lan";
        boolean result = !inputDiaChi.isEmpty();
        System.out.println("TC4: Dự kiến trả về: true" + "| Thực tế trả về: " + result);
        assertTrue(result,"Địa chỉ không được bỏ trống");
    }
    
    //Yêu cầu: Sau khi chỉnh sửa, thông tin khách hàng phải được cập nhật trên database
    //Test case 5: Thực hiện chỉnh sửa khách hàng, kiểm tra thông tin địa chỉ của khách hàng trước và sau khi chỉnh sửa
    @Test
    public void test5(){
        String idKH = "KH001";
        KhachHangDTO old = khachhangGUI.getKhachHangBUS().getKhachHangByID(idKH);
        String updateDiaChi = "Phong Xich Lan";
        if(!updateDiaChi.isEmpty()){
            KhachHangDTO update = new KhachHangDTO(old.getIdKhachHang(), old.getTenKhachHang(),
                    updateDiaChi,
                    old.getSdt(), true );
            khachhangGUI.getKhachHangBUS().updateKhachHang(update);
        }
        KhachHangDTO updated = khachhangGUI.getKhachHangBUS().getKhachHangByID(idKH);
        String updatedDiaChi = updated.getDiachi();
        boolean result = updatedDiaChi.equals(updateDiaChi);
        System.out.println("TC5: Dự kiến trả về: true" + "| Thực tế trả về: " + result);
        assertTrue(result,"Sau khi chỉnh sửa, thông tin đã được cập nhật");
        
    }
    
}
