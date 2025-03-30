import javax.swing.*;
import java.awt.*;

public class DashBoard {
    JFrame frame = new JFrame("DashBoard");
    JLabel welcome = new JLabel();

    DashBoard(String username) {
        welcome.setBounds(10, 10, 300, 50);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        welcome.setText("Welcome, " + username + "!");

        frame.add(welcome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
