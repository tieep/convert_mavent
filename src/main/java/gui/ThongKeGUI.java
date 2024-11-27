package gui;

import bus.CTHoaDonBUS;
import bus.HoaDonBUS;
import bus.KhachHangBUS;
import bus.NhaCungCapBUS;
import bus.PhieuNhapBUS;
import bus.SanPhamBUS;
import bus.UserBUS;
import com.toedter.calendar.JDateChooser;
import dto.CTHoaDonDTO;
import dto.KhachHangDTO;
import dto.NhaCungCapDTO;
import dto.SanPhamDTO;
import dto.UserDTO;
import gui.model.IconModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ThongKeGUI extends JPanel {
    private int width, height;
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private UserBUS userBUS = new UserBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private CTHoaDonBUS cthdBUS = new CTHoaDonBUS();
    
    private JPanel pnTop, pnFilter, pnChart;
    private JComboBox cbTieuChi, cbLocTheo;
    private ChartPanel chart;
    
    public ThongKeGUI(int width, int height) {
        this.width = width;
        this.height = height;
        this.init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        this.setBackground(this.colorBackground);
        
        this.pnTop = this.createPnTop();
        this.pnFilter = this.createPnFilter();
        this.pnChart = this.createPnChart();
        
        this.setLayout(new BorderLayout());
        this.add(this.pnTop, BorderLayout.NORTH);
        this.add(this.pnFilter, BorderLayout.CENTER);
        this.add(this.pnChart, BorderLayout.SOUTH);
    }
    
    public JPanel createPnTop() {
        JPanel pn_top = new JPanel(new FlowLayout(1, 70, 50));
        pn_top.setPreferredSize(new Dimension(this.width, 200));
        
        JPanel pn_sp = new JPanel(new FlowLayout(1));
        JPanel pn_nv = new JPanel(new FlowLayout(1));
        JPanel pn_kh = new JPanel(new FlowLayout(1));
        JPanel pn_ncc = new JPanel(new FlowLayout(1));
        
        Dimension d = new Dimension(150, 100);
        pn_sp.setPreferredSize(d);
        pn_nv.setPreferredSize(d);
        pn_kh.setPreferredSize(d);
        pn_ncc.setPreferredSize(d);
        
        Font font_heading = new Font("Segoe UI", Font.BOLD, 15);
        Font font_tong = new Font("Segoe UI", Font.BOLD, 23);
        Dimension d_heading = new Dimension(150, 30);
        Dimension d_tong = new Dimension(150, 30);
        
        // Tổng số sản phẩm
        JLabel lb_so_sp = new JLabel("Số sản phẩm", JLabel.CENTER);
        JLabel lb_sp = new JLabel(this.tongSoSanPham()+"", JLabel.CENTER);
        
        lb_so_sp.setPreferredSize(d_heading);
        lb_sp.setPreferredSize(d_tong);
        lb_so_sp.setFont(font_heading);
        lb_sp.setFont(font_heading);
        lb_so_sp.setForeground(this.colorBackground);
        lb_sp.setForeground(this.colorBackground);
        
        JLabel lb_icon_sp = new JLabel("", JLabel.CENTER);
        lb_icon_sp.setPreferredSize(new Dimension(20, 20));
        IconModel icon_sp = new IconModel(20, 20, "laptop-white.png");
        lb_icon_sp.setIcon(icon_sp.createIcon());
        
        pn_sp.setBackground(this.color2);
        pn_sp.add(lb_icon_sp);
        pn_sp.add(lb_so_sp);
        pn_sp.add(lb_sp);
        
        // Tổng số nhân viên
        JLabel lb_so_nv = new JLabel("Số nhân viên", JLabel.CENTER);
        JLabel lb_nv = new JLabel(this.tongNhanVien()+"", JLabel.CENTER);
        
        lb_so_nv.setPreferredSize(d_heading);
        lb_nv.setPreferredSize(d_tong);
        lb_so_nv.setFont(font_heading);
        lb_nv.setFont(font_heading);
        lb_so_nv.setForeground(this.colorBackground);
        lb_nv.setForeground(this.colorBackground);
        
        JLabel lb_icon_nv = new JLabel("", JLabel.CENTER);
        lb_icon_nv.setPreferredSize(new Dimension(20, 20));
        IconModel icon_nv = new IconModel(20, 20, "employee-white.png");
        lb_icon_nv.setIcon(icon_nv.createIcon());
        
        pn_nv.setBackground(this.color2);
        pn_nv.add(lb_icon_nv);
        pn_nv.add(lb_so_nv);
        pn_nv.add(lb_nv);
        
        
        // Tổng số khách hàng
        JLabel lb_so_kh = new JLabel("Số khách hàng", JLabel.CENTER);
        JLabel lb_kh = new JLabel(this.tongNhanVien()+"", JLabel.CENTER);
        
        lb_so_kh.setPreferredSize(d_heading);
        lb_kh.setPreferredSize(d_tong);
        lb_so_kh.setFont(font_heading);
        lb_kh.setFont(font_heading);
        lb_so_kh.setForeground(this.colorBackground);
        lb_kh.setForeground(this.colorBackground);
        
        JLabel lb_icon_kh = new JLabel("", JLabel.CENTER);
        lb_icon_kh.setPreferredSize(new Dimension(20, 20));
        IconModel icon_kh = new IconModel(20, 20, "people-white.png");
        lb_icon_kh.setIcon(icon_kh.createIcon());
        
        pn_kh.setBackground(this.color2);
        pn_kh.add(lb_icon_kh);
        pn_kh.add(lb_so_kh);
        pn_kh.add(lb_kh);
        
        // Tổng số nhà cung cấp
        JLabel lb_so_ncc = new JLabel("Số nhà cung cấp", JLabel.CENTER);
        JLabel lb_ncc = new JLabel(this.tongNhanVien()+"", JLabel.CENTER);
        
        lb_so_ncc.setPreferredSize(d_heading);
        lb_ncc.setPreferredSize(d_tong);
        lb_so_ncc.setFont(font_heading);
        lb_ncc.setFont(font_heading);
        lb_so_ncc.setForeground(this.colorBackground);
        lb_ncc.setForeground(this.colorBackground);
        
        JLabel lb_icon_ncc = new JLabel("", JLabel.CENTER);
        lb_icon_ncc.setPreferredSize(new Dimension(20, 20));
        IconModel icon_ncc = new IconModel(20, 20, "delivery-white.png");
        lb_icon_ncc.setIcon(icon_ncc.createIcon());
        
        pn_ncc.setBackground(this.color2);
        pn_ncc.add(lb_icon_ncc);
        pn_ncc.add(lb_so_ncc);
        pn_ncc.add(lb_ncc);
        
        pn_top.add(pn_sp);
        pn_top.add(pn_nv);
        pn_top.add(pn_kh);
        pn_top.add(pn_ncc);
        
        return pn_top;
    }
    
    public JPanel createPnFilter() {
        JPanel pn_filter = new JPanel(new FlowLayout(1, 20, 20));
        
        // Tiêu chí 
        JLabel lb_tieu_chi = new JLabel("Tiêu chí lọc", JLabel.CENTER);
        
        String[] tieu_chi = {
            "Tổng doanh số", "Doanh số nhập", "Doanh số bán", "Top 3 sản phẩm bán chạy", "Top 5 sản phẩm bán chạy"
        };
        cbTieuChi = new JComboBox(tieu_chi);
        cbTieuChi.setPreferredSize(new Dimension(200, 30));
        
        
        // Lọc theo tháng/năm
        JLabel lb_loc_theo = new JLabel("Lọc theo", JLabel.CENTER);
        
        String[] loc_theo = {
            "Tháng", "Năm"
        };
        cbLocTheo = new JComboBox(loc_theo);
        cbLocTheo.setPreferredSize(new Dimension(100, 30));
        
        // Lọc từ - đến
        JLabel lb_tu = new JLabel("Từ", JLabel.CENTER);
        JLabel lb_den = new JLabel("Đến", JLabel.CENTER);
        
        JDateChooser date_begin = new JDateChooser();
        JDateChooser date_end = new JDateChooser();
                        
        date_begin.setPreferredSize(new Dimension(150, 30));
        date_end.setPreferredSize(new Dimension(150, 30));
        
        
        // Nút lọc 
        JButton btn_loc = new JButton("Lọc");
        btn_loc.setPreferredSize(new Dimension(150, 30));
        btn_loc.setForeground(this.colorBackground);
        btn_loc.setBackground(this.color2);
        
        Font font_filter = new Font("Segoe UI", Font.BOLD, 13);
        lb_tieu_chi.setFont(font_filter);
        cbTieuChi.setFont(font_filter);
        lb_loc_theo.setFont(font_filter);
        cbLocTheo.setFont(font_filter);
        lb_tu.setFont(font_filter);
        date_begin.setFont(font_filter);
        lb_den.setFont(font_filter);
        date_end.setFont(font_filter);
        btn_loc.setFont(font_filter);
        
        lb_tieu_chi.setForeground(this.color1);
        cbTieuChi.setForeground(this.color1);
        lb_loc_theo.setForeground(this.color1);
        cbLocTheo.setForeground(this.color1);
        lb_tu.setForeground(this.color1);
        date_begin.setForeground(this.color1);
        lb_den.setForeground(this.color1);
        date_end.setForeground(this.color1);
        
        // Khi ấn nút lọc
        btn_loc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Date input1 = date_begin.getDate();
                Date input2 = date_end.getDate();
                if (input1 == null || input2 == null || input1.after(input2)) {
                    JOptionPane.showMessageDialog(null, "Khoảng thời gian không hợp lệ");
                    return;
                }
                LocalDate dateBegin = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dateEnd = input2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                createChart(dateBegin, dateEnd);
            }
        });
        
        pn_filter.add(lb_tieu_chi);
        pn_filter.add(cbTieuChi);
        pn_filter.add(lb_loc_theo);
        pn_filter.add(cbLocTheo);
        pn_filter.add(lb_tu);
        pn_filter.add(date_begin);
        pn_filter.add(lb_den);
        pn_filter.add(date_end);
        pn_filter.add(btn_loc);
         
        return pn_filter;
    }
    
    public JPanel createPnChart() {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 70, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Tháng", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
                
        return pn_chart; 
    }
        
    public int tongSoSanPham() {
        if (sanPhamBUS.getSpList() == null) {
            sanPhamBUS.list();
        }
        int sum = 0;
        
        for (SanPhamDTO sp : sanPhamBUS.getSpList()) {
            if (sp.isEnable()) {
                sum++;
            }
        }
        
        return sum;
    }
    
    public int tongNhanVien() {
        if (userBUS.getUserList() == null) {
            userBUS.list();
        }
        int sum = 0;
        
        for (UserDTO user : userBUS.getUserList()) {
            if (user.isEnable()) {
                sum++;
            }
        }
        
        return sum;
    }
    
    public int tongKhachHang() {
        if (khachHangBUS.getKhList() == null) {
            khachHangBUS.list();
        }
        int sum = 0;
        
        for (KhachHangDTO kh : khachHangBUS.getKhList()) {
            if (kh.isEnable()) {
                sum++;
            }
        }
        
        return sum;
    }
    
    public int tongNhaCungCap() {
        if (nhaCungCapBUS.getNccList() == null) {
            nhaCungCapBUS.list();
        }
        int sum = 0;
        
        for (NhaCungCapDTO ncc : nhaCungCapBUS.getNccList()) {
            if (ncc.isEnable()) {
                sum++;
            }
        }
        
        return sum;
    }
    
    public void createChart(LocalDate dateBegin, LocalDate dateEnd) {
        String tieu_chi = (String) cbTieuChi.getItemAt(cbTieuChi.getSelectedIndex());
        String loc_theo = (String) cbLocTheo.getItemAt(cbLocTheo.getSelectedIndex());
        
        switch(tieu_chi) {
            case "Tổng doanh số":
                switch (loc_theo) {
                    case "Tháng":
                        pnChart.removeAll();
                        pnChart.add(chartDoanhSoTongThang(dateBegin, dateEnd));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                    case "Năm":
                        pnChart.removeAll();
                        pnChart.add(chartDoanhSoTongNam(dateBegin, dateEnd));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                }
                break;
            case "Doanh số nhập":
                switch (loc_theo) {
                    case "Tháng":
                        pnChart.removeAll();
                        pnChart.add(chartDoanhSoNhapThang(dateBegin, dateEnd));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                    case "Năm":
                        pnChart.removeAll();
                        pnChart.add(chartDoanhSoNhapNam(dateBegin, dateEnd));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                }
                break;
            case "Doanh số bán":
                switch (loc_theo) {
                    case "Tháng":
                        pnChart.removeAll();
                        pnChart.add(chartDoanhSoBanThang(dateBegin, dateEnd));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                    case "Năm":
                        pnChart.removeAll();
                        pnChart.add(chartDoanhSoBanNam(dateBegin, dateEnd));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                }
                break;
            case "Top 3 sản phẩm bán chạy":
                switch (loc_theo) {
                    case "Tháng":
                        pnChart.removeAll();
                        pnChart.add(chartSanPhamBanChayThang(dateBegin, dateEnd, 3));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                    case "Năm":
                        pnChart.removeAll();
                        pnChart.add(chartSanPhamBanChayNam(dateBegin, dateEnd, 3));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                }
                break;
            case "Top 5 sản phẩm bán chạy":
                switch (loc_theo) {
                    case "Tháng":
                        pnChart.removeAll();
                        pnChart.add(chartSanPhamBanChayThang(dateBegin, dateEnd, 5));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                    case "Năm":
                        pnChart.removeAll();
                        pnChart.add(chartSanPhamBanChayNam(dateBegin, dateEnd, 5));
                        pnChart.repaint();
                        pnChart.validate();
                        break;
                }
                break;
        }
    }
    
    public JPanel chartDoanhSoNhapThang(LocalDate dateBegin, LocalDate dateEnd) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (phieuNhapBUS.getPnList() == null) {
            phieuNhapBUS.list();
        }
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusMonths(1)) {
            dataset.addValue(Double.valueOf(phieuNhapBUS.tinhTienNhap(date, date.plusMonths(1))), "Tiền nhập", date.getMonth()+ "-" +date.getYear());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Tháng", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartDoanhSoNhapNam(LocalDate dateBegin, LocalDate dateEnd) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (phieuNhapBUS.getPnList() == null) {
            phieuNhapBUS.list();
        }
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusYears(1)) {
            dataset.addValue(Double.valueOf(phieuNhapBUS.tinhTienNhap(date, date.plusYears(1))), "Tiền nhập", date.getYear()+"");
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Năm", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartDoanhSoBanThang(LocalDate dateBegin, LocalDate dateEnd) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (hoaDonBUS.getHdList()== null) {
            hoaDonBUS.list();
        }
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusMonths(1)) {
            dataset.addValue(Double.valueOf(hoaDonBUS.tinhTienBan(date, date.plusMonths(1))), "Tiền bán", date.getMonth()+ "-" +date.getYear());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Tháng", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartDoanhSoBanNam(LocalDate dateBegin, LocalDate dateEnd) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (hoaDonBUS.getHdList()== null) {
            hoaDonBUS.list();
        }
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusYears(1)) {
            dataset.addValue(Double.valueOf(hoaDonBUS.tinhTienBan(date, date.plusYears(1))), "Tiền bán", date.getYear()+"");
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Năm", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartDoanhSoTongThang(LocalDate dateBegin, LocalDate dateEnd) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (phieuNhapBUS.getPnList() == null) {
            phieuNhapBUS.list();
        }
        if (hoaDonBUS.getHdList()== null) {
            hoaDonBUS.list();
        }
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusMonths(1)) {
            Double tien_ban = Double.valueOf(hoaDonBUS.tinhTienBan(date, date.plusMonths(1)));
            Double tien_nhap =  Double.valueOf(phieuNhapBUS.tinhTienNhap(date, date.plusMonths(1)));
            dataset.addValue(tien_ban - tien_nhap, "Tổng doanh thu", date.getMonth()+ "-" +date.getYear());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Tháng", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartDoanhSoTongNam(LocalDate dateBegin, LocalDate dateEnd) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (phieuNhapBUS.getPnList() == null) {
            phieuNhapBUS.list();
        }
        if (hoaDonBUS.getHdList()== null) {
            hoaDonBUS.list();
        }
        
        // Tạo dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusYears(1)) {
            Double tien_ban = Double.valueOf(hoaDonBUS.tinhTienBan(date, date.plusYears(1)));
            Double tien_nhap =  Double.valueOf(phieuNhapBUS.tinhTienNhap(date, date.plusYears(1)));
            dataset.addValue(tien_ban - tien_nhap, "Tổng doanh thu", date.getYear()+"");
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Năm", "Tiền (USD)", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartSanPhamBanChayThang(LocalDate dateBegin, LocalDate dateEnd, int top) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (hoaDonBUS.getHdList() == null) {
            hoaDonBUS.list();
        }
        if (cthdBUS.getCthdList() == null) {
            cthdBUS.list();
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusMonths(1)) {
            ArrayList<String> arr_hd = hoaDonBUS.getArrHD(dateBegin, date.plusMonths(1));
            Map<String, Integer> map_sp = new LinkedHashMap<>();
            int tong_sl = 0;
            for (String id_hd : arr_hd) {
                for (CTHoaDonDTO cthd : cthdBUS.getCthdList()) {
                    if (id_hd.equals(cthd.getIdHoaDon())) {
                        tong_sl += cthd.getSoLuong();
                        map_sp.put(cthd.getIdSanPham(), tong_sl);
                    }
                }
            }
            // Lấy ra top sản phẩm bán chạy
            if (map_sp.isEmpty()) {
                dataset.addValue(0, "", date.getMonth()+ "-" +date.getYear());
                continue;
            } 
            else if (map_sp.size() < top) {
                for (Map.Entry<String, Integer> entry : map_sp.entrySet()) {
                    dataset.addValue(entry.getValue(), entry.getKey(), date.getMonth()+ "-" +date.getYear());
                }
                continue;
            }
            for (int i = 0; i < top; i++) {
                int top_sl = Collections.max(map_sp.values());
                for (Map.Entry<String, Integer> entry : map_sp.entrySet()) {
                    if (entry.getValue() == top_sl) {
                        dataset.addValue(entry.getValue(), entry.getKey(), date.getMonth()+ "-" +date.getYear());
                        map_sp.remove(entry.getKey());
                        break;
                    }
                }
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Tháng", "Số lượng bán", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
    
    public JPanel chartSanPhamBanChayNam(LocalDate dateBegin, LocalDate dateEnd, int top) {
        JPanel pn_chart = new JPanel(new FlowLayout(1, 10, 10));
        pn_chart.setPreferredSize(new Dimension(this.width, 370));
        
        if (hoaDonBUS.getHdList() == null) {
            hoaDonBUS.list();
        }
        if (cthdBUS.getCthdList() == null) {
            cthdBUS.list();
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for (LocalDate date = dateBegin; date.isBefore(dateEnd); date = date.plusYears(1)) {
            ArrayList<String> arr_hd = hoaDonBUS.getArrHD(dateBegin, date.plusYears(1));
            Map<String, Integer> map_sp = new LinkedHashMap<>();
            int tong_sl = 0;
            for (String id_hd : arr_hd) {
                for (CTHoaDonDTO cthd : cthdBUS.getCthdList()) {
                    if (id_hd.equals(cthd.getIdHoaDon())) {
                        tong_sl += cthd.getSoLuong();
                        map_sp.put(cthd.getIdSanPham(), tong_sl);
                    }
                }
            }
            // Lấy ra top sản phẩm bán chạy
            if (map_sp.isEmpty()) {
                dataset.addValue(0, "", date.getYear() + "");
                continue;
            }
             else if (map_sp.size() < top) {
                for (Map.Entry<String, Integer> entry : map_sp.entrySet()) {
                    dataset.addValue(entry.getValue(), entry.getKey(), date.getYear() + "");
                }
                continue;
            }
            for (int i = 0; i < top; i++) {
                int top_sl = Collections.max(map_sp.values());
                for (Map.Entry<String, Integer> entry : map_sp.entrySet()) {
                    if (entry.getValue() == top_sl) {
                        dataset.addValue(entry.getValue(), entry.getKey(), date.getYear() + "");
                        map_sp.remove(entry.getKey());
                        break;
                    }
                }
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "", "Năm", "Số lượng bán", dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        pn_chart.add(chartPanel);
        
        return pn_chart;
    }
}
