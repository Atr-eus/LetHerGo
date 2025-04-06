import java.io.File;
import java.sql.*;
import java.util.Arrays;

public class VaultManager {
    public DBStatus create_db(String db_name, char[] master_passwd) {
        if (db_name.isEmpty() || master_passwd.length == 0) {
            System.out.println("Please enter a valid database name and/or master password.");
            return DBStatus.CREATE_FAILED;
        }

        try {
            String db_path = db_name + ".lhgdb";
            File db_file = new File(db_path);
            if (db_file.exists()) {
                System.out.println("Database already exists.");
                return DBStatus.CREATE_FAILED;
            }

            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db_path);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS meta (hash TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS entries (site TEXT, username TEXT, password TEXT)");

            statement.execute("INSERT INTO entries (site, username, password) VALUES ('example.com', 'user1', 'pass1')");
            statement.execute("INSERT INTO entries (site, username, password) VALUES ('another.com', 'admin', 'adminpass')");

            String hashed_passwd = CryptoUtil.hash_passwd(master_passwd);
            PreparedStatement prepared_statement = connection.prepareStatement("INSERT INTO meta (hash) VALUES (?)");
            prepared_statement.setString(1, hashed_passwd);
            prepared_statement.executeUpdate();
            connection.close();

            System.out.println("Database created.");
            return DBStatus.CREATE_SUCCESSFUL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating database.");
            return DBStatus.CREATE_FAILED;
        } finally {
            // shred password to prevent leakage
            Arrays.fill(master_passwd, '0');
        }
    }

    public DBStatus open_db(String db_name, char[] master_passwd) {
        if (db_name.isEmpty() || master_passwd.length == 0) {
            System.out.println("Please enter a valid database name and/or master password.");
            return DBStatus.OPEN_FAILED;
        }

        try {
            String db_path = db_name + ".lhgdb";
            File db_file = new File(db_path);
            if (!db_file.exists()) {
                System.out.println("Database already exists.");
                return DBStatus.OPEN_FAILED;
            }

            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + db_path);
            Statement statement = connection.createStatement();
            ResultSet result_set = statement.executeQuery("SELECT hash FROM meta LIMIT 1");

            if (result_set.next()) {
                String stored_hash = result_set.getString("hash");
                String entered_hash = CryptoUtil.hash_passwd(master_passwd);

                if (stored_hash.equals(entered_hash)) {
                    System.out.println("Database opened.");

                    new Vault(db_path).show_vault();
                    return DBStatus.OPEN_SUCCESSFUL;
                } else {
                    System.out.println("Incorrect master password.");
                    return DBStatus.OPEN_FAILED;
                }
            } else {
                connection.close();
                System.out.println("Database file is invalid.");
                return DBStatus.OPEN_FAILED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error opening database.");
            return DBStatus.OPEN_FAILED;
        } finally {
            Arrays.fill(master_passwd, '0');
        }
    }
}