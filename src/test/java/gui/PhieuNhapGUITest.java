/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gui;

/**
 *
 * @author hp
 */
import dto.PhieuNhapDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;

public class PhieuNhapGUITest {

    private final PhieuNhapGUI phieuNhapGUI = new PhieuNhapGUI(50, 50);

    @Test
    public void testEmptyInput1() {
        Date input1 = null;
        Date input2 = new Date();
        ArrayList<?> result = phieuNhapGUI.getSearchList(input1, input2);
        System.out.println("TC3: Dự kiến trả về: null " + "| Thực tế trả về: " + result);
        assertNull(result, "Khi input1 là null, hàm getSearchList phải trả về null.");
    }

    @Test
    public void testEmptyInput2() {
        Date input1 = new Date();
        Date input2 = null;
        ArrayList<?> result = phieuNhapGUI.getSearchList(input1, input2);
        System.out.println("TC4: Dự kiến trả về: null " + "| Thực tế trả về: " + result);
        assertNull(result, "Khi input2 là null, hàm getSearchList phải trả về null.");
    }

    @Test
    public void testInput1AfterInput2() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date input1 = formatter.parse("2024-11-05");
            Date input2 = formatter.parse("2024-11-04");
            ArrayList<?> result = phieuNhapGUI.getSearchList(input1, input2);
            System.out.println("TC5: Dự kiến trả về: null" + "| Thực tế trả về: " + result);
            assertNull(result, "Khi input1 trước input2, hàm getSearchList không được trả về null.");
        } catch (ParseException ex) {
            Logger.getLogger(PhieuNhapGUITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testInput1BeforeInput2() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date input1 = formatter.parse("2024-11-04");
            Date input2 = formatter.parse("2024-11-05");
            ArrayList<?> result = phieuNhapGUI.getSearchList(input1, input2);
            System.out.println("TC6: Dự kiến trả về: mảng đối tượng PhieuNhapDTO" + "| Thực tế trả về: " + result);
            assertNotNull(result, "Khi input1 trước input2, hàm getSearchList không được trả về null.");
        } catch (ParseException ex) {
            Logger.getLogger(PhieuNhapGUITest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testInput1EqualInput2() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date input1 = formatter.parse("2024-11-04");
            Date input2 = formatter.parse("2024-11-04");
            ArrayList<?> result = phieuNhapGUI.getSearchList(input1, input2);
            System.out.println("TC7: Dự kiến trả về: mảng đối tượng PhieuNhapDTO" + "| Thực tế trả về: " + result);
            assertNotNull(result, "Khi input1 trước input2, hàm getSearchList không được trả về null.");
        } catch (ParseException ex) {
            Logger.getLogger(PhieuNhapGUITest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Test
    public void testEmptyText() {
        String txt = "";
        int choice = 1;
        var result = phieuNhapGUI.createRowFilter(txt, choice);
        System.out.println("TC1: Dự kiến trả về: null " + "| Thực tế trả về: " + result);
        assertNull(result, "Khi txt rỗng hoặc null, hàm createRowFilter phải trả về null.");
    }

    @Test
    public void testOtherText() {
        String txt = "test";
        int choice = 2;
        var result = phieuNhapGUI.createRowFilter(txt, choice);
        System.out.println("TC2: Dự kiến trả về: đối tượng RowFilter " + "| Thực tế trả về: " + result);
        assertNotNull(result, "Khi txt không rỗng, hàm createRowFilter không được trả về null.");
    }

}