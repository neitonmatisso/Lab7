package test;

import businessLogic.collectionWorker.LoginManager;
import businessLogic.dataBase.dataBaseHandler;
import businessLogic.dataBase.dataBaseManager;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



public class AppTest{

    public static void main(String[] args) {
        dataBaseHandler dbH = new dataBaseHandler("ssh");
        dataBaseManager dataBaseManager = new dataBaseManager(dbH);
        LoginManager lm = new LoginManager(dataBaseManager);
        //lm.register("login", "pass");
        System.out.println(lm.passCheck("login", "pass"));




    }




}
