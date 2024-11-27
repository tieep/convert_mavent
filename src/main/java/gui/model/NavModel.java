package gui.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NavModel extends JPanel implements MouseListener {
    private int width, height;
    private JLabel icon, label;
    private Color colorNormal, colorHover;
    private boolean isActive;
    private String navName, navIcon, navIconHover;
    
    public NavModel(String n, int w, int h, String icon, String icon_hover, Color normal, Color hover) {
        this.navName = n;
        this.width = w;
        this.height = h;
        this.navIcon = icon;
        this.navIconHover = icon_hover;
        this.colorNormal = normal;
        this.colorHover = hover;
        this.init();
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }
    
    public void init() {
        this.addMouseListener(this);
        Font font = new Font("Segoe UI",Font.BOLD,13);
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setLayout(null);
        this.setBackground(this.colorNormal);
        
        // khoi tao icon va label
        this.icon = new JLabel("", JLabel.CENTER);
        this.label = new JLabel(this.navName);
        
        // thiet ke label icon
        IconModel icon_model = new IconModel(20, 20, this.navIcon);
        this.icon.setBounds(0, 0, 50, 50);
        this.icon.setIcon(icon_model.createIcon());
        
        // thiet ke label
        this.label.setBounds(60, 0, 150, 50);
        this.label.setFont(font);
        this.label.setForeground(Color.decode("#FFFFFF"));
        this.label.setBackground(this.colorNormal);
        
        this.add(icon);
        this.add(label);
    }
    
    public void actived() {
        this.isActive = true;
        this.setBackground(Color.white);
        this.label.setForeground(colorNormal);
        IconModel i = new IconModel(20, 20, this.navIconHover);
        this.icon.setIcon(i.createIcon());
    }
    
    public void noActived() {
        this.isActive = false;
        this.setBackground(this.colorNormal);
        this.label.setForeground(Color.white);
        IconModel i = new IconModel(20, 20, this.navIcon);
        this.icon.setIcon(i.createIcon());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!this.isActive) {
            this.setBackground(this.colorHover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!this.isActive) {
            this.setBackground(this.colorNormal);
            IconModel i = new IconModel(20, 20, this.navIcon);
            this.icon.setIcon(i.createIcon());
        }
    }
}
