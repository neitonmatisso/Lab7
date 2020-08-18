package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.mainApp.Result;
/*
    удаляет что-то из коллекции по определенному критерию
 */
public class RemoveAllByCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    private dataBaseCollection dataBaseCollection;
    public RemoveAllByCommand(ControlUnit cu, HashMapWrapper hashMapWrapper, dataBaseCollection dataBaseCollection){
        cu.addCommand("remove_by_sbe", this,CommandType.ARGS);
        this.hashMapWrapper = hashMapWrapper;
        this.dataBaseCollection = dataBaseCollection;
    }
    @Override
    public void execute(String options, Result result) {
        try {
            result.writeResult(dataBaseCollection.removeBySBE(Integer.parseInt(options)));
            dataBaseCollection.update();
        } catch (NumberFormatException ec){
            result.writeResult("поступил неверный аргумент. Команда не будет исполнена");
        }
    }

    @Override
    public String toString() {
        return "remove_by_sbe shouldBeExpelled: удалить из коллекции все элементы," + System.lineSeparator() +
                " значение поля shouldBeExpelled которого эквивалентно заданному";
    }
}
