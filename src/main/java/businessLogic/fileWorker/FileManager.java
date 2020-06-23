package businessLogic.fileWorker;

import businessLogic.collectionWorker.CollectionWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import businessLogic.exceptions.NoFileException;
import businessLogic.factories.IdGenerator;
import businessLogic.mainApp.Result;
import businessLogic.sourseDate.StudyGroup;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/*
    класс , который работает с файлом
    умент сохранять и загружать
 */
public class FileManager implements IOInterface {
    private File saveFile;
    private CollectionWrapper colletionWorker;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Gson jsoner;
    private List<String> jsonList;
    private Scanner scanner;
    public FileManager(CollectionWrapper c,String fileAddress) throws NoFileException {
        colletionWorker = c;
        jsonList = new ArrayList<>();
        jsoner = new Gson();
        saveFile = new File(fileAddress);

    }
    public void write(Result resultShell){
        try {
            if(colletionWorker.isEmpty()){
                resultShell.writeResult("Коллекция пуста. запись не будет произведена");
                return;
            }
            bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
            System.out.println("Запись пошла!");
            System.out.println("Файл: " + saveFile.getAbsolutePath());
            Iterator<Long> it = colletionWorker.getIterator();
            while (it.hasNext()){
                bufferedWriter.write(jsoner.toJson(colletionWorker.getStudyGroup(it.next() ) )+ "\n");
            }
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File("IdFile")));
            Long buf = IdGenerator.getCurrentId();
            bf.write(buf.toString());
            bf.close();
            bufferedWriter.close();
            resultShell.writeResult("Записьт в файло была успешно произведена");

        } catch (FileNotFoundException ex){
            resultShell.writeResult("Файл не найден!");
        }
        catch (IOException e){
            System.out.println("Произошла непредвиденная ошибка! Запись в файла не была произведена");
        }
    }
    public void read(Result resultShell) {
        try {
            scanner = new Scanner(saveFile);
            while (scanner.hasNext()) {
                colletionWorker.addElement(jsoner.fromJson(scanner.nextLine(), StudyGroup.class));
            }
            resultShell.writeResult("Данные загружены успешно!");
            scanner.close();
        } catch (IOException e ){
            resultShell.writeResult("чтение из файла было прервано!");
        } catch (JsonSyntaxException e){
            resultShell.writeResult("Файл поврежден");
        }

    }
}
