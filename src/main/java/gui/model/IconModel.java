package gui.model;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class IconModel {
    private int width, height;
    private String srcIcon;
    
    public IconModel(int w, int h, String s) {
        this.width = w;
        this.height = h;
        this.srcIcon = s;
    }
    
    public ImageIcon createIcon() {
        URL imgUrl = getClass().getResource("/img/" + this.srcIcon);
        
        if (imgUrl == null) {
            System.err.println("Không tìm thấy hình ảnh: /img/" + this.srcIcon);
            return new ImageIcon(getClass().getResource("/img/NhanVien/image.png")); // Đường dẫn đến hình ảnh mặc định
        }
    
        ImageIcon icon = new ImageIcon(imgUrl);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        
        return icon;
    }
    
}
