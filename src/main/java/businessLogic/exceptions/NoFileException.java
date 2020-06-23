package businessLogic.exceptions;

public class NoFileException extends Exception  {
    public NoFileException(){
        super("Файл не найден");
    }
}
