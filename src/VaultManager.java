import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.Arrays;

public class VaultManager {
    public String create_db(String db_name, char[] master_passwd) {
        if(db_name.isEmpty() || master_passwd.length == 0) {
            return "Please enter a valid database name and/or master password.";
        }

        try {
            String db_path = db_name + ".lhgdb";
            File db_file = new File(db_path);
            if(db_file.exists()) {
                return "Database already exists.";
            }

            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db_path);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS meta (hash TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS entries (site TEXT, username TEXT, password TEXT)");

            String hashed_passwd = CryptoUtil.hash_passwd(master_passwd);
            PreparedStatement prepared_statement = connection.prepareStatement("INSERT INTO meta (hash) VALUES (?)");
            prepared_statement.setString(1, hashed_passwd);
            prepared_statement.executeUpdate();
            connection.close();

            return "Database created.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating DB.";
        } finally {
            // shred password to prevent leakage
            Arrays.fill(master_passwd, '0');
        }
    }

    public String open_db(String db_name, char[] master_passwd) {
        if (db_name.isEmpty() || master_passwd.length == 0) {
            return "Please enter a valid database name and/or master password.";
        }

        try {
            String db_path = db_name + ".lhgdb";
            File db_file = new File(db_path);
            if(!db_file.exists()) {
                return "Database doesn't exists.";
            }

            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db_path);
            Statement statement = connection.createStatement();
            ResultSet result_set = statement.executeQuery("SELECT hash FROM meta LIMIT 1");

            if(result_set.next()) {
                String stored_hash = result_set.getString("hash");
                String entered_hash = CryptoUtil.hash_passwd(master_passwd);

                if(stored_hash.equals(entered_hash)) {
                    new Vault(db_name);
                    return "Database opened.";
                } else {
                    return "Incorrect master password.";
                }
            } else {
                connection.close();
                return "Database file is not valid.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating DB.";
        } finally {
            Arrays.fill(master_passwd, '0');
        }
    }
}