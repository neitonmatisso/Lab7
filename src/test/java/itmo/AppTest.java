package itmo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

class dataBaseConnectionManager {

    private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s284201";
    private static final String PASS = "tkl821";
    private static Connection connection;

    public dataBaseConnectionManager(){
        System.out.println("Подключение к базе данных");
        connectToDataBase();
    }

    static void connectToDataBase(){

        connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Соеденение с базой данных не удалось");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Соеденение с базой данных успешно"); }
    }

    public void closeConnection(){

        if (connection == null) return;
        try {
            connection.close();
            System.out.println("Соединение с базой данных разорвано.");
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }

    public static class AppTest{
        public static void main(String[] args) {
            dataBaseConnectionManager DB = new dataBaseConnectionManager();
        }
    }


}

