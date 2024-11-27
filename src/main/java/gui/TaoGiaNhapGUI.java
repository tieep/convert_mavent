package gui;

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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TaoGiaNhapGUI extends JDialog implements ActionListener {
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    
    private JLabel lbGiaNhap, lbLoiNhuan;
    private JTextField tfGiaNhap, tfLoiNhuan; 
    private ArrayList<JPanel> arrPnInfor;
    private ArrayList<JLabel> arrLbInfor;
    private ArrayList<JTextField> arrTfInfor;
    private JButton btnChon, btnQuayVe;
    private JSlider slider;
    private int giaNhap = 0, giaBan = 0;
    
    public TaoGiaNhapGUI() {
        this.setModal(true);
        this.init();
    }
    
    public void init() {
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        
        // price layout
        JPanel pn_center = new JPanel(new FlowLayout(1, 5, 15));
        pn_center.setPreferredSize(new Dimension(400, 200));
//        pn_center.setBorder(BorderFactory.createLineBorder(color1, 2));
        
        String[] thuoc_tinh = {"Giá nhập", "Lợi nhuận"};
        int len = thuoc_tinh.length;
        this.arrPnInfor = new ArrayList<>();
        this.arrLbInfor = new ArrayList<>();
        this.arrTfInfor = new ArrayList<>();
        
        Dimension d_pn = new Dimension(400, 35);
        Dimension d_lb = new Dimension(100, 30);
        Dimension d_tf = new Dimension(150, 30);
        Color color_font = this.color1;
        Font font_infor = new Font("Segoe UI", Font.PLAIN, 13);
        for (int i = 0; i < len; i++) {
            this.arrPnInfor.add(new JPanel(new FlowLayout(1)));
            this.arrPnInfor.get(i).setPreferredSize(d_pn);
            
            this.arrLbInfor.add(new JLabel(thuoc_tinh[i]));
            this.arrLbInfor.get(i).setPreferredSize(d_lb);
            this.arrTfInfor.add(new JTextField());
            this.arrTfInfor.get(i).setPreferredSize(d_tf);

            this.arrLbInfor.get(i).setForeground(color_font);
            this.arrLbInfor.get(i).setFont(font_infor);
            this.arrTfInfor.get(i).setForeground(color_font);
            this.arrTfInfor.get(i).setFont(font_infor);
            
            this.arrPnInfor.get(i).add(this.arrLbInfor.get(i));
            this.arrPnInfor.get(i).add(this.arrTfInfor.get(i));
            pn_center.add(this.arrPnInfor.get(i));
        }
        this.arrTfInfor.get(1).setEditable(false);
        this.arrTfInfor.get(1).setText("0%");
        
        // Thanh chọn lợi nhuận
        JPanel pn_slider = new JPanel(new BorderLayout());
        pn_slider.setPreferredSize(new Dimension(240, 50));
        this.slider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
        this.slider.setPreferredSize(new Dimension(200, 15));
        this.slider.setMajorTickSpacing(20);
        this.slider.setMinorTickSpacing(10);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        this.slider.setLabelTable(slider.createStandardLabels(20));
        this.slider.setFont(font_infor);
        this.slider.setForeground(color_font);
        
        this.slider.addChangeListener(new ChangeListener() { 
            @Override
            public void stateChanged(ChangeEvent e) {
                arrTfInfor.get(1).setText(slider.getValue() + "%");
                
            }        
        });
        
        pn_slider.add(slider, BorderLayout.CENTER);
        pn_center.add(pn_slider);

        // button layout
        JPanel pn_btn = new JPanel(new FlowLayout(1));
        pn_btn.setPreferredSize(new Dimension(400, 50));
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
                
        this.add(pn_center, BorderLayout.CENTER);
        this.add(pn_btn, BorderLayout.SOUTH);
        
        this.setVisible(true);
    }

    public int getGiaNhap() {
        return giaNhap;
    }
    
    public int getGiaBan() {
        return giaBan;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnChon)) {
            try {
                this.giaNhap = Integer.parseInt(this.arrTfInfor.get(0).getText()); 
            } catch(NumberFormatException E) {
                JOptionPane.showMessageDialog(null, "Giá nhập không hợp lệ!");
                return;
            }
            
            if (this.giaNhap < 100) {
                JOptionPane.showMessageDialog(null, "Giá nhập tối thiểu là 100");
                this.giaNhap = 0;
                return;
            }
            
            if (this.slider.getValue() == 0) {
                JOptionPane.showMessageDialog(null, "Lợi nhuận tối thiểu là 1%");
                this.giaNhap = 0;
                return;
            }
            
            this.giaBan = this.giaNhap + (this.giaNhap * this.slider.getValue()/100);
            int confirmed = JOptionPane.showConfirmDialog(null, "Sản phẩm sẽ có giá bán " + this.giaBan, "", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                dispose();
            }
            else if (confirmed == JOptionPane.NO_OPTION) {
                this.giaNhap = 0;
                dispose();
            }
        }
        else if (e.getSource().equals(this.btnQuayVe)) {
            dispose();
        }
    }    
}
