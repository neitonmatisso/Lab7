package businessLogic.commands;

import businessLogic.mainApp.Result;
/*
    насильно завершает работу программы
 */
public class ExitCommand implements Command{
    public ExitCommand(ControlUnit cu){
        cu.addCommand("exit", this,CommandType.CLEAR);
    }

    @Override
    public void execute(String options, Result result) {
        if(options != null){
            result.writeResult("Данная комада не содержит аргументов! Запрос не будет выполнен");
            return;
        }
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
