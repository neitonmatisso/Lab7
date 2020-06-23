package businessLogic.commands;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.mainApp.Result;

/*
    обновляет элемент по заданному айди
 */
public class UpdateCommand implements Command {
    private HashMapWrapper hashMapWrapper;
    public UpdateCommand(ControlUnit cu , HashMapWrapper ha){
//        cu.addCommand("update", this);
//        hashMapWrapper = ha;
    }
    @Override
    public void execute(String options, Result result) {
        if(options == null){
            result.writeResult("Данная команда не работает без аргументов! Запрос не будет выполнен");
            return;
        }
       result.writeResult( hashMapWrapper.updateById(Long.parseLong(options), new StudyGroupFactory().createStudyGroup())) ;
    }

    @Override
    public String toString() {
        return "update id : обновить значение элемента коллекции, id которого равен заданному";
    }
}
