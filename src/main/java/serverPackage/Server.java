package serverPackage;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.commands.ControlUnit;
import businessLogic.commands.LoadCommand;
import businessLogic.exceptions.NoFileException;
import businessLogic.fileWorker.FileManager;
import businessLogic.mainApp.CommandLoader;
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
import businessLogic.dataBase.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

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

    public Server(dataBaseHandler dataBaseHandler) throws NoFileException {

        queryQueue = new ArrayDeque<>();
        answerQueue = new ArrayDeque<>();
        connectionsList = new ArrayList<>();

        controlUnit = new ControlUnit();
        hashMapWrapper = new HashMapWrapper();
        fileManager = new FileManager(hashMapWrapper,"ResourceFile");

        dataBaseManager dataBaseManager = new dataBaseManager(dataBaseHandler);
        LoginManager loginManager = new LoginManager(dataBaseManager);
        dataBaseCollection dataBaseCollection = new dataBaseCollection(dataBaseManager); //need login

        requestReciever = new RequestReciever(queryQueue);
        requestHeadler = new RequestHeadler(queryQueue,answerQueue,controlUnit, dataBaseCollection);
        requestHeadler.loadCommands(controlUnit, hashMapWrapper, dataBaseCollection, loginManager);
        responceSender = new ResponceSender(answerQueue);


        try(ServerSocket serverSocket = new ServerSocket(2121)) {
            CollectionUpdater collectionUpdater = new CollectionUpdater(dataBaseManager, hashMapWrapper);
            System.out.println("Server start...");
            collectionUpdater.updateCollection();
            //new LoadCommand(controlUnit,fileManager).execute("",new Result());
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
        dataBaseHandler dataBaseHandler;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Если вы хотите проложить SSH соеденение - введите SSH");
        String SSH7 = scanner.nextLine();
        if (SSH7.equals("ssh") || SSH7.equals("SSH")){
            dataBaseHandler = new dataBaseHandler("ssh");
        }else{
            dataBaseHandler = new dataBaseHandler("");
        }


        try {
            new Server(dataBaseHandler);
        } catch (NoFileException e) {
            System.out.println("File error! Please create file SaveCollFile");
        }
    }
    @Override
    public void getTransferObject(Connection connection, TransferObject transferObject) {
        Request request = new Gson().fromJson(transferObject.getJsonTransfer(),Request.class);
        Runnable task = () -> {
            System.out.println("Проблема в getTransferObject Server");
        };
        switch (request.getRequestType()){
            case QUERY:
                task = () -> {
                    requestReciever.recievRequest(request);
                    requestHeadler.completeRequest(request.getLogin());
                    responceSender.sendAnswer(connection);
                };
                break;
            case LOGIN:
                task = () -> {
                    requestReciever.recievRequest(request);
                    requestHeadler.completeRequest(request.getLogin());
                    responceSender.sendLogin(connection);
                };
                break;
        }
        Thread requestThread = new Thread(task);
        requestThread.start();
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
