package test11;

import gui.LoginGUI;
import gui.MainGUI;
import API.API_Server;
import java.io.IOException;
public class Test {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new API_Server(8080); // API chạy trên cổng 8080
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new LoginGUI();
    }
}
