package test;


import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.*;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.factories.Typer;
import businessLogic.sourseDate.Person;
import businessLogic.sourseDate.StudyGroup;


public class AppTest{

    public static void main(String[] args) throws InterruptedException {
        dataBaseHandler dbH = new dataBaseHandler("ssh");
        dataBaseManager dataBaseManager = new dataBaseManager(dbH);
        LoginManager lm = new LoginManager(dataBaseManager);
        lm.loginCheck("login", "pass");
        //lm.register("login", "pass");
        //System.out.println(lm.passCheck("login", "pass"));

        //HashMapWrapper hashMap = new HashMapWrapper();
        //dataBaseCollection dbc = new dataBaseCollection(dataBaseManager, hashMap);
        //dbc.updateCollection();
        //dbc.getCollectionUpdater().join();
        //System.out.println(hashMap.show());
        StudyGroup group = new StudyGroupFactory().createStudyGroup();
        paramsMaker paramsMaker = new paramsMaker(lm.getLogin());
        String params = paramsMaker.makeParams(group.getEverything(),tablesEnum.STGROUP);
        //dataBaseManager.executeUpdate("insert into stgroup " + params + ";");
        params = "insert into stgroup " + params;
        System.out.println(params);
        // problem in semesterEnum
        //insert into stgroup (name, coordinates, creationDate, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin_id, owner) values ('P3113', '1.0,1', '2020-Aug-04 09:25:04', 30, null, null, null, 'login');













    }




}
