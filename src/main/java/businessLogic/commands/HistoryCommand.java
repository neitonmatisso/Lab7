package businessLogic.commands;

import businessLogic.mainApp.Result;
/*
    выводит информацию о последних выполненных командах
 */
public class HistoryCommand implements Command {
    ControlUnit cu;
    public HistoryCommand(ControlUnit controlUnit){
        cu = controlUnit;
        cu.addCommand("history", this,CommandType.CLEAR);
    }
    @Override
    public void execute(String options, Result result) {
        result.writeResult(cu.checkHistory());
    }

    @Override
    public String toString() {
        return "history : вывести последние 5 команд";
    }
}
