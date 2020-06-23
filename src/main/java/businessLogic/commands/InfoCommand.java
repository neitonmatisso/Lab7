package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;
/*
    команда, которая выводит информацию о коллекции
 */
public class InfoCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    public InfoCommand(ControlUnit controlUnit, HashMapWrapper hm){
        controlUnit.addCommand("info", this,CommandType.CLEAR);
        hashMapWrapper = hm;
    }
    @Override
    public void execute(String options, Result result) {
        result.writeResult(hashMapWrapper.info());
    }

    @Override
    public String toString() {
        return "info : вывести в стандартный поток вывода информацию о коллекции";
    }
}
