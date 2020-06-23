package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;
/*
    удаляет что-то из коллекции по определенному критерию
 */
public class RemoveAllByCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    public RemoveAllByCommand(ControlUnit cu, HashMapWrapper hashMapWrapper){
        cu.addCommand("remove_by_sbe", this,CommandType.ARGS);
        this.hashMapWrapper = hashMapWrapper;
    }
    @Override
    public void execute(String options, Result result) {
        try {
            result.writeResult(hashMapWrapper.removeBySBE(Integer.parseInt(options)));
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
