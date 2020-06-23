package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;
/*
    команда для очистики коллекции
 */
public class ClearCommand implements Command {
    private HashMapWrapper mainColl;
    public ClearCommand(ControlUnit cu, HashMapWrapper hashMapWrapper){
        cu.addCommand("clear", this,CommandType.CLEAR);
        mainColl = hashMapWrapper;
    }
    @Override
    public void execute(String options, Result result) {
        mainColl.clear();
        result.writeResult("Коллекция очищена!");

    }

    @Override
    public String toString() {
        return "clear - очистить коллекцию";
    }
}
