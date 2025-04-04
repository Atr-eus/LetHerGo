import javax.swing.*;
import java.awt.*;

public class Login {
    private final JFrame frame;
    private final JTextField username_field;
    private final JPasswordField passwd_field;
    private final AccountManager account_manager;

    public Login() {
        account_manager = new AccountManager();

        frame = new JFrame();
        frame.setTitle("Login/Register - Let Her GO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 1));

        username_field = new JTextField();
        passwd_field = new JPasswordField();
        JButton login_btn = new JButton("Login");
        JButton register_btn = new JButton("Register");

        login_btn.addActionListener(_ -> {
            String username = username_field.getText();
            String passwd = new String(passwd_field.getPassword());

            if (account_manager.login(username, passwd)) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                new DashBoard(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong username or password!");
            }
        });

        register_btn.addActionListener(_ -> {
            String username = username_field.getText();
            String passwd = new String(passwd_field.getPassword());

            if (account_manager.register(username, passwd)) {
                JOptionPane.showMessageDialog(frame, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "User already exists!");
            }
        });

        frame.add(new JLabel("Username:"));
        frame.add(username_field);
        frame.add(new JLabel("Password:"));
        frame.add(passwd_field);
        frame.add(login_btn);
        frame.add(register_btn);
        frame.setVisible(true);
    }
}