package businessLogic.dataBase;

import businessLogic.factories.Typer;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.Person;
import businessLogic.sourseDate.StudyGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dataBaseCollection {

    private dataBaseManager dataBaseManager;
    String login = "unknown";
    Result result = new Result();
    paramsMaker paramsMaker;

    public dataBaseCollection(dataBaseManager dataBaseManager){
        this.dataBaseManager = dataBaseManager;
    }

    public void setOwner(String login) {
        this.login = login;
    }

    private void deleteNote(ResultSet resultSet){
        try {
            if (resultSet.getString("owner").equals(login)){
                System.out.println("first");
                dataBaseManager.executeUpdate("delete from person where passportid = " + Typer.typeRefact(resultSet.getString("groupadmin_id")) + ";");
                System.out.println("second");
                System.out.println(Typer.typeRefact(resultSet.getString("id")));
                dataBaseManager.executeUpdate("delete from stgroup where id = " + Typer.typeRefact(resultSet.getString("id")) + ";");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении deleteNote dataBaseCollection");
        }
    } // Подавать нужно полное поле

    public String removeBySBE(int a){
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where shouldbeexpelled = " + Typer.typeRefact(a) + ";");
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
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where id = " + Typer.typeRefact(a) + ";");
        try {
           notes.next();
           deleteNote(notes);
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных remove dataBaseCollection");
        }
        return "Удаление в базе данных прошло успешно";
    }

    public String removeLowerKey(Long a){
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where id < " + Typer.typeRefact(a) + ";");
        try {
            while (notes.next()){
                deleteNote(notes);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных remLowerKey dataBaseCollection");
            return ("Ошибка загрузки данных remLowerKey dataBaseCollection");
        }
        return "Удаление прошло успешно";
    }

    public String insertStudyGroup (StudyGroup studyGroup){

        String params = paramsMaker.makeParams(studyGroup.getEverything(), studyGroup.tableEnum, login);
        dataBaseManager.executeUpdate("insert into stgroup " + params + ";");
        return "Запись в бащу данных прошла успешно";
    }

    public String insertPerson (Person person){
        String params = paramsMaker.makeParams(person.getEverything(), person.tableEnum, login);
        dataBaseManager.executeUpdate("insert into person " + params + ";");
        return "Запись в базу данных прошла успешно";
    }

    public String insertStGroupAndPerson(StudyGroup studyGroup){
        ResultSet note = dataBaseManager.executeQuery("select passportid from person where passportid = " + Typer.typeRefact(studyGroup.getGroupAdmin().getEverything().get(1)) + ";");
        try {
            if (note.next()){
                return "Админ с таким же паспортом уже существует, запись не будет добавлена";
            }
        } catch (SQLException e) {
            System.out.println("Ошибка проверки уникальности паспорта inPerson dataBaseCollection");
            return "Ошибка проверки уникальности паспорта inPerson dataBaseCollection";
        }
        String params = paramsMaker.makeParams(studyGroup.getGroupAdmin().getEverything(), studyGroup.getGroupAdmin().tableEnum, login);
        dataBaseManager.executeUpdate("insert into person " + params + ";");
        params = paramsMaker.makeParams(studyGroup.getEverything(), studyGroup.tableEnum, login);
        dataBaseManager.executeUpdate("insert into stgroup " + params + ";");
        return "Запись в базу данных прошла успешно";
    }

    public String clear(){
        ResultSet notes = dataBaseManager.executeQuery("select * from stgroup where owner = " + Typer.typeRefact(login) + ";");
        try {
            while (notes.next()){
                deleteNote(notes);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных clear dataBaseCollection");
        }
        return "Удаление прошло успешно";
    }
}
