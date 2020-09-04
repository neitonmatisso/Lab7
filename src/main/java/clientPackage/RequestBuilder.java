package clientPackage;

import businessLogic.commands.CommandType;
import businessLogic.factories.StudyGroupFactory;
import businessLogic.sourseDate.StudyGroup;
import clientPackage.excpetions.InvalidCommandException;
import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {
    private String commandName = "";
    private String commandArgs = "";
    private HashMap<String, CommandType> commandMap;
    public RequestBuilder(HashMap<String,CommandType> commandMap) {
        this.commandMap = commandMap;
    }

    public AbstractMap.SimpleEntry<String, String> completeQuery(String name, String args, String login) throws InvalidCommandException {
        if(!commandMap.containsKey(name)){
            System.out.println("Invalid command name");
            throw new InvalidCommandException();
        }

        String commandType = String.valueOf(commandMap.get(name));

        switch (commandType){
            case "ARGS":
                if(args == null){
                    System.out.println("Данная команда  содержит аргументы");
                    throw new InvalidCommandException();
                }
                return new AbstractMap.SimpleEntry<String, String>(name,args);
            case "CLEAR":
                if(!(args == null)  ){
                    System.out.println("Данная команда не содержит аргументов");
                    throw new InvalidCommandException();
                }
                return new AbstractMap.SimpleEntry<String, String>(name,args);
            case "OBJECT":
                StudyGroup studyGroup = new StudyGroupFactory(login).createStudyGroup();
                return new AbstractMap.SimpleEntry<String, String>(name, new Gson().toJson(studyGroup));
            default:
                throw new InvalidCommandException();
        }


    }

}
