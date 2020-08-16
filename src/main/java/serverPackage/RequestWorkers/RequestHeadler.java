package serverPackage.RequestWorkers;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.commands.*;
import businessLogic.dataBase.LoginManager;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.dataBase.dataBaseManager;
import businessLogic.dataBase.paramsMaker;
import businessLogic.exceptions.NoFileException;
import businessLogic.fileWorker.FileManager;
import businessLogic.mainApp.Result;

import java.util.Queue;

public class RequestHeadler {
    private Queue<String> queryQueue;
    private Queue<String> answerQueue;
    private ControlUnit controlUnit;
    //private HashMapWrapper hashMapWrapper;
   // private FileManager fileManager;
   private dataBaseCollection dataBaseCollection;
    public RequestHeadler(Queue<String> queryQueue, Queue<String> answerQueue, ControlUnit controlUnit, dataBaseCollection dataBaseCollection) {
       // HashMapWrapper hashMapWrapper, FileManager fileManager
        this.queryQueue = queryQueue;
        this.answerQueue = answerQueue;
        this.controlUnit = controlUnit;
       // this.hashMapWrapper = hashMapWrapper;
       // this.fileManager = fileManager;
       this.dataBaseCollection = dataBaseCollection;

    }

    public void completeRequest(String login){
        dataBaseCollection.setOwner(login);
        String command = queryQueue.poll();
        Result result = new Result();
        assert command != null;
        controlUnit.executeCommand(command,result);
        answerQueue.add(result.checkResult());
    }


    public void loadCommands(ControlUnit controlUnit, HashMapWrapper hashMapWrapper, dataBaseCollection dataBaseCollection, LoginManager loginManager){
        Command clearCommand = new ClearCommand(controlUnit,hashMapWrapper, dataBaseCollection);
        Command helpCommand = new HelpCommand(controlUnit);
        Command historyCommand = new HistoryCommand(controlUnit);
        Command infoCommand = new InfoCommand(controlUnit,hashMapWrapper);
        Command insertCommand = new InsertCommand(controlUnit ,hashMapWrapper ,  dataBaseCollection);
        Command printCommand = new PrintFieldCommand(controlUnit,hashMapWrapper);
        Command removeCommand = new RemoveCommand(controlUnit, hashMapWrapper, dataBaseCollection);
        Command showCommand = new ShowCommand(controlUnit,hashMapWrapper);
        Command sumCommand = new SumCommand(controlUnit,hashMapWrapper);
        Command removeLowerCommand = new RemoveLowerKeyCommand(controlUnit,hashMapWrapper, dataBaseCollection);
        //Command saveCommand = new SaveCommand(controlUnit,fileManager);
        Command loginCommand = new LoginCommand(loginManager, controlUnit);
        Command registerCommand = new RegisterCommand(loginManager, controlUnit);

    }
}
