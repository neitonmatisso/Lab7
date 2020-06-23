package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;
/*
    выводит информацию о админах группы и соритрует по какому-то критерию
 */
public class PrintFieldCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    public PrintFieldCommand(ControlUnit cu, HashMapWrapper hm){
        cu.addCommand("print_admin",this,CommandType.CLEAR);
        hashMapWrapper = hm;
    }

    @Override
    public void execute(String options, Result result) {
        result.writeResult(hashMapWrapper.printAdmins());
    }

    @Override
    public String toString() {
        return "print_admin : вывести значения поля groupAdmin всех элементов в порядке возрастания";
    }
}
