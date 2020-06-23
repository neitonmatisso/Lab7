package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.StudyGroup;
import com.google.gson.Gson;
/*
    Добавляет новый элемент в коллекцию с помощью фабрики
 */

public class InsertCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    public InsertCommand(ControlUnit cu, HashMapWrapper hmw){
        cu.addCommand("insert", this,CommandType.OBJECT);
        hashMapWrapper = hmw;
    }
    @Override
    public void execute(String options, Result result) {

        hashMapWrapper.addElement(new Gson().fromJson(options, StudyGroup.class));
        result.writeResult("Объект успешно добавлен  в коллекцию!");
    }

    @Override
    public String toString() {
        return "insert : добавить новый элемент в коллекцию";
    }
}
