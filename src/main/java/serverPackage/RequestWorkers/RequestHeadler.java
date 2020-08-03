package serverPackage.RequestWorkers;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.commands.*;
import businessLogic.exceptions.NoFileException;
import businessLogic.fileWorker.FileManager;
import businessLogic.mainApp.Result;

import java.util.Queue;

public class RequestHeadler {
    private Queue<String> queryQueue;
    private Queue<String> answerQueue;
    private ControlUnit controlUnit;
    private HashMapWrapper hashMapWrapper;
    private FileManager fileManager;

    public RequestHeadler(Queue<String> queryQueue, Queue<String> answerQueue, ControlUnit controlUnit, HashMapWrapper hashMapWrapper
                                                                , FileManager fileManager) throws NoFileException {
        this.queryQueue = queryQueue;
        this.answerQueue = answerQueue;
        this.controlUnit = controlUnit;
        this.hashMapWrapper = hashMapWrapper;
        this.fileManager = fileManager;
        loadCommands(controlUnit,hashMapWrapper,fileManager);
    }

    public void completeRequest(){
        String command = queryQueue.poll();
        Result result = new Result();
        controlUnit.executeCommand(command,result);
        answerQueue.add(result.checkResult());
    }


    private void loadCommands(ControlUnit controlUnit,HashMapWrapper hashMapWrapper, FileManager fileManager){
        Command clearCommand = new ClearCommand(controlUnit,hashMapWrapper);
        Command helpCommand = new HelpCommand(controlUnit);
        Command historyCommand = new HistoryCommand(controlUnit);
        Command infoCommand = new InfoCommand(controlUnit,hashMapWrapper);
        //Command insertCommand = new InsertCommand(controlUnit,hashMapWrapper);
        Command printCommand = new PrintFieldCommand(controlUnit,hashMapWrapper);
        Command removeCommand = new RemoveCommand(controlUnit,hashMapWrapper);
        Command showCommand = new ShowCommand(controlUnit,hashMapWrapper);
        Command sumCommand = new SumCommand(controlUnit,hashMapWrapper);
        Command removeLowerCommand = new RemoveLowerKeyCommand(controlUnit,hashMapWrapper);
        Command saveCommand = new SaveCommand(controlUnit,fileManager);

    }
}
