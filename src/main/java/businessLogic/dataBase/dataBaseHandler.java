package businessLogic.dataBase;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dataBaseHandler {

    private static Connection connection = null;
    private static Session session= null;

    private final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private final int lport = 5555;
    private final String sshUrl = "jdbc:postgresql://localhost:"+ lport +"/studs";

    private final String DB_USER = "s284201";
    private final String DB_PASS = "tkl821";

    public dataBaseHandler(String typeOfConnection){
        if (typeOfConnection.equals("ssh")){
            connectWithSHH();
        } else {
            connect();
        }
    }

    public Statement getStatement(){
        Statement statement = null;
        try {
            if (connection != null){
                statement = connection.createStatement();
            }
        } catch (SQLException e){
            System.out.println("Произашла ошибка создания стейтмента");
        }
        return statement;
    }

    public void connect(){
        connectToDataBase(DB_URL);
    }

    public void connectWithSHH(){

        makeSSH();
        connectToDataBase(sshUrl);

    }

    public void makeSSH(){
        try{

            final String rhost="pg";
            final int rport=5432;
            final String sshHost="se.ifmo.ru";
            final String user="s284201";
            final String password="tkl821";

            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, sshHost, 2222);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            System.out.println("Port Forwarded");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void connectToDataBase(String DB_URL){

        connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException e) {
            System.out.println("Соеденение с базой данных не удалось");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Соеденение с базой данных успешно"); }
    }

    public void closeConnection(){

        if (connection == null) return;
        try {
            connection.close();
            System.out.println("Соединение с базой данных разорвано сервером.");
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }

    public void closeSSHConnection(){

        if (connection == null) return;
        session.disconnect();
        System.out.println("Соединение с базой данных разорвано сервером.");
    }

}
