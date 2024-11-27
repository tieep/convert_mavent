package gui;

import bus.CTQuyenChucNangBUS;
import dto.UserDTO;
import gui.model.IconModel;
import gui.model.NavModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainGUI extends JFrame implements MouseListener {
    private int width = 1200, height = 700;
    private Color colorBackground = Color.decode("#FFFFFF");
    private Color color1 = Color.decode("#006270");
    private Color color2 = Color.decode("#009394");
    private Color color3 = Color.decode("#00E0C7");
    
    private int widthCenter = 1000, heightCenter = 670;
    private JPanel header, nav, main;
    private ArrayList<String> navName = new ArrayList<>();
    private ArrayList<NavModel> navModel = new ArrayList<>(); 
    private Map<String, Boolean> mapCN = new HashMap<>();
    private CTQuyenChucNangBUS qcnBUS = new CTQuyenChucNangBUS();
    private UserDTO user = new UserDTO();
    
    public MainGUI() {
        this.init();
    }
    
    public MainGUI(UserDTO user) {
        this.user = user;
        this.init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        
        this.header = createHeader();
        this.nav = createNav();
        this.main = new ThongKeGUI(this.width, this.height);
        
        this.setLayout(new BorderLayout());
        this.add(this.header, BorderLayout.NORTH);
        this.add(this.nav, BorderLayout.WEST);
        this.add(this.main, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    // TAO HEADER   
    public JPanel createHeader() {
        JPanel pn_header = new JPanel();
        pn_header.setLayout(new BorderLayout());
        pn_header.setPreferredSize(new Dimension(this.width, 30));
        pn_header.setBackground(Color.decode("#006270"));

        JLabel lb_exit = new JLabel("", JLabel.CENTER);
        lb_exit.setOpaque(true);
        lb_exit.setBackground(Color.decode("#006270"));
        lb_exit.setPreferredSize(new Dimension(30, 30));
        
        IconModel icon_exit = new IconModel(10, 10, "close-white.png");
        lb_exit.setIcon(icon_exit.createIcon());
        
        lb_exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lb_exit.setBackground(Color.decode("#009394"));            
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lb_exit.setBackground(Color.decode("#006270"));
            }
        
        });
        
        JPanel pn_hello = new JPanel(new FlowLayout(0, 10, 7));
        pn_hello.setBackground(color1);
        pn_hello.setPreferredSize(new Dimension(200, 30));
        
        JLabel lb_hello = new JLabel("Hi " + this.user.getTenUser(), JLabel.CENTER);
        lb_hello.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lb_hello.setForeground(this.colorBackground);
        
        pn_hello.add(lb_hello);
        
        pn_header.add(lb_exit, BorderLayout.EAST);
        pn_header.add(pn_hello, BorderLayout.WEST);
        
        return pn_header;
    } 
    
    // TAO THANH MENU NAVIGATION
    public JPanel createNav() {
        JPanel pn_result = new JPanel(new BorderLayout());
        JPanel pn_nav = new JPanel();
        pn_nav.setLayout(new FlowLayout(1, 0, 0));
        pn_nav.setPreferredSize(new Dimension(200, this.height - 30));
        pn_nav.setBackground(Color.decode("#006270"));
        
        // add avatar
        JLabel lb_ava = new JLabel();
        lb_ava.setPreferredSize(new Dimension(200, 100));
        lb_ava.setOpaque(true);
        lb_ava.setBackground(Color.decode("#009394"));
        IconModel icon = new IconModel(200, 100, "logo.jpg");
        lb_ava.setIcon(icon.createIcon());
        lb_ava.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        lb_ava.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.removeAll();
                main.add(new ThongKeGUI(widthCenter, heightCenter));
                main.repaint();
                main.validate();
            }
        });
        
        
        pn_nav.add(lb_ava);
        
        if (qcnBUS.getCtList() == null) {
            qcnBUS.list();
        }
        ArrayList<String> arr_cn = qcnBUS.listId(this.user.getQuyen());
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 9; j++) {
                mapCN.put(i+""+j, false);
            }
        }
        for (String cn : arr_cn) {
            mapCN.put(cn, true);
        }
        // add navigation menu
        for (String cn : arr_cn) {
            if (cn.charAt(0) == '0') {
                switch (cn.charAt(1)) {
                    case '0':
                        this.navName.add("Nhân viên:employee-white.png:employee-1.png");
                        break;
                    case '1':
                        this.navName.add("Khách hàng:people-white.png:people-1.png");
                        break;
                    case '2':
                        this.navName.add("Nhà cung cấp:delivery-white.png:delivery-1.png");
                        break;
                    case '3':
                        this.navName.add("Sản phẩm:laptop-white.png:laptop-1.png");
                        break;
                    case '4':
                        this.navName.add("Quyền:quyen-white.png:quyen-1.png");
                        break;
                    case '5':
                        this.navName.add("Bán hàng:cart-white.png:cart-1.png");
                        break;
                    case '6':
                        this.navName.add("Nhập hàng:cart-white.png:cart-1.png");
                        break;
                    case '7':
                        this.navName.add("Hóa đơn:task-white.png:task-1.png");
                        break;
                    case '8':
                        this.navName.add("Phiếu nhập:task-white.png:task-1.png");   
                        break;
                    case '9':
                        this.navName.add("Bảo hành:shield-white.png:shield-1.png");   
                        break;
                }
            }
        }
        
        
        // tao nav item menu
        for (int i = 0; i < navName.size(); i++) {
            String[] parts = navName.get(i).split(":");
            navModel.add(new NavModel(parts[0], 200, 50, parts[1], parts[2], this.color1, this.color2));
            navModel.get(i).addMouseListener(this);
        }
        
        for (NavModel obj : navModel) {
            pn_nav.add(obj);
        }
        
        JPanel pn_logout = new JPanel(new FlowLayout(1, 5, 10));
        pn_logout.setPreferredSize(new Dimension(200, 70));
        pn_logout.setBackground(this.color2);
        JLabel lb_icon_logout = new JLabel("", JLabel.CENTER);
        JLabel lb_logout = new JLabel("Đăng xuất", JLabel.CENTER);
        
        lb_icon_logout.setPreferredSize(new Dimension(30, 30));
        lb_logout.setPreferredSize(new Dimension(100, 50));
        
        IconModel icon_logout = new IconModel(20, 20, "logout-white.png");
        lb_icon_logout.setIcon(icon_logout.createIcon());
        
        lb_logout.setFont(new Font("Segoe UI",Font.BOLD,13));
        lb_logout.setForeground(this.colorBackground);
        
        pn_logout.add(lb_icon_logout);
        pn_logout.add(lb_logout);
        
        pn_logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginGUI();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                pn_logout.setBackground(color3);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                pn_logout.setBackground(color2);
            }
        });
        
        pn_result.add(pn_nav, BorderLayout.CENTER);
        pn_result.add(pn_logout, BorderLayout.SOUTH);
        
        return pn_result;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < navModel.size(); i++) {
            NavModel item = navModel.get(i);
            if (e.getSource() == item) {
                item.actived();
                changeMain(item);
            }
            else {
                item.noActived();
            }    
        }
    }
    
    public void changeMain(NavModel nav) {
        switch (nav.getNavName()) {
            case "Nhân viên": // nhan vien
                main.removeAll();
                main.add(new NhanVienGUI(widthCenter, heightCenter, mapCN.get("10"), mapCN.get("20"), mapCN.get("30")));
                main.repaint();
                main.validate();
                break;
            case "Khách hàng": // khach hang
                main.removeAll();
                main.add(new KhachHangGUI(widthCenter, heightCenter, mapCN.get("11"), mapCN.get("21"), mapCN.get("31")));
                main.repaint();
                main.validate();
                break;
            case "Nhà cung cấp": // nha cung cap
                main.removeAll();
                main.add(new NhaCungCapGUI(widthCenter, heightCenter, mapCN.get("12"), mapCN.get("22"), mapCN.get("32")));
                main.repaint();
                main.validate();
                break;
            case "Sản phẩm": // san pham 
                main.removeAll();
                main.add(new SanPhamGUI(widthCenter, heightCenter, mapCN.get("13"), mapCN.get("23"), mapCN.get("33")));
                main.repaint();
                main.validate();
                break;
            case "Quyền": // quyen
                main.removeAll();
                main.add(new QuyenGUI(widthCenter, heightCenter, mapCN.get("14"), mapCN.get("24"), mapCN.get("34")));
                main.repaint();
                main.validate();
                break;
            case "Bán hàng": // ban hang
                main.removeAll();
                main.add(new BanHangGUI(widthCenter, heightCenter, user, mapCN.get("15"), mapCN.get("25"), mapCN.get("35")));
                main.repaint();
                main.validate();
                break;
            case "Nhập hàng": // nhap hang
                main.removeAll();
                main.add(new NhapHangGUI(widthCenter, heightCenter, user, mapCN.get("16"), mapCN.get("26"), mapCN.get("36")));
                main.repaint();
                main.validate();
                break;
            case "Hóa đơn": // hoa don
                main.removeAll();
                main.add(new HoaDonGUI(widthCenter, heightCenter));
                main.repaint();
                main.validate();
                break;
            case "Phiếu nhập": // phieu nhap
                main.removeAll();
                main.add(new PhieuNhapGUI(widthCenter, heightCenter));
                main.repaint();
                main.validate();
                break;
            case "Bảo hành": // baohanh 
                main.removeAll();
                main.add(new BaoHanhGUI(widthCenter, heightCenter));
                main.repaint();
                main.validate();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
