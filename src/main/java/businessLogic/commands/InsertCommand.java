package businessLogic.commands;

import businessLogic.collectionWorker.CollectionWrapper;
import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.dataBaseCollection;
import businessLogic.dataBase.dataBaseManager;
import businessLogic.dataBase.paramsMaker;
import businessLogic.dataBase.tablesEnum;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.StudyGroup;
import com.google.gson.Gson;
/*
    Добавляет новый элемент в коллекцию с помощью фабрики
 */

public class InsertCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    private dataBaseCollection dataBaseCollection;
    public InsertCommand(ControlUnit cu, HashMapWrapper hashMapWrapper, dataBaseCollection dataBaseCollection){
        cu.addCommand("insert", this,CommandType.OBJECT);
        this.hashMapWrapper = hashMapWrapper;
        this.dataBaseCollection = dataBaseCollection;
    }
    @Override
    public void execute(String options, Result result) {
        StudyGroup studyGroup = new Gson().fromJson(options, StudyGroup.class);
        result.writeResult(dataBaseCollection.insertStGroupAndPerson(studyGroup));
        dataBaseCollection.update();
    }

    @Override
    public String toString() {
        return "insert : добавить новый элемент в коллекцию";
    }
}
