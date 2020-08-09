package businessLogic.dataBase;

import businessLogic.dataBase.dataBaseManager;
import businessLogic.mainApp.Result;
import connectionPackage.Connection;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
        String encApass = encrypt(Apass);
        ResultSet resultSet;
        String query = "select password from users where login = '" + login + "';";
        resultSet = dbM.executeQuery(query);
        try{
            resultSet.next();
            String pass = resultSet.getString("password");
            return encApass.equals(pass);
        } catch (SQLException e) {
            System.out.println("Произашла ошибка при проверке пароля или такого логина не существует");
            return false;
        }
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
        try {
            ResultSet resultSet = dbM.executeQuery("SELECT login from users");
            while (resultSet.next()){
                if (login.equals(resultSet.getString("login"))){
                    return "Такой логин уже существует";
                }
            }
        } catch (SQLException e) {
            System.out.println("Произашла ошибка при регистрации LoginManager");
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
