package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.mainApp.Result;

public class SumCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    public SumCommand(ControlUnit cu, HashMapWrapper hw){
        cu.addCommand("sum_sbe", this,CommandType.CLEAR);
        hashMapWrapper = hw;
    }
    @Override
    public void execute(String options, Result result) {
        result.writeResult("Сумма: " +hashMapWrapper.sumOfElement());
    }

    @Override
    public String toString() {
        return "sum_sbe shouldBeExpelled: вывести сумму значений поля shouldBeExpelled для всех элементов коллекции";
    }
}
