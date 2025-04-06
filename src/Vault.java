import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

public class Vault {
    private final String db_name;

    Vault(String db_name) {
        this.db_name = db_name;
    }

    public void show_vault() {
        JFrame frame = new JFrame(db_name + " - Let Her Go");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        String[] columns = {"#", "Site", "Username", "Password"};
        DefaultTableModel tab_model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tab_model);

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db_name);
            Statement statement = connection.createStatement();

            ResultSet result_set = statement.executeQuery("SELECT site, username, password FROM entries");

            for (int i = 1; result_set.next(); ++i) {
                String site = result_set.getString("site");
                String username = result_set.getString("username");
                String passwd = result_set.getString("password");

                tab_model.addRow(new Object[]{i, site, username, passwd});
            }
        } catch (Exception e) {
            CryptoUtil.logger.log(Level.SEVERE, "An error occurred.", e);
        }

        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}