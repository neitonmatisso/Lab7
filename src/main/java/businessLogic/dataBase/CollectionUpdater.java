package businessLogic.dataBase;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.factories.IdGenerator;
import businessLogic.factories.Typer;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CollectionUpdater {

    private dataBaseManager dbM;
    private HashMapWrapper HashMap;
    private final Result result = new Result();
    private Thread CollectionUpdater;

    public CollectionUpdater(dataBaseManager dbM, HashMapWrapper HashMap){
        this.dbM = dbM;
        this.HashMap = HashMap;
    }

    public void updateCollection(){
        HashMap.clear();
        List<Map<String, Object>> idsDB = dbM.executeQuery("select id from stgroup;");
        String[] ids_raw = new String[0];

        ArrayList<String> id_strings = new ArrayList<>();
        for (Map<String, Object> i : idsDB){
            id_strings.add(((Integer) i.get("id")).toString());
        }
        ids_raw = new String[id_strings.size()];
        for (int i = 0; i < id_strings.size(); i++){
            ids_raw[i] = id_strings.get(i);
        }

        HashMap.clear();
        System.out.println("Очищено");
        final String[] ids = ids_raw;

        Runnable task = () -> {

            if (ids.length == 0){
                result.writeResult("Ошибка с загрузкой колликции. \n Тут или танцы с бубном или админ...");
                System.out.println("Ошибка с первоначальными id");
            }
            for (String id : ids){
                System.out.println(HashMap.addElementWithID(Long.parseLong(id.split("\\n")[0]), downloadStudyGroup(id.split("\\n")[0])));
            }
            System.out.println("Коллекция загружена");
        };

        CollectionUpdater = new Thread(task);
        CollectionUpdater.start();

    }

    public Thread getCollectionUpdaterThread() {
        return CollectionUpdater;
    }

    public StudyGroup downloadStudyGroup (String group_id){
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
        List<Map<String, Object>> ids = dbM.executeQuery("select name from stgroup where id = " + id + ";");
        Map<String, Object> aid = ids.get(0);
        name = (String) aid.get("name");
        return name;
    }

    private Coordinates askCoordinates(String id){
        Coordinates coordinates = null;
            List<Map<String, Object>> cordsDB = dbM.executeQuery("select coordinates from stgroup where id = " + id + ";");
            Map<String, Object> mCordsBD = cordsDB.get(0);
            String cords = mCordsBD.get("coordinates").toString();
            String crd = cords.split("\\(")[1];
            crd = crd.split("\\)")[0];
            Double x = Double.parseDouble(crd.split(",")[0]);
            Integer y = Integer.parseInt(crd.split(",")[1].split("\\.")[0]);
            coordinates= new Coordinates(x,y);
        return coordinates;
    }

    private Date askCreationDate(String id){
        Date date = null;
        try {
            List<Map<String, Object>> crdateDB = dbM.executeQuery("select creationdate from stgroup where id =" + id + ";");
            Map<String, Object> mCrdateDB = crdateDB.get(0);
            String creation = mCrdateDB.get("creationdate").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = creation.split("\\.")[0];
            date = format.parse(str);
        } catch (ParseException e) {
            System.out.println("Ошибка с разбором даты CollectionUpdater");
        }
        return date;
    }

    private int askShouldBeExpelled(String id){
        Integer sbEx = -1;
            List<Map<String, Object>> sbExDB = dbM.executeQuery("select shouldbeexpelled from stgroup where id = " + id + ";");
            Map<String, Object> mSbExDB = sbExDB.get(0);
            sbEx = (Integer) mSbExDB.get("shouldbeexpelled");
        return sbEx;
    }

    private FormOfEducation askFormOfEducation(String id){
        FormOfEducation formOfEducation = null;
            List<Map<String, Object>> formOEDB = dbM.executeQuery("select formofeducation from stgroup where id =" + id + ";");
            Map<String, Object> mFormOEDB = formOEDB.get(0);
            String form = (String) mFormOEDB.get("formofeducation");
            if (form == null){
                return null;
            }
            switch (form){
                case "FULL_TIME_EDUCATION": formOfEducation = FormOfEducation.FULL_TIME_EDUCATION; break;
                case "DISTANCE_EDUCATION": formOfEducation = FormOfEducation.DISTANCE_EDUCATION; break;
                case "EVENING_CLASSES": formOfEducation = FormOfEducation.EVENING_CLASSES; break;
                default: formOfEducation = null;
            }
        return formOfEducation;
    }

    private Semester askSemester(String id){
        Semester semester = null;
            List<Map<String, Object>> semesterDB = dbM.executeQuery("select semesterenum from stgroup where id =" + id + ";");
            Map<String, Object> mSemesterDB = semesterDB.get(0);
            String sem = (String) mSemesterDB.get("semesterenum");
            switch (sem){
                case "FIRST": semester =  Semester.FIRST; break;
                case "THIRD": semester =  Semester.THIRD; break;
                case "FOURTH": semester =  Semester.FOURTH; break;
                case "EIGHTH": semester =  Semester.EIGHTH; break;
                default: semester =  null;
            }
        return semester;
    }

    //In data base of studyGroup you can find only admin's id.
    // So, find his name using his id at persons table

    private String askPersonId(String id){
        String adminID = null;
            List<Map<String, Object>> adminIdDB = dbM.executeQuery("select groupadmin_id from stgroup where id =" + id + ";");
            Map<String, Object> mAdminIdDB = adminIdDB.get(0);
            adminID = (String) mAdminIdDB.get("groupadmin_id");
            if(adminID == null){
                return null;
            }
        return adminID.split("\\n")[0];
    }

    private String askPersonName(String admin_id){
        String adminName = null;
            List<Map<String, Object>> adminIdDB = dbM.executeQuery("select name from person where passportid = " + Typer.typeRefact(admin_id) + ";");
            if(adminIdDB.isEmpty()){
                return null;
            }
            Map<String, Object> mAdminIdDB = adminIdDB.get(0);
            adminName = (String) mAdminIdDB.get("name");
        return adminName;
    }

    private String askOwner(String id){
        String ownerName = null;
            List<Map<String, Object>> adminIdDB = dbM.executeQuery("select owner from stgroup where id =" + id + ";");
            Map<String, Object> mAdminIdDB = adminIdDB.get(0);
            ownerName = (String) mAdminIdDB.get("owner");
        return ownerName.split("\\n")[0];
    }


}
