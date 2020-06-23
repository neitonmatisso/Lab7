package businessLogic.commands;

import businessLogic.mainApp.Result;
/*
    выводит скудную инфомарцию о командах...
 */
public class HelpCommand implements Command {
    ControlUnit cu;

    public HelpCommand(ControlUnit cu){
        cu.addCommand("help", this,CommandType.CLEAR);
        this.cu = cu;
    }
    @Override
    public void execute(String options, Result result) {
        String help = "";
        for (Object command : cu.commandList){
            help += command.toString() + "\n";
        }
        result.writeResult(help);

    }
    @Override
    public String toString() {
        return "help : вывести справку по доступным командам";
    }
}
