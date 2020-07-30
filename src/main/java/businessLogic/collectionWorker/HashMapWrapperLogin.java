package businessLogic.collectionWorker;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HashMapWrapperLogin implements CollectionWrapperLogin {

    private Map<String, byte[]> loginMap;

    public HashMapWrapperLogin(){
        loginMap = new HashMap();
        //byte[] pass = new byte[32];
        //String passStr = ""
        //loginMap.put("login", );
    }




    @Override
    public boolean askPass(String login, String Apass) {
        try{
            byte[] data = Apass.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest();
            byte[] pass = loginMap.get(login);
            return Arrays.equals(pass, digest);
        } catch (NoSuchAlgorithmException e){
            System.out.println("Проблема с расшифровкой пароля");
        }
        return false;
    }

    @Override
    public void register() {

    }
}
