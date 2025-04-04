import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.*;

public class AccountManager {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(AccountManager.class.getName());

    public AccountManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:accounts.lhgdb");

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT UNIQUE NOT NULL, password TEXT NOT NULL)");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "SQL error occurred!", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred!", e);
        }
    }

    private String hash_passwd(String passwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(passwd.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "SQL error occurred!", e);
            return null;
        }
    }

    public boolean register(String username, String passwd) {
        String hashed_passwd = hash_passwd(passwd);
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement prepared_statement = connection.prepareStatement(sql)) {
            prepared_statement.setString(1, username);
            prepared_statement.setString(2, hashed_passwd);
            prepared_statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred!", e);

            return false;
        }
    }

    public boolean login(String username, String passwd) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (PreparedStatement prepared_statement = connection.prepareStatement(sql)) {
            prepared_statement.setString(1, username);
            ResultSet rs = prepared_statement.executeQuery();

            if (rs.next()) {
                return rs.getString("password").equals(hash_passwd(passwd));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error occurred!", e);
        }

        return false;
    }
}