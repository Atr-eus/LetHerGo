import javax.swing.*;
import java.awt.*;

public class OpenVaultGUi {
    private JFrame frame;
    private JTextField db_name_field;
    private JPasswordField master_passwd_field;
    private JLabel status_label;
    private VaultManager vault_manager;

    public OpenVaultGUi() {
        vault_manager = new VaultManager();
    }

    public void UI() {
        frame = new JFrame();
        frame.setTitle("Open Vault - Let Her GO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 6));

        JButton create_db_btn = new JButton("Create Database");
        JButton open_db_btn = new JButton("Open Database");

        db_name_field = new JTextField();
        master_passwd_field = new JPasswordField();
        status_label = new JLabel("Status: Waiting...");

        panel.add(new JLabel("Database Name:"));
        panel.add(db_name_field);
        panel.add(new JLabel("Master Password:"));
        panel.add(master_passwd_field);
        panel.add(create_db_btn);
        panel.add(open_db_btn);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(status_label, BorderLayout.SOUTH);

        create_db_btn.addActionListener(e -> {
            String db_name = db_name_field.getText();
            char[] master_passwd = master_passwd_field.getPassword();
            String res = vault_manager.create_db(db_name, master_passwd);
            status_label.setText(res);
        });

        open_db_btn.addActionListener(e -> {
            String db_name = db_name_field.getText();
            char[] master_passwd = master_passwd_field.getPassword();
            String res = vault_manager.open_db(db_name, master_passwd);
            status_label.setText(res);
        });

        frame.setVisible(true);
    }
}