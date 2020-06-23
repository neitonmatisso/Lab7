package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;
/*
    выводит все элементы коллекции
 */
public class ShowCommand implements Command {
    public HashMapWrapper hashMapWrapper;
    public ShowCommand(ControlUnit controlUnit, HashMapWrapper hashMapWrapper){
        controlUnit.addCommand("show", this,CommandType.CLEAR);
        this.hashMapWrapper = hashMapWrapper;
    }
    @Override
    public void execute(String options, Result result) {
        result.writeResult(hashMapWrapper.show());
    }

    @Override
    public String toString() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
