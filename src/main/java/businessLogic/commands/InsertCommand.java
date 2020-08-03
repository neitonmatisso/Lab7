package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.dataBaseManager;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.StudyGroup;
import com.google.gson.Gson;
/*
    Добавляет новый элемент в коллекцию с помощью фабрики
 */

public class InsertCommand implements Command {
    private dataBaseManager dbM;
    public InsertCommand(ControlUnit cu, dataBaseManager dbM){
        cu.addCommand("insert", this,CommandType.OBJECT);
        this.dbM = dbM;
    }
    @Override
    public void execute(String options, Result result) {

        String query = "Insert into data base";



        //hashMapWrapper.addElement(new Gson().fromJson(options, StudyGroup.class));
        result.writeResult("Объект успешно добавлен  в коллекцию!");
    }

    @Override
    public String toString() {
        return "insert : добавить новый элемент в коллекцию";
    }
}
