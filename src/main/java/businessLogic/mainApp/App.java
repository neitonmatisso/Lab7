package businessLogic.mainApp;

import businessLogic.collectionWorker.HashMapWrapper;
import businessLogic.commands.*;
import businessLogic.exceptions.NoFileException;
import businessLogic.factories.IdGenerator;
import businessLogic.fileWorker.FileManager;

import java.util.Scanner;

/*

 */
public class App {

    public static void main(String[] args) throws NoFileException {
        ControlUnit cu = new ControlUnit();
        IdGenerator idGenerator = new IdGenerator();
        HashMapWrapper hashMapWrapper = new HashMapWrapper();
        FileManager fl = new FileManager(hashMapWrapper, "SaveFile");
        //FileManager fl = new FileManager(hashMapWrapper, System.getenv("FilePath")); //TODO УБРАТЬ
        CommandLoader commandLoader = new CommandLoader(cu,hashMapWrapper,fl);
        Result result = new Result();
        System.out.println("Добро пожаловать!  Введите команду");
        cu.executeCommand("load", result);
        System.out.println(result.checkResult());
        while (true) {
            System.out.print("> ");
            result.clear();
            String request = new Scanner(System.in).nextLine();
            if(request.equals("")){
                continue;
            }
            cu.executeCommand(request, result);
            System.out.println(result.checkResult());
        }


    }
}
