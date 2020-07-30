package itmo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



class AppTest{

    public static byte[] encrypt(String pass){
        byte[] hash = null;
        try{
            String pepper = "63(8:^";
            MessageDigest md = MessageDigest.getInstance("SHA-384");
             hash = md.digest((pass + pepper).getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e){
            System.out.println("Проблема с расшифровкой пароля");
        }
        return hash;
    }

    public static void main(String[] args) {
        String pass = "12345678";
        System.out.println();


    }


}
