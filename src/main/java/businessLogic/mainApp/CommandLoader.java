package businessLogic.mainApp;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.commands.*;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.fileWorker.FileManager;

public class CommandLoader {
    public CommandLoader(ControlUnit cu, HashMapWrapper hashMapWrapper, FileManager fl, dataBaseCollection dataBaseCollection){
       Command clear = new ClearCommand(cu, hashMapWrapper);
       Command execute = new ExecuteScriptCommand(cu);
       Command exit = new ExitCommand(cu);
       Command help = new HelpCommand(cu);
       Command history = new HistoryCommand(cu);
       Command info = new InfoCommand(cu, hashMapWrapper);
       Command insert = new InsertCommand(cu, hashMapWrapper, dataBaseCollection);
       Command load = new LoadCommand(cu, fl);
       Command print = new PrintFieldCommand(cu,hashMapWrapper);
       Command removeAll = new RemoveAllByCommand(cu, hashMapWrapper, dataBaseCollection);
       Command remove = new RemoveCommand(cu, hashMapWrapper, dataBaseCollection);
       Command removeLower = new RemoveLowerKeyCommand(cu, hashMapWrapper, dataBaseCollection);
       Command save = new SaveCommand(cu,fl);
       Command scriptAdd = new ScriptAddCommand(cu, hashMapWrapper);
       Command show = new ShowCommand(cu, hashMapWrapper);
       Command sum = new SumCommand(cu,hashMapWrapper);
       Command update = new UpdateCommand(cu, hashMapWrapper);
    }
}
