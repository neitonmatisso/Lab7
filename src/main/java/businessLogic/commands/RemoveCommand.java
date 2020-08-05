package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.mainApp.Result;
/*
    удаляет элемент из коллекции по его ID
 */
public class RemoveCommand implements Command {
    HashMapWrapper hashMapWrapper ;
    dataBaseCollection dataBaseCollection;
    public RemoveCommand(ControlUnit cu, HashMapWrapper hw, dataBaseCollection dataBaseCollection){
        cu.addCommand("remove" , this,CommandType.ARGS);
        this.dataBaseCollection = dataBaseCollection;
        hashMapWrapper = hw;
    }

    @Override
    public void execute(String options, Result result) {

        try {
            result.writeResult(dataBaseCollection.removeElement((Integer.parseInt(options))));
            result.writeResult(hashMapWrapper.removeElement(Long.parseLong(options)));
        } catch (NumberFormatException ec){
            result.writeResult("поступил неверный аргумент. Команда не будет исполнена");
        }

    }

    @Override
    public String toString() {
        return "remove id: удаляет элемент по id в аргументе";
    }
}
