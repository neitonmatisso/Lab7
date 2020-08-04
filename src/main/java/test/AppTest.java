package test;


import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.LoginManager;
import businessLogic.dataBase.dataBaseHandler;
import businessLogic.dataBase.dataBaseManager;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.factories.Typer;
import businessLogic.sourseDate.Coordinates;
import businessLogic.sourseDate.StudyGroup;
import javafx.util.Pair;
import jdk.internal.org.objectweb.asm.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import businessLogic.dataBase.dataBaseCollection;


public class AppTest{

    public static void main(String[] args) throws InterruptedException {
        dataBaseHandler dbH = new dataBaseHandler("ssh");
        dataBaseManager dataBaseManager = new dataBaseManager(dbH);
        //LoginManager lm = new LoginManager(dataBaseManager);
        //lm.register("login", "pass");
        //System.out.println(lm.passCheck("login", "pass"));

        HashMapWrapper hashMap = new HashMapWrapper();
        dataBaseCollection dbc = new dataBaseCollection(dataBaseManager, hashMap);
        dbc.updateCollection();
        dbc.getCollectionUpdater().join();
        System.out.println(hashMap.show());














    }




}
