import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Login implements ActionListener {
    JFrame frame = new JFrame();
    JButton login_btn = new JButton("Login");
    JButton reset_btn = new JButton("Reset");
    JTextField username_field = new JTextField();
    JPasswordField password_field = new JPasswordField();
    JLabel username_label = new JLabel("Username");
    JLabel password_label = new JLabel("Password");
    JLabel message_label = new JLabel();
    HashMap<String,String> credentials;

    Login(HashMap<String, String> creds) {
        credentials = creds;

        username_label.setBounds(100, 100, 75, 25);
        password_label.setBounds(100, 150, 75, 25);
        message_label.setBounds(100, 200, 800, 45);
        message_label.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        username_field.setBounds(200, 100, 200, 25);
        password_field.setBounds(200, 150, 200, 25);

        login_btn.setBounds(100, 250, 100, 25);
        login_btn.setFocusable(false);
        login_btn.addActionListener(this);
        reset_btn.setBounds(250, 250, 100, 25);
        reset_btn.setFocusable(false);
        reset_btn.addActionListener(this);

        frame.add(username_label);
        frame.add(password_label);
        frame.add(message_label);
        frame.add(username_field);
        frame.add(password_field);
        frame.add(login_btn);
        frame.add(reset_btn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == reset_btn) {
            username_field.setText("");
            password_field.setText("");
        } else if (event.getSource() == login_btn) {
            String username = username_field.getText();
            String password = new String(password_field.getPassword());

            if(credentials.containsKey(username)) {
                if(credentials.get(username).equals(password)) {
                    message_label.setText("Logged in!");

                    frame.dispose();
                    DashBoard dash_board = new DashBoard(username);
                }
            } else {
                message_label.setText("Invalid credentials!");
            }
        }
    }
}
