package businessLogic.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dataBaseConnectionManager {

    private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "pg";
    private static final String PASS = "studs";
    private static Connection connection;

    public dataBaseConnectionManager(){

        connectToDataBase();
    }

    static void connectToDataBase(){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден, ошибка соеденения с базой данных");
            e.printStackTrace();
            return;
        }

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


}
