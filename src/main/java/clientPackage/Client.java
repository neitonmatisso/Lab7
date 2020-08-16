package clientPackage;

import businessLogic.commands.CommandType;
import businessLogic.dataBase.LoginManager;
import com.google.gson.Gson;
import connectionPackage.Connection;
import connectionPackage.ConnectionListener;
import connectionPackage.connectionData.Request;
import connectionPackage.connectionData.RequestType;
import connectionPackage.connectionData.Responce;
import connectionPackage.connectionData.TransferObject;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Client implements ConnectionListener {
    private Connection connection;
    private HashMap commandMap;
    private Queue<String> answersQueue;
    private ServerStatus serverStatus;
    private String login = "###\n";

    public String getLogin() {
        return login;
    }

    public Client(){
        commandMap = new HashMap<>();
        answersQueue = new ArrayDeque<>();
        serverStatus = ServerStatus.CLOSE;
    }

    public HashMap<String,CommandType > getSettings(){
        return commandMap;
    }

    public String getNextAnswer(){
        return answersQueue.poll();
    }

    public ServerStatus getServerStatus(){
        return serverStatus;
    }

    public void createQuery(String commandName, String args){
        Request request = new Request(RequestType.QUERY,commandName,args);
        request.setLogin(login);
        try {
            connection.sendTransferObject(new TransferObject(new Gson().toJson(request)));
        } catch (IOException ex ){
            System.out.println("Cannot send request!");
        }
    }
    public void createLogin(String args){
        Request request = new Request(RequestType.LOGIN,"login",args);
        try {
            connection.sendTransferObject(new TransferObject(new Gson().toJson(request)));
        } catch (IOException ex ){
            System.out.println("Cannot send request!");
        }
    }



    public boolean connectToServer(String IP, int port){
        try{
            connection = new Connection(this,IP,port);
            return true;
        }catch (IOException ex){
            System.out.println("Хоспаде, Максон, запомни localhost 2121");
            return false;
        }
    }


    @Override
    public void getTransferObject(Connection connection, TransferObject transferObject) {
        Responce response = new Gson().fromJson(transferObject.getJsonTransfer(),Responce.class);
        switch (response.getResponseType()){
            case ANSWER:
                answersQueue.add(response.getResponseOption());
                System.out.println(response.getResponseOption());
                break;
            case LOGIN:
                login = response.getResponseOption().split(" ")[0];
                if (!(login.equals("###\n"))){
                    System.out.println("Ура, вы вошли!");
                }else{
                    System.out.println("Вы не смогли войти");
                }
                break;

            case SETTINGS:
                Responce jsonMap = new Gson().fromJson(transferObject.getJsonTransfer(), Responce.class);
                commandMap = new Gson().fromJson(jsonMap.getResponseOption(),HashMap.class);
                System.out.println("Got settings");
                break;
            case BAD_REQUEST:
                System.out.println("something wrong...");
                //breakpoint, можно для какой-нибудь ошибки обработать
                break;
        }

    }

    @Override
    public void connectionReady(Connection connection) {
        serverStatus = ServerStatus.READY;
        System.out.println("Connection ready");
    }

    @Override
    public void disconnect(Connection connection) {
        serverStatus = ServerStatus.CLOSE;
        System.out.println("Connection close");
        this.login = "";

    }

}
