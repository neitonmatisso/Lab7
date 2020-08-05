package businessLogic.dataBase;

import businessLogic.sourseDate.StudyGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dataBaseCollection {

    private dataBaseManager dataBaseManager;
    private paramsMaker paramsMaker;
    String login;

    public dataBaseCollection(dataBaseManager dataBaseManager, String login, paramsMaker paramsMaker){
        this.paramsMaker = paramsMaker;
        this.dataBaseManager = dataBaseManager;
        this.login = login;
    }

    private void deleteNote(ResultSet resultSet){
        try {
            if (resultSet.getString("owner").equals(login)){
                dataBaseManager.executeUpdate("delete from person where passportid = " + resultSet.getString("groupadmin_id"));
                dataBaseManager.executeUpdate("delete from stgroup where id = " + resultSet.getString("id"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении dataBaseCollection");
        }
    }

    public String removeBySBE(int a){
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where shouldbeexpelled = " + a + ";");
        try {
            while (notes.next()){
                deleteNote(notes);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных sbe dataBaseCollection");
        }
        return "Удаление в базе данных прошло успешно";

    }

    public String removeElement(int a ){
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where id = " + a + ";");
        try {
           notes.next();
           deleteNote(notes);
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных remove dataBaseCollection");
        }
        return "Удаление в базе данных прошло успешно";
    }

    public String removeLowerKey(Long a){
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where id < " + a + ";");
        try {
            while (notes.next()){
                deleteNote(notes);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных remLowerKey dataBaseCollection");
        }
        return "Удаление прошло успешно";
    }

    public String insert (StudyGroup studyGroup){
        String params = paramsMaker.makeParams(studyGroup.getEverything(), studyGroup.tableEnum);
        dataBaseManager.executeUpdate("insert into stgroup " + params + ";");
        return "Запись в бащу данных прошла успешно";
    }
}
