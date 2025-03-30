public class Main {
    public static void main(String[] args) {
        AccountManager acc_manager = new AccountManager();
        Login login = new Login(acc_manager.get_credentials());
    }
}