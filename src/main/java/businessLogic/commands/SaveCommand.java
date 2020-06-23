package businessLogic.commands;

import businessLogic.fileWorker.FileManager;
import businessLogic.mainApp.Result;
/*
    сохраняет коллекцию в файл
 */
public class SaveCommand implements Command {
    FileManager fileManager;
    public SaveCommand(ControlUnit cu , FileManager fileManager){
        cu.addCommand("save",this,CommandType.CLEAR);
        this.fileManager = fileManager;
    }
    @Override
    public void execute(String options, Result result) {
        fileManager.write(result);
    }

    @Override
    public String toString() {
        return "";
    }
}
