package gui;

import bus.KhachHangBUS;
import dto.KhachHangDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ChonKhachHangGUI extends JDialog implements ActionListener {
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    
    private String idKhach = "";
    private JButton btnChon, btnQuayVe, btnThemKhach;
    private TableRowSorter<TableModel> rowSorter;
    private JTable tableKH;
    private DefaultTableModel modelKH;
    private KhachHangBUS khachHangBUS = new KhachHangBUS();

    
    public ChonKhachHangGUI() {
        this.setModal(true);
        this.init();
    }
    
    public void init() {
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());     
        
        // tim kiem
        JPanel pn_tim_kiem = new JPanel(new FlowLayout(1, 10, 10));
        pn_tim_kiem.setPreferredSize(new Dimension(700, 50));
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
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 2).toUpperCase().equals("KH")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else if (txt.trim().substring(0, 1).equals("0")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ txt +".*", 2));
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
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 2).toUpperCase().equals("KH")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + txt, 0));                    
                } 
                else if (txt.trim().substring(0, 1).equals("0")) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ txt +".*", 2));
                }
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^"+ txt +".*", 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
        
        Font font_filter = new Font("Segoe UI", Font.BOLD, 13);
        
        lb_tim_kiem.setFont(font_filter);
        tf_tim_kiem.setFont(font_filter);
        
        lb_tim_kiem.setForeground(color1);
        tf_tim_kiem.setForeground(color1);  
 
        pn_tim_kiem.add(lb_tim_kiem);
        pn_tim_kiem.add(tf_tim_kiem);
        
        
        // btn them khach
        this.btnThemKhach = new JButton("Thêm khách hàng");
        this.btnThemKhach.setPreferredSize(new Dimension(150, 30));
        this.btnThemKhach.setForeground(this.colorBackground);
        this.btnThemKhach.setBackground(color2);
        this.btnThemKhach.addActionListener(this);
        
        pn_tim_kiem.add(this.btnThemKhach);
        
        // table
        JPanel pn_table = new JPanel(new FlowLayout(1, 25, 10));
        String[] col = {
            "Mã khách hàng", "Tên khách hàng", "SĐT"
        };
        modelKH = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableKH = new JTable();
        rowSorter = new TableRowSorter<TableModel>(modelKH);
        tableKH.setModel(modelKH);
        tableKH.setRowSorter(rowSorter);
        JScrollPane scroll = new JScrollPane(tableKH);
        scroll.setPreferredSize(new Dimension(550, 350));
        
        tableKH.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableKH.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableKH.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        loadKH();
        
        pn_table.add(pn_tim_kiem);
        pn_table.add(scroll);
        
        Font font_table = new Font("Segoe UI", Font.BOLD, 13);
        tableKH.getTableHeader().setBackground(color1);
        tableKH.getTableHeader().setFont(font_table);
        tableKH.getTableHeader().setForeground(this.colorBackground);
        tableKH.getTableHeader().setOpaque(false); 
        tableKH.getTableHeader().setBorder(BorderFactory.createLineBorder(this.color1));
        
        // căn giữa các chữ trong ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < col.length; i++) {
            tableKH.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        tableKH.setFocusable(false);
        tableKH.setShowVerticalLines(false);
        tableKH.setIntercellSpacing(new Dimension(0, 0));
        tableKH.setFillsViewportHeight(true);
        tableKH.setSelectionBackground(color3);
        tableKH.setRowHeight(30);
        tableKH.setBorder(BorderFactory.createLineBorder(this.color1));
        
        JPanel pn_btn = new JPanel(new FlowLayout(1));
        pn_btn.setPreferredSize(new Dimension(700, 50));
        
        btnChon = new JButton("Chọn");
        btnQuayVe = new JButton("Quay về");
        
        btnChon.setPreferredSize(new Dimension(150, 30));
        btnQuayVe.setPreferredSize(new Dimension(150, 30));
        btnChon.setBackground(color2);
        btnQuayVe.setBackground(color2);
        btnChon.setForeground(this.colorBackground);
        btnQuayVe.setForeground(this.colorBackground);
        
        btnChon.addActionListener(this);
        btnQuayVe.addActionListener(this);
        
        pn_btn.add(btnChon);
        pn_btn.add(btnQuayVe);
                
        this.add(pn_tim_kiem, BorderLayout.NORTH);
        this.add(pn_table, BorderLayout.CENTER);
        this.add(pn_btn, BorderLayout.SOUTH);
        
        this.setVisible(true);
    }
    
    public void loadKH() {
        if (khachHangBUS.getKhList() == null) {
            khachHangBUS.list();
        }
        ArrayList<KhachHangDTO> khList = khachHangBUS.getKhList();
        modelKH.setRowCount(0);
        reloadKH(khList);
    }
    
    public void reloadKH(ArrayList<KhachHangDTO> khList) {
        modelKH.setRowCount(0);
        for (KhachHangDTO kh : khList) {
            if (kh.isEnable()) {
                modelKH.addRow(new Object[]{
                    kh.getIdKhachHang(), kh.getTenKhachHang(), kh.getSdt()
                });
            }
        }
    }
    
    public String getIdKhach() {
        return idKhach;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnChon)) {
            int row = tableKH.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng!"); 
            }
            idKhach = tableKH.getModel().getValueAt(row, 0).toString();
            dispose();
        }
        else if (e.getSource().equals(this.btnThemKhach)) {
            new ThemKhachHangGUI();
            khachHangBUS.list();
            reloadKH(khachHangBUS.getKhList());
        }
        else if (e.getSource().equals(this.btnQuayVe)) {
            dispose();
        }
    }
}
