package gui;

import bus.CTPhieuNhapBUS;
import bus.PhieuNhapBUS;
import bus.SanPhamBUS;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import dto.CTPhieuNhapDTO;
import dto.PhieuNhapDTO;
import dto.SanPhamDTO;
import dto.UserDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class NhapHangGUI extends JPanel implements ActionListener {
    private int width, height;
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private CTPhieuNhapBUS ctPhieuNhapBUS = new CTPhieuNhapBUS();

    private JPanel pnInfor, pnFilter, pnTable;
//    private JSlider slider;
    private ArrayList<JPanel> arrPnInfor;
    private ArrayList<JLabel> arrLbInfor;
    private ArrayList<JTextField> arrTfInfor;
    private JButton btnTaoPhieuNhap, btnXoaSanPham, btnThemSanPham, btnChonNhaCungCap;
    private JLabel lbTongTien;
    private ArrayList<CTPhieuNhapDTO> arrCTPN = new ArrayList<>();
    
    private JTextField tfSoLuong;
    private JTable table, tableCT;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel model, modelCT;
    
    private UserDTO user = new UserDTO();
    private boolean quyenThem, quyenSua, quyenXoa;
    
    public NhapHangGUI(int width, int height, UserDTO user, boolean quyenThem, boolean quyenSua, boolean quyenXoa) {
        this.width = width;
        this.height = height;
        this.user = user;
        this.quyenThem = quyenThem;
        this.quyenSua = quyenSua;
        this.quyenXoa = quyenXoa;
        this.init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        
        this.pnInfor = this.createPnInfor();
        this.pnFilter = this.createPnFilter();
        this.pnTable = this.createPnTable();
        
        this.setLayout(new BorderLayout());
        this.add(this.pnInfor, BorderLayout.NORTH);
        this.add(this.pnFilter, BorderLayout.CENTER);
        this.add(this.pnTable, BorderLayout.SOUTH);
    }
    
    public JPanel createPnInfor() {
        JPanel result = new JPanel(new FlowLayout(1, 0, 10));
        result.setPreferredSize(new Dimension(this.width, 300));
        
        // phần thông tin phiếu nhập
        JPanel pn_infor = new JPanel(new FlowLayout(1, 5, 15));
        pn_infor.setPreferredSize(new Dimension(250, 250));
        pn_infor.setBorder(BorderFactory.createLineBorder(color1, 2));
        
        loadPN();
        
        String[] thuoc_tinh = {"Mã phiếu nhập", "Nhà cung cấp", "Mã nhân viên", "Ngày"};
        int len = thuoc_tinh.length;
        this.arrPnInfor = new ArrayList<>();
        this.arrLbInfor = new ArrayList<>();
        this.arrTfInfor = new ArrayList<>();
        
        Dimension d_pn = new Dimension(240, 30);
        Dimension d_lb = new Dimension(100, 30);
        Dimension d_tf = new Dimension(130, 30);
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

        this.arrTfInfor.get(0).setText(phieuNhapBUS.createNewId());
        this.arrTfInfor.get(2).setText(this.user.getIdUser());
        this.arrTfInfor.get(3).setText(LocalDate.now()+"");
        
        this.arrTfInfor.get(1).setPreferredSize(new Dimension(100, 25));
        this.btnChonNhaCungCap = new JButton("...");
        this.btnChonNhaCungCap.setPreferredSize(new Dimension(25, 25));
        this.btnChonNhaCungCap.setBackground(color3);
        this.btnChonNhaCungCap.setFont(font_infor);
        this.btnChonNhaCungCap.setForeground(color1);
        this.btnChonNhaCungCap.addActionListener(this);
        this.arrPnInfor.get(1).add(this.btnChonNhaCungCap);
        

        // phần bảng thông tin chi tiết hóa đơn
        JPanel pn_table = new JPanel(new FlowLayout(1));
        
        String[] col = {
            "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"
        };
        modelCT = new DefaultTableModel(col, 0);
        tableCT = new JTable();
        tableCT.setModel(modelCT);
        tableCT.setRowSorter(rowSorter);
        JScrollPane scroll = new JScrollPane(tableCT);
        scroll.setPreferredSize(new Dimension(500, 250));
        
        tableCT.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableCT.getColumnModel().getColumn(1).setPreferredWidth(70);
        tableCT.getColumnModel().getColumn(2).setPreferredWidth(10);
        tableCT.getColumnModel().getColumn(3).setPreferredWidth(40);
        
        pn_table.add(scroll);
        
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
        JPanel pn_btn = new JPanel(new FlowLayout(1, 5, 5));
        pn_btn.setPreferredSize(new Dimension(150, 250));
        
        this.btnXoaSanPham = new JButton("Xóa sản phẩm");
        this.btnTaoPhieuNhap = new JButton("Tạo phiếu nhập");
        this.btnXoaSanPham.setPreferredSize(new Dimension(140, 30));
        this.btnTaoPhieuNhap.setPreferredSize(new Dimension(140, 30));
        this.btnXoaSanPham.setBackground(this.color2);
        this.btnTaoPhieuNhap.setBackground(this.color2);
        this.btnXoaSanPham.setForeground(this.colorBackground);
        this.btnTaoPhieuNhap.setForeground(this.colorBackground);
        Font font_btn = new Font("Segoe UI", Font.BOLD, 13);
        this.btnXoaSanPham.setFont(font_btn);
        this.btnTaoPhieuNhap.setFont(font_btn);
        
        this.btnXoaSanPham.addActionListener(this);
        this.btnTaoPhieuNhap.addActionListener(this);
        
        this.btnTaoPhieuNhap.setVisible(quyenThem);
        
        JPanel pn_tong_tien = new JPanel(new FlowLayout(1, 5, 10));
        pn_tong_tien.setPreferredSize(new Dimension(150, 170));
        pn_tong_tien.setBorder(BorderFactory.createLineBorder(this.color1, 2));
        JLabel lb_tong_tien = new JLabel("Tổng tiền", JLabel.CENTER);
        lb_tong_tien.setPreferredSize(new Dimension(150, 30));
        
        Font font_tong_tien_1 = new Font("Segoe UI", Font.BOLD, 17);
        lb_tong_tien.setFont(font_tong_tien_1);
        lb_tong_tien.setForeground(this.color1);
        
        this.lbTongTien = new JLabel("0");
        this.lbTongTien.setForeground(this.color1);
        
        pn_tong_tien.add(lb_tong_tien);
        pn_tong_tien.add(lbTongTien);

        pn_btn.add(this.btnXoaSanPham);
        pn_btn.add(this.btnTaoPhieuNhap);
        pn_btn.add(pn_tong_tien);
        
        result.add(pn_infor);
        result.add(pn_table);
        result.add(pn_btn);
        
        return result;
    }
    
    public JPanel createPnFilter() {
        JPanel pn_filter = new JPanel(new FlowLayout(1, 20, 5));
        
        // Thanh tìm kiếm theo tên hoặc id sản phẩm
        JLabel lb_tim_kiem = new JLabel("Tìm kiếm", JLabel.CENTER);
        JTextField tf_tim_kiem = new JTextField();
        tf_tim_kiem.setPreferredSize(new Dimension(200, 30));
        tf_tim_kiem.getDocument().addDocumentListener(new DocumentListener() { 
            @Override
            public void insertUpdate(DocumentEvent e) {
                String txt = tf_tim_kiem.getText();
                if (txt.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                }
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 2).toUpperCase().equals("SP")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ txt +".*", 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String txt = tf_tim_kiem.getText();
                if (txt.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                }
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 2).toUpperCase().equals("SP")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ txt +".*", 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
        
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(10, 40));
        
        // phần thêm số lượng sản phẩm vào chi tiết phiếu nhập
        JLabel lb_so_luong = new JLabel("Số lượng", JLabel.CENTER);
        tfSoLuong = new JTextField("1");
        tfSoLuong.setPreferredSize(new Dimension(50, 30));
        
        Font font_filter = new Font("Segoe UI", Font.BOLD, 13);
        
        lb_tim_kiem.setFont(font_filter);
        tf_tim_kiem.setFont(font_filter);
        lb_so_luong.setFont(font_filter);
        tfSoLuong.setFont(font_filter);
        
        lb_tim_kiem.setForeground(color1);
        tf_tim_kiem.setForeground(color1);  
        lb_so_luong.setForeground(color1);
        tfSoLuong.setForeground(color1);
                
        this.btnThemSanPham = new JButton("Thêm sản phẩm");
        Font font_btn = new Font("Segoe UI", Font.BOLD, 13);
        this.btnThemSanPham.setPreferredSize(new Dimension(170, 30));
        this.btnThemSanPham.setFont(font_btn);
        this.btnThemSanPham.setBackground(this.color2);
        this.btnThemSanPham.setForeground(this.colorBackground);

        this.btnThemSanPham.addActionListener(this);
        
        pn_filter.add(lb_tim_kiem);
        pn_filter.add(tf_tim_kiem);
        pn_filter.add(sep);
        pn_filter.add(lb_so_luong);
        pn_filter.add(tfSoLuong);
        pn_filter.add(this.btnThemSanPham);
        
        return pn_filter;
    }
    
    public JPanel createPnTable() {
        JPanel pn_table = new JPanel(new FlowLayout(1, 0, 0));
        pn_table.setPreferredSize(new Dimension(this.width, 320));
        
        String[] col = {
            "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá nhập", "Hãng"
        };
        this.model = new DefaultTableModel(col, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        this.table.setModel(model);
        this.table.setRowSorter(rowSorter);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(900, 300));
        
        
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        
        this.loadSP();
        
        pn_table.add(scroll);
        
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
    
    public void loadPN() {
        if (phieuNhapBUS.getPnList() == null) {
            phieuNhapBUS.list();
        }
    }
    
    public void loadSP() {
        if (sanPhamBUS.getSpList() == null) {
            sanPhamBUS.list();
        }
        ArrayList<SanPhamDTO> spList = sanPhamBUS.getSpList();
        model.setRowCount(0);
        reloadSP(spList);
    }
    
    public void reloadSP(ArrayList<SanPhamDTO> spList) {
        model.setRowCount(0);
        for (SanPhamDTO sp : spList) {
            if (sp.isEnable()) {
                model.addRow(new Object[]{
                    sp.getIdSanPham(), sp.getTenSanPham(), sp.getSoLuong(), sp.getGiaNhap(), sp.getHang()
                });
            }
        }
    }
    
    public void reloadCTPN() {
        modelCT.setRowCount(0);
        for (CTPhieuNhapDTO ctpn : arrCTPN) {
            modelCT.addRow(new Object[]{
                ctpn.getIdSanPham(), ctpn.getTenSanPham(), ctpn.getSoLuong(), ctpn.getDonGia()
            });
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnThemSanPham)) {
            themSanPham();
        }
        else if (e.getSource().equals(this.btnXoaSanPham)) {
            xoaSanPham();
        }
        else if (e.getSource().equals(this.btnChonNhaCungCap)) {
            ChonNhaCungCapGUI result = new ChonNhaCungCapGUI();
            arrTfInfor.get(1).setText(result.getIdNCC());
        }
        else if (e.getSource().equals(this.btnTaoPhieuNhap)) {
            if (arrTfInfor.get(1).getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà cung cấp!");
                return;
            }
            int confirmed = JOptionPane.showConfirmDialog(null, "Xác nhận tạo phiếu nhập", "", JOptionPane.YES_NO_OPTION);
            if (confirmed == 0) {
                try {
                    taoPhieuNhap();
                } catch (IOException ex) {
                    Logger.getLogger(NhapHangGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void themSanPham() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần thêm!");
        } 
        else {
            int sl_them = 0;
            try {
                sl_them = Integer.parseInt(this.tfSoLuong.getText()); 
            } catch(NumberFormatException E) {
                JOptionPane.showMessageDialog(null, "Số lượng nhập không hợp lệ!");
                return;
            }
            
            if (sl_them <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng nhập không hợp lệ!");
                return;
            }
            
            String id_pn = this.arrTfInfor.get(0).getText();
            String id_sp = table.getModel().getValueAt(row, 0).toString();
            String ten_sp = table.getModel().getValueAt(row, 1).toString();
            int so_luong = Integer.parseInt(table.getModel().getValueAt(row, 2).toString());
            String hang = table.getModel().getValueAt(row, 4).toString();

            // kiểm tra sản phẩm có giá nhập chưa
            int gia_nhap = Integer.parseInt(table.getModel().getValueAt(row, 3).toString());
            int gia_ban;
            if (gia_nhap == 0) {
                TaoGiaNhapGUI result = new TaoGiaNhapGUI();
                gia_nhap = result.getGiaNhap();
                gia_ban = result.getGiaBan();
                if (gia_nhap == 0) return;
                else {
                    SanPhamDTO sp = new SanPhamDTO(id_sp, ten_sp, so_luong, gia_nhap, gia_ban, hang, id_sp+".png", true);
                    sanPhamBUS.updateSanPham(sp);
                    loadSP();
                }
            }
            
            // kiểm tra sản phẩm có trong giỏ chưa
            boolean sp_moi = true;
            for (CTPhieuNhapDTO ctpn : arrCTPN) {
                if (ctpn.getIdSanPham().equals(id_sp)) {
                    int sl_gio_hang = ctpn.getSoLuong();
                    ctpn.setSoLuong(sl_gio_hang + sl_them);
                    sp_moi = false;
                    break;
                }
            }

            if (sp_moi) {
//                int gia = Integer.parseInt(table.getModel().getValueAt(row, 3).toString());
                arrCTPN.add(new CTPhieuNhapDTO(id_pn, id_sp, ten_sp, sl_them, gia_nhap));
            }
            reloadCTPN();
            this.lbTongTien.setText(String.valueOf(tinhTongTien()));
        }
    }
    
    public void xoaSanPham() {
        int row = tableCT.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần xóa!");
        } 
        else {
            arrCTPN.remove(row);
            modelCT.removeRow(row);
            this.lbTongTien.setText(String.valueOf(tinhTongTien()));
        }
    }
    
    public int tinhTongTien() {
        int sum = 0;
        for (CTPhieuNhapDTO ctpn : arrCTPN) {
            sum += ctpn.getDonGia() * ctpn.getSoLuong();
        }
        return sum;
    }
    int tong;
    public void taoPhieuNhap() throws IOException {
        if (this.arrCTPN.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hóa đơn trống!");
            return;
        }
        String id_hd = this.arrTfInfor.get(0).getText();
        String id_ncc = this.arrTfInfor.get(1).getText();
        String id_nv = this.arrTfInfor.get(2).getText();
        LocalDate ngay = LocalDate.now();
        int tt = Integer.parseInt(this.lbTongTien.getText());

        PhieuNhapDTO pn = new PhieuNhapDTO(id_hd, id_ncc, id_nv, ngay, tt);
        phieuNhapBUS.addPhieuNhap(pn);

        for (CTPhieuNhapDTO ctpn : arrCTPN) {
            ctPhieuNhapBUS.addCTPN(ctpn);
            sanPhamBUS.themSoLuong(ctpn.getIdSanPham(), ctpn.getSoLuong());
        }
        tong=pn.getTongTien();
        writepdf();
        cleanPage();
    }
    
    public void cleanPage() {
        this.arrTfInfor.get(0).setText(phieuNhapBUS.createNewId());
        this.arrTfInfor.get(1).setText("");
        this.arrCTPN.removeAll(arrCTPN);
        this.lbTongTien.setText("0");
        this.loadSP();
        this.reloadCTPN();
    }
    
    public void tinhGiaNhap(int lai) {
        for (CTPhieuNhapDTO ctpn : arrCTPN) {
            int tien_goc = sanPhamBUS.getGiaOf(ctpn.getIdSanPham());
            int tien_giam = tien_goc * lai / 100;
            int tien_nhap = tien_goc - tien_giam;
            ctpn.setDonGia(tien_nhap);
        }
    }
    
     public void writepdf() throws IOException {
        String id_hd = this.arrTfInfor.get(0).getText();
        String id_kh = this.arrTfInfor.get(1).getText();
        String id_nv = this.arrTfInfor.get(2).getText();
        LocalDate ngay_mua = LocalDate.now();
             
       Document document = new Document();
        try {
            
            com.itextpdf.text.Font fontData = new com.itextpdf.text.Font(BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font(BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, com.itextpdf.text.Font.NORMAL);
            
            PdfWriter.getInstance(document, new FileOutputStream("src/report/"+id_hd+".pdf"));
            document.open();
            Chunk glue = new Chunk(new VerticalPositionMark());// Khoang trong giua hang
            
            Paragraph para = new Paragraph(new Phrase("THÔNG TIN PHIẾU NHẬP",fontTitle));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach 
            
            
            Paragraph para1 = new Paragraph(new Phrase("Mã phiếu nhập: " + id_hd,fontHeader));
            Paragraph para2 = new Paragraph(new Phrase("Mã nhà cung cấp: " + id_kh,fontHeader));
            Paragraph para3 = new Paragraph(new Phrase("Ngày nhập hàng : " + ngay_mua,fontHeader));
            Paragraph para4 = new Paragraph(new Phrase("Người tạo: " + id_nv,fontHeader));
            para1.setIndentationLeft(40);
            para2.setIndentationLeft(40);
            para3.setIndentationLeft(40);
            para4.setIndentationLeft(40);
            document.add(para1);
            document.add(para2);
            document.add(para3);
            document.add(para4);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach
            
            PdfPTable pdfTable = new PdfPTable(4);
            pdfTable.setWidths(new float[]{15f, 30f, 15f, 15f});
            PdfPCell cell;

            //Set headers cho table chi tiet
            pdfTable.addCell(new PdfPCell(new Phrase("Mã sản phẩm",fontData)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tên sản phẩm",fontData)));
            pdfTable.addCell(new PdfPCell(new Phrase("Số kượng ",fontData)));
            pdfTable.addCell(new PdfPCell(new Phrase("Đơn giá",fontData)));

            for (int i = 0; i < 4; i++) {
                cell = new PdfPCell(new Phrase(""));
                pdfTable.addCell(cell);
            }

            //Truyen thong tin tung chi tiet vao table
            for (CTPhieuNhapDTO ctpn : arrCTPN) {
                pdfTable.addCell(new PdfPCell(new Phrase(ctpn.getIdSanPham(),fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(ctpn.getTenSanPham(),fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong() ))));
                pdfTable.addCell(new PdfPCell(new Phrase(ctpn.getDonGia() +"",fontData)));
            }
            document.add(pdfTable);
            document.add(Chunk.NEWLINE);
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + tong,fontHeader));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        System.out.println(System.getProperty("user.dir"));
    }
}