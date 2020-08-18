package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.mainApp.Result;
/*
    команда для очистики коллекции
 */
public class ClearCommand implements Command {
    private HashMapWrapper mainColl;
    private dataBaseCollection dataBaseCollection;
    public ClearCommand(ControlUnit cu, HashMapWrapper hashMapWrapper, dataBaseCollection dataBaseCollection){
        cu.addCommand("clear", this,CommandType.CLEAR);
        mainColl = hashMapWrapper;
        this.dataBaseCollection = dataBaseCollection;
    }
    @Override
    public void execute(String options, Result result) {
        dataBaseCollection.clear();
        dataBaseCollection.update();
        result.writeResult("Коллекция очищена!");

    }

    @Override
    public String toString() {
        return "clear - очистить коллекцию";
    }
}
