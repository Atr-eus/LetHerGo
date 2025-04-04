import javax.swing.*;

public class DashBoard {
    public DashBoard(String username) {
        JFrame frame = new JFrame();
        frame.setTitle("DashBoard - Let Her Go");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JLabel welcome_label = new JLabel("Welcome to your very own dashboard, " + username + "!", SwingConstants.CENTER);
        frame.add(welcome_label);
        frame.setVisible(true);
    }
}