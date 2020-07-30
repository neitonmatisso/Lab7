package itmo;



import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;


public class test2 {

    /**
     * Java Program to connect to the remote database through SSH using port forwarding
     * @author Pankaj@JournalDev
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        int lport=5432;
        String rhost="pg";
        String host="se.ifmo.ru";
        int rport = 5432;
        String user="s284201";
        String password="tkl821";
        final String DB_URL = "jdbc:postgresql://pg:5432/studs";
        final String USER = "s284201";
        final String PASS = "tkl821";
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

            //mysql database connectivity
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println ("Database connection established");
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
                System.out.println("Closing Database Connection");
                conn.close();
            }
            if(session !=null && session.isConnected()){
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        }
    }

}
