package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.dataBase.LoginManager;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.mainApp.Result;

/*
    обновляет элемент по заданному айди
 */
public class UpdateCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    private LoginManager loginManager;
    public UpdateCommand(ControlUnit cu , HashMapWrapper ha, LoginManager loginManager){
//        cu.addCommand("update", this);
//        hashMapWrapper = ha;
        this.loginManager = loginManager;
    }
    @Override
    public void execute(String options, Result result) {
        if(options == null){
            result.writeResult("Данная команда не работает без аргументов! Запрос не будет выполнен");
            return;
        }
       result.writeResult( hashMapWrapper.updateById(Long.parseLong(options), new StudyGroupFactory(loginManager.getLogin()).createStudyGroup())) ;
    }

    @Override
    public String toString() {
        return "update id : обновить значение элемента коллекции, id которого равен заданному";
    }
}
