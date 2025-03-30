import java.util.HashMap;

public class AccountManager {
    HashMap<String, String> credentials = new HashMap<>();
    AccountManager() {
        credentials.put("admin", "admin");
        credentials.put("dumb_user", "123");
    }

    protected HashMap<String, String> get_credentials() {
       return credentials;
    }
}
