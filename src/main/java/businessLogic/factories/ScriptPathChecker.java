package businessLogic.factories;

import java.util.HashSet;
import java.util.Set;
/*
     защищает от рекурсии в скрипте. Добавляет пуи в сет и проверяет, есть ли уже такой путь ( теория графов епт)
 */
public class ScriptPathChecker {
    private static Set<String> paths = new HashSet<>();
    public static void addNewPath(String s){
        paths.add(s);
    }
    public static boolean checkRecursion(String s){
        return paths.contains(s);
    }
    public static void clear(){
        paths.clear();
    }
}
