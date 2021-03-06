package businessLogic.dataBase;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
public class LoginManager{

    private dataBaseManager dbM;
    private String login = null;

    public LoginManager(dataBaseManager dbM){
        this.dbM = dbM;
    }

    public boolean loginCheck(String login, String Apass){
        if (passCheck(login, Apass)){
            this.login = login;
            return true;
        }
        return false;
    }

    public boolean passCheck(String login, String Apass){
        if (login.equals("") || Apass.equals("")){
            return false;
        }
        String encApass = encrypt(Apass);
        String query = "select password from users where login = '" + login + "';";
        List<Map<String, Object>> resultSet = dbM.executeQuery(query);
        Map<String, Object> mResultSet = resultSet.get(0);
        String pass = (String) mResultSet.get("password");
        return encApass.equals(pass);
    }

    public String encrypt(String pass){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] bytes = md.digest(pass.getBytes());
            BigInteger integers = new BigInteger(1, bytes);
            StringBuilder newPassword = new StringBuilder(integers.toString(16));
            while (newPassword.length() < 96) {
                newPassword.append("0");
            }
            return newPassword.toString();
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("Не найден алгоритм хэширования пароля! LoginManager");
            throw new IllegalStateException(exception);
        }
    }

    public String askPassBD(String login){
        String query = "SELECT password";
        return "";
    }

    public String register(String login, String pass) {
            List<Map<String, Object>> logins = dbM.executeQuery("SELECT login from users");
            for (Map<String, Object> mLogin : logins){
                if (login.equals(mLogin.get("login"))){
                    return "Такой логин уже существует";
                }
            }

        String query = "INSERT INTO users (id, login, password) VALUES (nextval('id_stgroup_serial'), '" +
                login +"', '" + encrypt(pass) + "');";
        dbM.executeUpdate(query);
        return "Регистрация пользователя " + login + " прошла успешно";

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
