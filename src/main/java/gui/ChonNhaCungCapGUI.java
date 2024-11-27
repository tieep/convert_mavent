package gui;

import bus.NhaCungCapBUS;
import dto.NhaCungCapDTO;
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

public class ChonNhaCungCapGUI extends JDialog implements ActionListener {
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    
    private String idNCC = "";
    private JButton btnChon, btnQuayVe;
    private TableRowSorter<TableModel> rowSorter;
    private JTable tableNCC;
    private DefaultTableModel modelNCC;
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();

    
    public ChonNhaCungCapGUI() {
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
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 2).toUpperCase().equals("CC")) {
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
                else if (txt.trim().length() >= 2 && txt.trim().substring(0, 2).toUpperCase().equals("CC")) {
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
        
        Font font_filter = new Font("Segoe UI", Font.BOLD, 13);
        
        lb_tim_kiem.setFont(font_filter);
        tf_tim_kiem.setFont(font_filter);
        
        lb_tim_kiem.setForeground(color1);
        tf_tim_kiem.setForeground(color1);  
 
        pn_tim_kiem.add(lb_tim_kiem);
        pn_tim_kiem.add(tf_tim_kiem);
        
        // table
        JPanel pn_table = new JPanel(new FlowLayout(1, 25, 10));
        String[] col = {
            "Mã nhà cung cấp", "Tên nhà cung cấp", "SĐT"
        };
        modelNCC = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableNCC = new JTable();
        rowSorter = new TableRowSorter<TableModel>(modelNCC);
        tableNCC.setModel(modelNCC);
        tableNCC.setRowSorter(rowSorter);
        JScrollPane scroll = new JScrollPane(tableNCC);
        scroll.setPreferredSize(new Dimension(550, 350));
        
        tableNCC.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableNCC.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableNCC.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        loadNCC();
        
        pn_table.add(pn_tim_kiem);
        pn_table.add(scroll);
        
        Font font_table = new Font("Segoe UI", Font.BOLD, 13);
        tableNCC.getTableHeader().setBackground(color1);
        tableNCC.getTableHeader().setFont(font_table);
        tableNCC.getTableHeader().setForeground(this.colorBackground);
        tableNCC.getTableHeader().setOpaque(false); 
        tableNCC.getTableHeader().setBorder(BorderFactory.createLineBorder(this.color1));
        
        // căn giữa các chữ trong ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < col.length; i++) {
            tableNCC.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        tableNCC.setFocusable(false);
        tableNCC.setShowVerticalLines(false);
        tableNCC.setIntercellSpacing(new Dimension(0, 0));
        tableNCC.setFillsViewportHeight(true);
        tableNCC.setSelectionBackground(color3);
        tableNCC.setRowHeight(30);
        tableNCC.setBorder(BorderFactory.createLineBorder(this.color1));
        
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
    
    public void loadNCC() {
        if (nhaCungCapBUS.getNccList() == null) {
            nhaCungCapBUS.list();
        }
        ArrayList<NhaCungCapDTO> nccList = nhaCungCapBUS.getNccList();
        modelNCC.setRowCount(0);
        reloadNCC(nccList);
    }
    
    public void reloadNCC(ArrayList<NhaCungCapDTO> nccList) {
        modelNCC.setRowCount(0);
        for (NhaCungCapDTO ncc : nccList) {
            if (ncc.isEnable()) {
                modelNCC.addRow(new Object[]{
                    ncc.getIdNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSdt()
                });
            }
        }
    }
    
    public String getIdNCC() {
        return idNCC;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnChon)) {
            int row = tableNCC.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng!"); 
            }
            idNCC = tableNCC.getModel().getValueAt(row, 0).toString();
            dispose();
        }
        else if (e.getSource().equals(this.btnQuayVe)) {
            dispose();
        }
    }
}
