package test;


import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.*;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.factories.Typer;
import businessLogic.sourseDate.Person;
import businessLogic.sourseDate.StudyGroup;

import java.math.BigInteger;


public class AppTest{

    public static void main(String[] args) throws InterruptedException {
        System.out.println(BigInteger.class);
        //System.out.println("Check");
        //dataBaseHandler dbH = new dataBaseHandler("ssh");
        //dataBaseManager dataBaseManager = new dataBaseManager(dbH);
        //LoginManager lm = new LoginManager(dataBaseManager);
        //lm.loginCheck("login", "pass");
        //lm.register("login", "pass");
        //System.out.println(lm.passCheck("login", "pass"));

        //HashMapWrapper hashMap = new HashMapWrapper();
        //CollectionUpdater dbc = new CollectionUpdater(dataBaseManager, hashMap);
        //dbc.updateCollection();
        //dbc.getCollectionUpdater().join();
        //System.out.println(hashMap.show());
        //dataBaseManager.executeUpdate("insert into person values ('oleg', '100');");
        // problem in semesterEnum
        //insert into stgroup (name, coordinates, creationDate, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin_id, owner) values ('P3113', '1.0,1', '2020-Aug-04 09:25:04', 30, null, null, null, 'login');













    }




}
