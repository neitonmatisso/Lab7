package businessLogic.dataBase;

import businessLogic.mainApp.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataBaseManager {

    private Statement statement;
    private dataBaseHandler handler;

    public dataBaseManager(dataBaseHandler handler){
        this.handler = handler;
        this.statement = handler.getStatement();
    }

    public void executeUpdate(String query){
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Ошибка при запросе");
        }
    }

    public ResultSet executeQuery(String query){
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Ошибка при запросе");
        }
        return resultSet;
    }
}
