package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.mainApp.Result;
/*
    удаляет все элементы из коллекции , коюч которых ниже заданного
 */
public class RemoveLowerKeyCommand implements Command {
    private HashMapWrapper hashMapWrapper ;
    private dataBaseCollection dataBaseCollection;
    public RemoveLowerKeyCommand(ControlUnit cu, HashMapWrapper hashMapWrapper, dataBaseCollection dataBaseCollection){
        cu.addCommand("remove_lover", this,CommandType.ARGS);
        this.hashMapWrapper = hashMapWrapper;
        this.dataBaseCollection = dataBaseCollection;
    }
    @Override
    public void execute(String options, Result result) {
        try {
            result.writeResult(dataBaseCollection.removeLowerKey(Long.parseLong(options)));
            dataBaseCollection.update();
        } catch (NumberFormatException ex){
            result.writeResult("поступил неверный формат. Запрос не будет исполнен");
        }
    }

    @Override
    public String toString() {
        return "remove_lover lowerKey: удалить из коллекции все элементы, id которых меньше, чем заданный";
    }
}
