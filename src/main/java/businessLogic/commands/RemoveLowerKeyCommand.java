package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;
/*
    удаляет все элементы из коллекции , коюч которых ниже заданного
 */
public class RemoveLowerKeyCommand implements Command {
    private HashMapWrapper hashMapWrapper ;
    public RemoveLowerKeyCommand(ControlUnit cu, HashMapWrapper hashMapWrapper){
        cu.addCommand("remove_lover", this,CommandType.ARGS);
        this.hashMapWrapper = hashMapWrapper;
    }
    @Override
    public void execute(String options, Result result) {
        try {
            result.writeResult(hashMapWrapper.removeLowerKey(Long.parseLong(options)));
        } catch (NumberFormatException ex){
            result.writeResult("поступил неверный формат. Запрос не будет исполнен");
        }
    }

    @Override
    public String toString() {
        return "remove_lover lowerKey: удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }
}
