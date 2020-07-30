package itmo;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.*;

public class test3 {

    /**
     * Java Program to connect to the remote database through SSH using port forwarding
     * @author Pankaj@JournalDev
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        int lport=5432;
        String rhost="pg";
        String host="se.ifmo.ru";
        int rport=5432;
        String user="s284201";
        String password="tkl821";
        String dbuserName = "s284201";
        String dbpassword = "tkl821";
        String url = "jdbc:postgresql://localhost:"+lport+"/studs";
        Connection conn = null;
        Session session= null;
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, host, 2222);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            System.out.println("Port Forwarded");

            //mysql database connectivity
            conn = DriverManager.getConnection (url, dbuserName, dbpassword);
            System.out.println ("Database connection established");
            System.out.println("DONE");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total number of books in the table : " + count);
            }
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
