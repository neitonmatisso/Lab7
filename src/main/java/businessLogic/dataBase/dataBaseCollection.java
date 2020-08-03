package businessLogic.dataBase;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.factories.IdGenerator;
import businessLogic.sourseDate.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class dataBaseCollection {

    private dataBaseManager dbM;
    private HashMapWrapper HashMap;

    public dataBaseCollection(dataBaseManager dbM, HashMapWrapper HashMap){
        this.dbM = dbM;
        this.HashMap = HashMap;
    }

    public void updateCollection(){
        ResultSet ids = dbM.executeQuery("select id from stgroup;");
        HashMap.clear();

        Runnable task = () -> {
            try {
                while(ids.next()){
                    HashMap.addElement(uploadStudyGroup(ids.getString("id")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        Thread CollectionUpdater = new Thread(task);
        CollectionUpdater.start();

    }

    public StudyGroup uploadStudyGroup (String group_id){
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(Long.parseLong(group_id));
        studyGroup.setName(askGroupName(group_id));
        studyGroup.setCoordinates(askCoordinates(group_id));
        studyGroup.setCreationDate(askCreationDate(group_id));
        studyGroup.setShouldBeExpelled(askShouldBeExpelled(group_id));
        studyGroup.setFormOfEducation(askFormOfEducation(group_id));
        studyGroup.setSemesterEnum(askSemester(group_id));
        String person_id = askPersonId(group_id);
        Person person = new Person(askPersonName(person_id),person_id);
        studyGroup.setGroupAdmin(person);
        studyGroup.setOwner(askOwner(group_id));
        return studyGroup;
    }

    private String askGroupName(String id){
        String name = "corrupted";
        try {
            ResultSet ids = dbM.executeQuery("select name from stgroup where id = " + id + ";");
            name = ids.getString("name");
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении имени dataBaseCollection");
        }
        return name;
    }

    private Coordinates askCoordinates(String id){
        Coordinates coordinates = null;

        try {
            ResultSet cordsDB = dbM.executeQuery("select coordinates from stgroup where id = " + id + ";");
            String cords = cordsDB.getString("coordinates");
            String crd = cords.split("\\(")[1];
            crd = crd.split("\\)")[0];
            Double x = Double.parseDouble(crd.split(",")[0]);
            Integer y = Integer.parseInt(crd.split(",")[1].split("\\.")[0]);
            coordinates= new Coordinates(x,y);
        } catch (SQLException e) {
            System.out.println("Ошибка пи загрузке координат dtaBaseCollection");
        }
        return coordinates;
    }

    private Date askCreationDate(String id){
        Date date = null;
        try {
            ResultSet crdateDB = dbM.executeQuery("select creationdate from stgroup where id =" + id + ";");
            String creation = crdateDB.getString("creationdate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = creation.split("\\.")[0];
            date = format.parse(str);
        } catch (SQLException e) {
            System.out.println("Ошибка с загрузкой даты dataBaseCollection");
        } catch (ParseException e) {
            System.out.println("Ошибка с разбором даты dataBaseCollection");
        }
        return date;
    }

    private int askShouldBeExpelled(String id){
        Integer sbEx = -1;
        try {
            ResultSet sbExDB = dbM.executeQuery("select shouldbeexpelled from stgroup where id" + id + ";");
            sbEx = Integer.parseInt(sbExDB.getString("shouldbeexpelled"));
        } catch (SQLException e) {
            System.out.println("Ошибка с загрузкой колличества исключенных");
        }
        return sbEx;
    }

    private FormOfEducation askFormOfEducation(String id){
        FormOfEducation formOfEducation = null;
        try {
            ResultSet formOEDB = dbM.executeQuery("select formofeducation from stgroup where id =" + id + ";");
            String form = formOEDB.getString("formofeducation");
            switch (form){
                case "FULL_TIME_EDUCATION": formOfEducation = FormOfEducation.FULL_TIME_EDUCATION; break;
                case "DISTANCE_EDUCATION": formOfEducation = FormOfEducation.DISTANCE_EDUCATION; break;
                case "EVENING_CLASSES": formOfEducation = FormOfEducation.EVENING_CLASSES; break;
                default: formOfEducation = null;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка с загрузкой формы обучения");
        }
        return formOfEducation;
    }

    private Semester askSemester(String id){
        Semester semester = null;
        try {
            ResultSet semesterDB = dbM.executeQuery("select semesterenum from stgroup where id =" + id + ";");
            String sem = semesterDB.getString("semesterenum");
            switch (sem){
                case "FIRST": semester =  Semester.FIRST; break;
                case "THIRD": semester =  Semester.THIRD; break;
                case "FOURTH": semester =  Semester.FOURTH; break;
                case "EIGHTH": semester =  Semester.EIGHTH; break;
                default: semester =  null;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка с загрузкой формы обучения");
        }
        return semester;
    }

    //In data base of studyGroup you can find only admin's id.
    // So, find his name using his id at persons table

    private String askPersonId(String id){
        String adminID = null;
        try {
            ResultSet adminIdDB = dbM.executeQuery("select groupadmin_id from stgroup where id =" + id + ";");
            adminID = adminIdDB.getString("groupadmin_id");
        } catch (SQLException e) {
            System.out.println("Ошибка запроса ID админа dataBaseCollection");
        }
        return adminID;
    }

    private String askPersonName(String admin_id){
        String adminName = null;
        try {
            ResultSet adminIdDB = dbM.executeQuery("select name from person where passportid =" + admin_id + ";");
            adminName = adminIdDB.getString("name");
        } catch (SQLException e) {
            System.out.println("Ошибка запроса имени админа dataBaseCollection");
        }
        return adminName;
    }

    private String askOwner(String id){
        String ownerName = null;
        try {
            ResultSet adminIdDB = dbM.executeQuery("select owner from stgroup where id =" + id + ";");
            ownerName = adminIdDB.getString("owner");
        } catch (SQLException e) {
            System.out.println("Ошибка запроса обладателя записи dataBaseCollection");
        }
        return ownerName;
    }
}
