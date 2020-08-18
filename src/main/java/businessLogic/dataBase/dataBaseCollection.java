package businessLogic.dataBase;

import businessLogic.factories.Typer;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.Person;
import businessLogic.sourseDate.StudyGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class dataBaseCollection {

    private dataBaseManager dataBaseManager;
    String login = "unknown";
    Result result = new Result();
    paramsMaker paramsMaker;
    CollectionUpdater collectionUpdater;

    public dataBaseCollection(dataBaseManager dataBaseManager, CollectionUpdater collectionUpdater){
        this.dataBaseManager = dataBaseManager;
        this.collectionUpdater = collectionUpdater;
    }

    public void setOwner(String login) {
        this.login = login;
    }

    private void deleteNote(Map<String, Object> note){
            if (note.get("owner").equals(login)){
                if (note.get("groupadmin_id") == null){
                    String Id = Typer.typeRefact((Integer)note.get("id"));
                    dataBaseManager.executeUpdate("delete from stgroup where id = " + Id + ";");
                    return;
                }
                String grAdmId = Typer.typeRefact(note.get("groupadmin_id"));
                dataBaseManager.executeUpdate("delete from person where passportid = " + grAdmId + ";");
                dataBaseManager.executeUpdate("delete from stgroup where groupadmin_id = " + grAdmId + ";");
            }
    } // Подавать нужно полное поле

    public String removeBySBE(int a){
        List<Map<String, Object>> notes = dataBaseManager.executeQuery("select * from stgroup where shouldbeexpelled = " + Typer.typeRefact(a) + ";");
            for (Map<String, Object> note : notes){
                deleteNote(note);
            }
        return "Удаление в базе данных прошло успешно";

    }

    public String removeElement(int a ){
        List<Map<String, Object>> notes = dataBaseManager.executeQuery("select * from stgroup where id = " + Typer.typeRefact(a) + ";");
            Map<String, Object> mNote = notes.get(0);
           deleteNote(mNote);
        return "Удаление в базе данных прошло успешно";
    }

    public String removeLowerKey(Long a){
        List<Map<String, Object>> notes = dataBaseManager.executeQuery("select * from stgroup where id < " + Typer.typeRefact(a) + ";");
        for (Map<String, Object> note : notes){
            deleteNote(note);
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
        if (studyGroup.getGroupAdmin() == null){
            insertStudyGroup(studyGroup);
            return "Запись в базу данных прошла успешно";
        }
        List<Map<String, Object>> note = dataBaseManager.executeQuery("select passportid from person where passportid = " + Typer.typeRefact(studyGroup.getGroupAdmin().getPassportID()) + ";");
        if (!note.isEmpty()){
            return "Админ с таким же паспортом уже существует, запись не будет добавлена";
        }
        insertPerson(studyGroup.getGroupAdmin());
        insertStudyGroup(studyGroup);

        //String params = paramsMaker.makeParams(studyGroup.getGroupAdmin().getEverything(), studyGroup.getGroupAdmin().tableEnum, login);
        //dataBaseManager.executeUpdate("insert into person " + params + ";");
        //params = paramsMaker.makeParams(studyGroup.getEverything(), studyGroup.tableEnum, login);
        //dataBaseManager.executeUpdate("insert into stgroup " + params + ";");
        return "Запись в базу данных прошла успешно";
    }

    public String clear(){
        List<Map<String, Object>> notes = dataBaseManager.executeQuery("select * from stgroup where owner = " + Typer.typeRefact(login) + ";");
        for (Map<String, Object> note : notes){
            deleteNote(note);
        }
        return "Удаление прошло успешно";
    }

    public void update(){
        collectionUpdater.updateCollection();
    }
}
