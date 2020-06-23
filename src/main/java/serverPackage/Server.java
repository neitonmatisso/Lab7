package serverPackage;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.commands.ControlUnit;
import businessLogic.commands.LoadCommand;
import businessLogic.exceptions.NoFileException;
import businessLogic.fileWorker.FileManager;
import businessLogic.mainApp.Result;
import com.google.gson.Gson;
import connectionPackage.Connection;
import connectionPackage.ConnectionListener;
import connectionPackage.connectionData.Request;
import connectionPackage.connectionData.Responce;
import connectionPackage.connectionData.ResponseType;
import connectionPackage.connectionData.TransferObject;
import serverPackage.RequestWorkers.RequestHeadler;
import serverPackage.RequestWorkers.RequestReciever;
import serverPackage.RequestWorkers.ResponceSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Server implements ConnectionListener {
    private List<Connection> connectionsList;
    private Queue<String> queryQueue;
    private Queue<String> answerQueue;

    private RequestReciever requestReciever;
    private RequestHeadler requestHeadler;
    private ResponceSender responceSender;

    private ControlUnit controlUnit;
    private HashMapWrapper hashMapWrapper;
    private FileManager fileManager;

    public Server() throws NoFileException {


        queryQueue = new ArrayDeque<>();
        answerQueue = new ArrayDeque<>();
        connectionsList = new ArrayList<>();

        controlUnit = new ControlUnit();
        hashMapWrapper = new HashMapWrapper();
        fileManager = new FileManager(hashMapWrapper,"ResourceFile");

        requestReciever = new RequestReciever(queryQueue);
        requestHeadler = new RequestHeadler(queryQueue,answerQueue,controlUnit,hashMapWrapper,fileManager);
        responceSender = new ResponceSender(answerQueue);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                controlUnit.executeCommand("save",new Result());
            }
        });

        try(ServerSocket serverSocket = new ServerSocket(2121)) {
            System.out.println("Server start...");
            new LoadCommand(controlUnit,fileManager).execute("",new Result());
            while (true){
                Connection connection = new Connection(serverSocket.accept(),this);
                connectionsList.add(connection);
                System.out.println("New connection");
            }

        } catch (IOException ex){
            System.out.println("oops... something wrong with server");
        }
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (NoFileException e) {
            System.out.println("File error! Please create file SaveCollFile");
        }
    }
    @Override
    public void getTransferObject(Connection connection, TransferObject transferObject) {
        Request request = new Gson().fromJson(transferObject.getJsonTransfer(),Request.class);
        switch (request.getRequestType()){
            case QUERY:
                requestReciever.recievRequest(request);
                requestHeadler.completeRequest();
                responceSender.sendAnswer(connection);
                break;
            case LOGIN:
                //для авторизации в 7 лабе
                break;
            case REGISTRATION:
                //для регистрации в 7 лабе
                break;
        }
    }

    @Override
    public void connectionReady(Connection connection) {
        try {
            String jsonMap = new Gson().toJson(controlUnit.getCommands());
            Responce responce = new Responce(ResponseType.SETTINGS, jsonMap);
            connection.sendTransferObject(new TransferObject(new Gson().toJson(responce)));
        } catch (IOException exception) {
            System.out.println("Cannot send command map");
        }

    }

    @Override
    public void disconnect(Connection connection) {
        System.out.println("Отключился клиент , а вот какой... секрет");
        connectionsList.remove(connection);
    }

}
