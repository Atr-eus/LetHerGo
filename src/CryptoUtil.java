import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.logging.Logger;

public class CryptoUtil {
    public static final Logger logger = Logger.getLogger(Vault.class.getName());

    // why char[] instead of String? mutability, we want to shred the password from memory after use
    public static String hash_passwd(char[] passwd) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = new String(passwd).getBytes(StandardCharsets.UTF_8);
        byte[] hash = md.digest(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}