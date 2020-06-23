package businessLogic.commands;

import businessLogic.exceptions.RecursionOnScriptException;
import businessLogic.factories.ScriptPathChecker;
import businessLogic.mainApp.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/*
    Комманда загрузки скрипта
 */
public class ExecuteScriptCommand implements Command {
    private ControlUnit controlUnit;
    private Scanner sc;
    private File file;
    private Set<String> objectCreateCommandSet;
    public ExecuteScriptCommand(ControlUnit cu){
        controlUnit = cu;
        cu.addCommand("execute_script", this,CommandType.ARGS);
        objectCreateCommandSet = new HashSet<>();
        objectCreateCommandSet.add("insert");
        objectCreateCommandSet.add("update");
    }
    @Override
    public void execute(String options, Result result) {
        int currLine = 0; // подсчет строк
        ScriptPathChecker.addNewPath(options);
        file = new File(options); // получсем путь к скрипту
        List<String> check = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()){
                ++currLine;
                String newxtLine = sc.nextLine();
                if(newxtLine.isEmpty()){
                    continue;
                }
                check = Arrays.asList(newxtLine.split(" "));

                if(objectCreateCommandSet.contains(check.get(0))){
                    String buffer = "";
                    for (int i = 0; i < 8; i++) {
                        String currentCommand =  sc.nextLine().trim();
                        if(currentCommand.equals("")){
                            buffer+= "empty"+ "-";
                            continue;
                        }
                        buffer += currentCommand.trim() + "-";
                    }
                    controlUnit.executeCommand("script_add", buffer, result);
                    continue;
                }

                if(check.get(0).equals("execute_script")){
                    String pathBuff =check.get(1);
                    if(ScriptPathChecker.checkRecursion(pathBuff)){
                        System.out.println("обнаруженв рекурсия! срипт будет прерван");
                        throw new RecursionOnScriptException();
                    }
                    ScriptPathChecker.addNewPath(pathBuff);
                    controlUnit.executeCommand(check.get(0), check.get(1),result);
                    continue;
                }
                if(check.size() == 1){
                    controlUnit.executeCommand(check.get(0),null,result);
                    continue;
                }
                controlUnit.executeCommand(check.get(0), check.get(1),result);
            }

            sc.close();
            ScriptPathChecker.clear();

        } catch (FileNotFoundException ex){
            System.out.println("Файл не найден");
        } catch (RecursionOnScriptException ex2){
            System.out.println("Скрипт прерван рекурсий");
        }
        catch (NullPointerException ex3){
        System.out.println("В аргументах комманды нет названия файла");
        }
        catch (Exception e){
            System.out.println("Ошибка в строке: "+currLine );
            System.out.println("Команда, в которой произшла ошибка " + check.toString() );
        }
    }

    @Override
    public String toString() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла.";
    }
}

