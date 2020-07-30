package itmo;

import static org.junit.Assert.assertTrue;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.Test;
import org.postgresql.util.PSQLException;

/**
 * Unit test for simple App.
 */
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

class dataBaseConnectionManager {

    private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s284201";
    private static final String PASS = "tkl821";
    private static Connection connection;

    public dataBaseConnectionManager(){
        System.out.println("Подключение к базе данных");
        connectToDataBase();
    }

    static Session sshConnect(){

            int lport=5656;
            String rhost="se.ifmo.ru";
            String host="se.ifmo.ru";
            int rport = 2222;
            String user="s284201";
            String password="tkl821";
            Connection conn = null;
            Session session= null;
            try{
                //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                JSch jsch = new JSch();
                session = jsch.getSession(user, host, 2222);
                session.setPassword(password);
                session.setConfig(config);
                session.connect();
                System.out.println("Connected " + session.isConnected());
                int assinged_port = session.setPortForwardingL(lport, rhost, rport);
                System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
                System.out.println("Port Forwarded");


            }catch(Exception e){
                e.printStackTrace();
            }finally{
                return session;

            }
        }


    static void connectToDataBase(){
        Session session = null;
        connection = null;
        try {
            session = sshConnect();
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Соеденение с базой данных не удалось");
            e.printStackTrace();
            session.disconnect();
            return;

        }

        if (connection != null) {
            System.out.println("Соеденение с базой данных успешно"); }
    }

    public void closeConnection(){

        if (connection == null) return;
        try {
            connection.close();
            System.out.println("Соединение с базой данных разорвано.");
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }

    public static class AppTest{
        public static void main(String[] args) {
            dataBaseConnectionManager DB = new dataBaseConnectionManager();
        }
    }


}

