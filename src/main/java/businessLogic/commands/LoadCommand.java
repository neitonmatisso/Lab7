package businessLogic.commands;

import businessLogic.fileWorker.FileManager;
import businessLogic.mainApp.Result;
/*
    вызывает метод для сохранения всей коллекции в сериализованном виде в файл
 */
public class LoadCommand implements Command {
    FileManager fileManager;
    public LoadCommand(ControlUnit cu , FileManager fileManager){
        cu.addCommand("load",this,CommandType.CLEAR);
        this.fileManager = fileManager;
    }
    @Override
    public void execute(String options, Result result) {
        fileManager.read(result);
    }

    @Override
    public String toString() {
        return "load : загрузка коллекции из файла";
    }
}
