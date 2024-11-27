package gui;
import bus.BaoHanhBUS;
import bus.PhieuBaoHanhBUS;
import com.toedter.calendar.JDateChooser;
import dto.BaoHanhDTO;
import dto.CTHoaDonDTO;
import dto.CTSanPhamDTO;
import dto.HoaDonDTO;
import dto.KhachHangDTO;
import dto.PhieuBaoHanhDTO;
import dto.UserDTO;
import gui.model.IconModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class BaoHanhGUI  extends JPanel {
    private int width, height;
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    private BaoHanhBUS baoHanhBUS = new BaoHanhBUS();
    private PhieuBaoHanhBUS phieuBaoHanhBUS = new PhieuBaoHanhBUS();
    private ArrayList<JPanel> arrPnInfor;
    private ArrayList<JTextField> arrTfInfor;
    
    private JPanel pnInfor, pnFilter, pnTable;
    private JButton btn_baohanh;
    private ArrayList<JLabel> arrLbInfor;
     private ArrayList<PhieuBaoHanhDTO> arrPBH = new ArrayList<>();
    
    private JTable table, tableCT;
    private TableRowSorter<TableModel> rowSorter ,rowSorter1;
    private DefaultTableModel model, modelCT;
    
    private UserDTO user = new UserDTO();
    
    public BaoHanhGUI(int width, int height) {
        this.width = width;
        this.height = height;
        this.init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        this.setBackground(this.colorBackground);
        
        this.pnInfor = this.createPnInfor();
        this.pnFilter = this.createPnFilter();
        this.pnTable = this.createPnTable();
        
        this.setLayout(new BorderLayout());
        this.add(this.pnInfor, BorderLayout.NORTH);
        this.add(this.pnFilter, BorderLayout.CENTER);
        this.add(this.pnTable, BorderLayout.SOUTH);

    }
    
    public JPanel createPnInfor() {
        Font font_filter = new Font("Segoe UI", Font.BOLD, 13);
        JPanel result = new JPanel(new FlowLayout(1, 0, 10));
        result.setPreferredSize(new Dimension(this.width, 350));
        
        // phần thông tin phiếu nhập
        JPanel pn_infor = new JPanel(new FlowLayout(1, 5, 10));
        pn_infor.setPreferredSize(new Dimension(250, 310));
        pn_infor.setBorder(BorderFactory.createLineBorder(color1, 2));
        
//        loadBH();
        JLabel lb_baohanh = new JLabel("Bảo hành", JLabel.CENTER);
        lb_baohanh.setPreferredSize(new Dimension(150, 30));
        
        Font font_tong_tien_1 = new Font("Segoe UI", Font.BOLD, 17);
        lb_baohanh.setFont(font_tong_tien_1);
        lb_baohanh.setForeground(this.color1);
//        
//        this.lbTongTien = new JLabel("0");
//        this.lbTongTien.setForeground(this.color1);
        
        pn_infor.add(lb_baohanh);
//        pn_tong_tien.add(lbTongTien);
//
//        pn_btn.add(this.btnXoaSanPham);
//        pn_btn.add(this.btnTaoPhieuNhap);
 //       pn_btn.add(pn_tong_tien);
        
        String[] thuoc_tinh = {"Mã bảo hành", "Serial", "Tên sản phẩm", "Ngày bảo hành", "Ngày trả", "Khách hàng"};
        int len = thuoc_tinh.length;
        this.arrPnInfor = new ArrayList<>();
        this.arrLbInfor = new ArrayList<>();
        this.arrTfInfor = new ArrayList<>();
        
        Dimension d_pn = new Dimension(240, 25);
        Dimension d_lb = new Dimension(100, 25);
        Dimension d_tf = new Dimension(130, 25);
        Color color_font = this.color1;
        Font font_infor = new Font("Segoe UI", Font.PLAIN, 13);
        for (int i = 0; i < len; i++) {
            this.arrPnInfor.add(new JPanel(new FlowLayout(0, 0, 0)));
            this.arrPnInfor.get(i).setPreferredSize(d_pn);
            
            this.arrLbInfor.add(new JLabel(thuoc_tinh[i]));
            this.arrLbInfor.get(i).setPreferredSize(d_lb);
            this.arrTfInfor.add(new JTextField());
            this.arrTfInfor.get(i).setPreferredSize(d_tf);

            this.arrLbInfor.get(i).setForeground(color_font);
            this.arrLbInfor.get(i).setFont(font_infor);
            this.arrTfInfor.get(i).setForeground(color_font);
            this.arrTfInfor.get(i).setFont(font_infor);
            this.arrTfInfor.get(i).setEditable(false);
            
            this.arrPnInfor.get(i).add(this.arrLbInfor.get(i));
            this.arrPnInfor.get(i).add(this.arrTfInfor.get(i));
            pn_infor.add(this.arrPnInfor.get(i));
        }

//        this.arrTfInfor.get(0).setText(baoHanhBUS.createNewId());
        this.arrTfInfor.get(0).setText(this.user.getIdUser());
        this.arrTfInfor.get(2).setText(this.user.getIdUser());
        this.arrTfInfor.get(3).setText(LocalDate.now()+"");
        this.arrTfInfor.get(4).setText(LocalDate.now().plusDays(7).toString());
        
 //       this.arrTfInfor.get(1).setPreferredSize(new Dimension(100, 25));
//        this.btnChonNhaCungCap = new JButton("...");
//        this.btnChonNhaCungCap.setPreferredSize(new Dimension(25, 25));
//        this.btnChonNhaCungCap.setBackground(color3);
//        this.btnChonNhaCungCap.setFont(font_infor);
//        this.btnChonNhaCungCap.setForeground(color1);
//        this.btnChonNhaCungCap.addActionListener(this);
//        this.arrPnInfor.get(1).add(this.btnChonNhaCungCap);
        btn_baohanh = new JButton("Xác nhận");
        btn_baohanh.setPreferredSize(new Dimension(100, 30));
        btn_baohanh.setBackground(color2);
        btn_baohanh.setFont(font_filter);
        btn_baohanh.setForeground(this.colorBackground);
        pn_infor.add(btn_baohanh);
        
        
        
        btn_baohanh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (arrTfInfor.get(0).getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Bảo hành trống!");
                    return;
                }
                
                int confirmed = JOptionPane.showConfirmDialog(null, "Xác nhận bảo hành", "Alert", JOptionPane.YES_NO_OPTION);
                if (confirmed == 0) {
                    
                String id_bh = arrTfInfor.get(0).getText();
                String id_kh = arrTfInfor.get(5).getText();
                String ten_sp = arrTfInfor.get(2).getText();
                String serial = arrTfInfor.get(1).getText();
                LocalDate ngay_baohanh = LocalDate.now();
                LocalDate ngay_tramay = LocalDate.now().plusDays(7);
                
//                LocalDate ngay_hethan = null; // Khởi tạo biến bên ngoài vòng lặp
//                for (PhieuBaoHanhDTO pbh : arrPBH) {
//                    if (pbh != null && pbh.getSerial() != null && pbh.getSerial().equals(serial)) {
//                        ngay_hethan = pbh.getNgayHetHan();
//                        // Nếu bạn chỉ cần giá trị đầu tiên tìm thấy, có thể thoát khỏi vòng lặp
//                        break;
//                    }
//                }
//
//                // Kiểm tra sau vòng lặp nếu cần thiết
//                if (ngay_hethan == null) {
//                    System.out.println("Không tìm thấy serial tương ứng.");
//                } else {
//                    // sử dụng ngày hết hạn tại đây
//                }
//
//                
//                for (PhieuBaoHanhDTO pbh : arrPBH) {
//                    if(pbh.getSerial().equals(serial)){
//                        ngay_hethan = pbh.getNgayHetHan();
//                    }
//                }
//                String text = phieuBaoHanhBUS.check(ngay_baohanh);
                if(!(phieuBaoHanhBUS.checkTime(serial, ngay_baohanh))){
                    JOptionPane.showMessageDialog(null,"Phiếu bảo hành hết hạn !");
                    return;
                }
                
                if(!(baoHanhBUS.checkTime(serial, ngay_baohanh))){
                    JOptionPane.showMessageDialog(null,"Sản phẩm đang bảo hành !");
                    return;
                }
//
                BaoHanhDTO bh = new BaoHanhDTO(id_bh, id_kh, ten_sp, serial, ngay_baohanh, ngay_tramay);
                baoHanhBUS.addBH(bh);
                
                loadBH();
                }
            }
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                
//                int confirmed; // đang trong chế độ thêm
//                    confirmed = JOptionPane.showConfirmDialog(null, "Xác nhận bảo hành", "", JOptionPane.YES_NO_OPTION);
//                    
//                if (arrTfInfor.get(0).getText().isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Bảo hành trống!");
//                    return;
//                }
//                String id_bh = arrTfInfor.get(0).getText();
//                String id_kh = arrTfInfor.get(1).getText();
//                String ten_sp = arrTfInfor.get(2).getText();
//                String serial = arrTfInfor.get(3).getText();
//                LocalDate ngay_baohanh = LocalDate.now();
//                LocalDate ngay_tramay = LocalDate.now().plusDays(7);
////
//                BaoHanhDTO bh = new BaoHanhDTO(id_bh, id_kh, ten_sp, serial, ngay_baohanh, ngay_tramay);
//                baoHanhBUS.addBH(bh);
////
//                if (baoHanhBUS.getBhList() == null) {
//                     baoHanhBUS.list();
//                }
//////                        reloadKH(khachHangBUS.getKhList());
//////                        blankInfor();
//                    }
        });
        // Thanh chọn giá lời
//        JPanel pn_slider = new JPanel(new BorderLayout());
//        pn_slider.setPreferredSize(new Dimension(240, 50));
//        this.slider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
//        this.slider.setPreferredSize(new Dimension(200, 15));
//        this.slider.setMajorTickSpacing(20);
//        this.slider.setMinorTickSpacing(10);
//        this.slider.setPaintTicks(true);
//        this.slider.setPaintLabels(true);
//        this.slider.setLabelTable(slider.createStandardLabels(20));
//        this.slider.setFont(font_infor);
//        this.slider.setForeground(color_font);
//        
//        this.slider.addChangeListener(new ChangeListener() { 
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                arrTfInfor.get(4).setText(slider.getValue() + "%");
//                tinhGiaNhap(slider.getValue());
//                reloadCTPN();
//                lbTongTien.setText(String.valueOf(tinhTongTien()));
//            }        
//        });
//        
//        pn_slider.add(slider, BorderLayout.CENTER);
//        pn_infor.add(pn_slider);
        
        // phần bảng thông tin chi tiết hóa đơn
        JPanel pn_table = new JPanel(new BorderLayout());
        JPanel pn_timkiem_table = new JPanel(new FlowLayout(1));
        
        JLabel lb_tim_kiem = new JLabel("Tìm kiếm nhanh");
        lb_tim_kiem.setFont(font_filter);
        lb_tim_kiem.setForeground(color1);
        
        JTextField tf_tim_kiem = new JTextField();
        tf_tim_kiem.setPreferredSize(new Dimension(130, 30));
        tf_tim_kiem.setFont(font_filter);
        tf_tim_kiem.setForeground(color1);
        tf_tim_kiem.getDocument().addDocumentListener(new DocumentListener() { 
            @Override
            public void removeUpdate(DocumentEvent e) {
                String txt = tf_tim_kiem.getText();
                if (txt.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } 
                else if (txt.trim().length() >= 1 && txt.trim().substring(0, 1).toUpperCase().equals("B")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 1).toUpperCase().equals("KH")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt + ".*", 1));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                String txt = tf_tim_kiem.getText();
                if (txt.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } 
                else if (txt.trim().length() >= 1 && txt.trim().substring(0, 1).toUpperCase().equals("B")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 1).toUpperCase().equals("KH")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt + ".*", 1));
                }
            }


            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });

        pn_timkiem_table.add(lb_tim_kiem);
        pn_timkiem_table.add(tf_tim_kiem);
        
        JPanel pn_tablect = new JPanel(new FlowLayout(1));
        
        String[] col = {
            "Serial", "Hóa đơn", "Khách hàng", "Sản phẩm", "Ngày mua", "Ngày hết hạn"
        };
        
        model = new DefaultTableModel(col, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableCT = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tableCT.setModel(model);
        tableCT.setRowSorter(rowSorter);
        JScrollPane scroll = new JScrollPane(tableCT);
        scroll.setPreferredSize(new Dimension(600, 270));
        
        tableCT.getColumnModel().getColumn(0).setPreferredWidth(45);
        tableCT.getColumnModel().getColumn(1).setPreferredWidth(30);
        tableCT.getColumnModel().getColumn(2).setPreferredWidth(40);
        tableCT.getColumnModel().getColumn(3).setPreferredWidth(60);
        tableCT.getColumnModel().getColumn(4).setPreferredWidth(45);
        tableCT.getColumnModel().getColumn(5).setPreferredWidth(55);
        this.loadPBH();
        
        pn_table.add(pn_timkiem_table,BorderLayout.NORTH);
        pn_table.add(pn_tablect,BorderLayout.CENTER);
        pn_tablect.add(scroll);
        
        tableCT.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableCT.getSelectedRow();
                if (tableCT.getRowSorter() != null) {
                    row = tableCT.getRowSorter().convertRowIndexToModel(row);
                }
//                imgSanPham = tableCT.getModel().getValueAt(row, 6).toString();
//                IconModel icon_sp = new IconModel(175, 200, "SanPham/" + imgSanPham);
                
                // set thông tin cho sản phẩm
                arrTfInfor.get(0).setText(baoHanhBUS.createNewId());
                arrTfInfor.get(1).setText(tableCT.getModel().getValueAt(row, 0).toString());
                arrTfInfor.get(2).setText(tableCT.getModel().getValueAt(row, 3).toString());
                arrTfInfor.get(5).setText(tableCT.getModel().getValueAt(row, 2).toString());
//                arrTfInfor.get(4).setText(tableCT.getModel().getValueAt(row, 5).toString());
//                cbBrand.setSelectedItem(tableCT.getModel().getValueAt(row, 1).toString());
//                lbImgSanPham.setText("");
//                lbImgSanPham.setIcon(icon_sp.createIcon());
                
//                if (isEditing) {
//                    lockInfor(false);
//                }
//                else lockInfor(true);
            }
        });
        /////////////////
        Font font_table = new Font("Segoe UI", Font.BOLD, 13);
        tableCT.getTableHeader().setBackground(color1);
        tableCT.getTableHeader().setFont(font_table);
        tableCT.getTableHeader().setForeground(this.colorBackground);
        tableCT.getTableHeader().setOpaque(false); 
        tableCT.getTableHeader().setBorder(BorderFactory.createLineBorder(this.color1));
        
        // căn giữa các chữ trong ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < col.length; i++) {
            tableCT.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        tableCT.setFocusable(false);
        tableCT.setShowVerticalLines(false);
        tableCT.setIntercellSpacing(new Dimension(0, 0));
        tableCT.setFillsViewportHeight(true);
        tableCT.setSelectionBackground(color3);
        tableCT.setRowHeight(30);
        tableCT.setBorder(BorderFactory.createLineBorder(this.color1));
        
        // phần thông tin các nút
//        JPanel pn_btn = new JPanel(new FlowLayout(1, 5, 5));
//        pn_btn.setPreferredSize(new Dimension(80, 250));
        
//        this.btnXoaSanPham = new JButton("Xóa sản phẩm");
//        this.btnTaoPhieuNhap = new JButton("Tạo phiếu nhập");
//        this.btnXoaSanPham.setPreferredSize(new Dimension(140, 30));
//        this.btnTaoPhieuNhap.setPreferredSize(new Dimension(140, 30));
//        this.btnXoaSanPham.setBackground(this.color2);
//        this.btnTaoPhieuNhap.setBackground(this.color2);
//        this.btnXoaSanPham.setForeground(this.colorBackground);
//        this.btnTaoPhieuNhap.setForeground(this.colorBackground);
        //Font font_btn = new Font("Segoe UI", Font.BOLD, 13);
//        this.btnXoaSanPham.setFont(font_btn);
//        this.btnTaoPhieuNhap.setFont(font_btn);
//        
//        this.btnXoaSanPham.addActionListener(this);
//        this.btnTaoPhieuNhap.addActionListener(this);
//        
//        this.btnTaoPhieuNhap.setVisible(quyenThem);
        
//        JPanel pn_tong_tien = new JPanel(new FlowLayout(1, 5, 10));
//        pn_tong_tien.setPreferredSize(new Dimension(150, 170));
//        pn_tong_tien.setBorder(BorderFactory.createLineBorder(this.color1, 2));
//        JLabel lb_tong_tien = new JLabel("Tổng tiền", JLabel.CENTER);
//        lb_tong_tien.setPreferredSize(new Dimension(150, 30));
//        
//        Font font_tong_tien_1 = new Font("Segoe UI", Font.BOLD, 17);
//        lb_tong_tien.setFont(font_tong_tien_1);
//        lb_tong_tien.setForeground(this.color1);
////        
////        this.lbTongTien = new JLabel("0");
////        this.lbTongTien.setForeground(this.color1);
//        
//        pn_tong_tien.add(lb_tong_tien);
////        pn_tong_tien.add(lbTongTien);
////
////        pn_btn.add(this.btnXoaSanPham);
////        pn_btn.add(this.btnTaoPhieuNhap);
//        pn_btn.add(pn_tong_tien);
        
        result.add(pn_infor);
        result.add(pn_table);
//        result.add(pn_btn);
        
        return result;
    }
    
    public JPanel createPnFilter() {
        JPanel pn_filter = new JPanel(new FlowLayout(1, 10, 0));
        
        Font font_filter = new Font("Segoe UI", Font.BOLD, 13);
        JLabel lb_tim_kiem = new JLabel("Tìm kiếm");
        lb_tim_kiem.setFont(font_filter);
        lb_tim_kiem.setForeground(color1);
        
        JPanel pn_tim_kiem = new JPanel(new FlowLayout(1, 0, 0));
        pn_tim_kiem.setPreferredSize(new Dimension(200, 30));
//        JComboBox cb_tim_kiem = new JComboBox();
//        cb_tim_kiem.setPreferredSize(new Dimension(140, 30));
//        cb_tim_kiem.addItem("Mã hóa đơn");
//        cb_tim_kiem.addItem("Mã khách");
//        cb_tim_kiem.addItem("Mã nhân viên");
//        cb_tim_kiem.setForeground(color1);
//        cb_tim_kiem.setBackground(colorBackground);
//        cb_tim_kiem.setFont(font_filter);
        
        JTextField tf_tim_kiem1 = new JTextField();
        tf_tim_kiem1.setPreferredSize(new Dimension(150, 30));
        tf_tim_kiem1.setFont(font_filter);
        tf_tim_kiem1.setForeground(color1);
        
        tf_tim_kiem1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                String txt = tf_tim_kiem1.getText();
                if (txt.trim().length() == 0) {
                    rowSorter1.setRowFilter(null);
                } 
                else if (txt.trim().length() >= 1 && txt.trim().substring(0, 1).toUpperCase().equals("B")) {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 1).toUpperCase().equals("KH")) {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)^" + txt + ".*", 1));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                String txt = tf_tim_kiem1.getText();
                if (txt.trim().length() == 0) {
                    rowSorter1.setRowFilter(null);
                } 
                else if (txt.trim().length() >= 1 && txt.trim().substring(0, 1).toUpperCase().equals("B")) {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 1).toUpperCase().equals("KH")) {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)^" + txt + ".*", 1));
                }
            }


            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        pn_tim_kiem.add(lb_tim_kiem);
        pn_tim_kiem.add(tf_tim_kiem1);
        
        // lọc theo ngày
        JLabel lb_ngay = new JLabel("Ngày bảo hành", JLabel.CENTER);
        lb_ngay.setFont(font_filter);
        lb_ngay.setForeground(color1);
        
        JDateChooser date_from = new JDateChooser();
        JDateChooser date_to = new JDateChooser();
        
        date_from.setPreferredSize(new Dimension(150, 30));
        date_to.setPreferredSize(new Dimension(150, 30));
        
        JSeparator sep1 = new JSeparator(JSeparator.VERTICAL);
        sep1.setPreferredSize(new Dimension(10, 40));
        JSeparator sep2 = new JSeparator(JSeparator.HORIZONTAL);
        sep2.setPreferredSize(new Dimension(20, 10));
        
        JButton btn_loc = new JButton("Lọc");
        btn_loc.setPreferredSize(new Dimension(100, 30));
        btn_loc.setBackground(color2);
        btn_loc.setFont(font_filter);
        btn_loc.setForeground(this.colorBackground);
        
         btn_loc.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseClicked(MouseEvent e) {
                Date input1 = date_from.getDate();
                Date input2 = date_to.getDate();
                LocalDate date1 = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate date2 = input2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                reloadBH(baoHanhBUS.filter(date1, date2));
            }
        });
        
        pn_filter.add(lb_tim_kiem);
        pn_filter.add(pn_tim_kiem);
        pn_filter.add(sep1);
        pn_filter.add(lb_ngay);
        pn_filter.add(date_from);
        pn_filter.add(sep2);
        pn_filter.add(date_to);
        pn_filter.add(btn_loc);
        
        return pn_filter;
    }
    
    public JPanel createPnTable() {
        JPanel pn_table = new JPanel();
        pn_table.setPreferredSize(new Dimension(this.width, 250));
        
        String[] col = {
            "Mã bảo hành", "Khách hàng", "Tên sản phẩm", "Serial", "Ngày bảo hành", "Ngày trả máy"};
        this.model = new DefaultTableModel(col, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);
        rowSorter1 = new TableRowSorter<TableModel>(model);
        this.table.setModel(model);
        this.table.setRowSorter(rowSorter1);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(900, 240));
        
        
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(175);
        table.getColumnModel().getColumn(5).setPreferredWidth(175);
        
        this.loadBH();
        
        pn_table.add(scroll);
        
//        table.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                int row = table.getSelectedRow();
//                if (table.getRowSorter() != null) {
//                    row = table.getRowSorter().convertRowIndexToModel(row);
//                }
//                
//                // set thông tin cho sản phẩm
//                arrLbInfor.get(0).setText("Mã khách hàng " + table.getModel().getValueAt(row, 0).toString());
//                arrLbInfor.get(1).setText("Tên sản phẩm" + table.getModel().getValueAt(row, 1).toString());
//                arrLbInfor.get(2).setText("Serial" + table.getModel().getValueAt(row, 2).toString());
//                arrLbInfor.get(3).setText("Ngày mua" + table.getModel().getValueAt(row, 3).toString());
//                arrLbInfor.get(4).setText("Ngày hết hạn" + table.getModel().getValueAt(row, 4).toString());
//
//                loadCTHD(table.getModel().getValueAt(row, 0).toString());
//            }
//        });
        
        // giao diện table
        Font font_table = new Font("Segoe UI", Font.BOLD, 13);
        table.getTableHeader().setBackground(color1);
        table.getTableHeader().setFont(font_table);
        table.getTableHeader().setForeground(this.colorBackground);
        table.getTableHeader().setOpaque(false); 
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(this.color1));
        
        // căn giữa các chữ trong ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        table.setFocusable(false);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(color3);
        table.setRowHeight(30);
        table.setBorder(BorderFactory.createLineBorder(this.color1));
        return pn_table;
    }
    
    public void loadBH() {
        if (baoHanhBUS.getBhList() == null) {
            baoHanhBUS.list();
        }
        ArrayList<BaoHanhDTO> bhList = baoHanhBUS.getBhList();
        model.setRowCount(0);
        reloadBH(bhList);
    }
    
    public void reloadBH(ArrayList<BaoHanhDTO> bhList) {
        model.setRowCount(0);
        for (BaoHanhDTO bh : bhList) {
            model.addRow(new Object[]{
                 bh.getIdBaoHanh() ,bh.getIdKhachHang(), bh.getTenSanPham(), bh.getSerial(), bh.getNgayBaoHanh(), bh.getNgayTraMay()
            });
        }
    }
    
    public void loadPBH() {
        if (phieuBaoHanhBUS.getBhList() == null) {
            phieuBaoHanhBUS.list();
        }
        ArrayList<PhieuBaoHanhDTO> bhList = phieuBaoHanhBUS.getBhList();
        model.setRowCount(0);
        reloadPBH(bhList);
    }
    
    
    
    public void taoBaoHanh() throws IOException {
        if (this.arrTfInfor.get(0).getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bảo hành trống!");
            return;
        }
        String id_bh = this.arrTfInfor.get(0).getText();
        String id_kh = this.arrTfInfor.get(1).getText();
        String ten_sp = this.arrTfInfor.get(2).getText();
        String serial = this.arrTfInfor.get(3).getText();
        LocalDate ngay_baohanh = LocalDate.now();
        LocalDate ngay_tramay = LocalDate.now().plusDays(7);
//        int tt = Integer.parseInt(this.lbTongTien.getText());

        BaoHanhDTO bh = new BaoHanhDTO(id_bh, id_kh, ten_sp, serial, ngay_baohanh, ngay_tramay);
        baoHanhBUS.addBH(bh);
        
        if (baoHanhBUS.getBhList() == null) {
             baoHanhBUS.list();
        }
        
//        tong=hd.getTongTien();
//        writepdf();
//        cleanPage();
    }
    
//    public void cleanPage() {
////        this.arrTfInfor.get(0).setText(baoHanhBUS.createNewId());
////        this.arrTfInfor.get(1).setText("");
////        this.loadBH();
//        reloadBH();
//    }
    
    
//    
    
    public void reloadPBH(ArrayList<PhieuBaoHanhDTO> bhList) {
        model.setRowCount(0);
        for (PhieuBaoHanhDTO bh : bhList) {
            model.addRow(new Object[]{
                 bh.getSerial() ,bh.getIdHoaDon(), bh.getIdKhachHang(), bh.getTenSanPham(), bh.getNgayMua(), bh.getNgayHetHan()
            });
        }
    } 
}
