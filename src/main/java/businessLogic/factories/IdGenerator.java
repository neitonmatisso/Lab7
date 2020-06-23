package businessLogic.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
    айдигенератор имеет отдельный файл где хранит значение текущего айди
    при загрузки команды айдигенератор загужает последний использованный айди
 */
public class IdGenerator {
    private static File file;
    private static long id;
    private Scanner sc;
    public IdGenerator () {
        file = new File("IdFile");
        try {
            sc = new Scanner(file);
            if(sc.hasNextLong()) {
                id = sc.nextLong();
            } else {
                id = 0;
            }
        } catch (FileNotFoundException e){
            System.out.println("Файл не найден");
        } catch (NumberFormatException er){
            System.out.println("В файле произошла внутрянняя ошибкачё");
        }

    }
    public static long getNewId() {
        return ++id;
    }
    public static long getCurrentId(){
        return id;
    }
    public static String getFilePath(){
        return file.getAbsolutePath();
    }
}
