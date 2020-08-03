package businessLogic.dataBase;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class paramsMaker {

    public static String makeParams(String[] params){
        StringBuilder string = new StringBuilder();
        for (String elem : params){
            string.append(elem);
        }
        return string.toString();
    }

    public static String makeParams(ArrayList<Pair<String, String>> array, String[] values){
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < array.size(); i++){
            if (Arrays.asList(values).contains(array.get(i).getKey())){
                if (i == (array.size()-1)){
                    params.append(array.get(i).getValue());
                }
                params.append(array.get(i).getValue()).append(", ");
            }
        }
        params = new StringBuilder("(" + params + ")");
        return params.toString();
    }

    public static String makeValues(String[] values){
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < values.length; i++){
            if (i == values.length-1){
                string.append(values[i]);
            }
            string.append(values[i]).append(", ");
        }
        string = new StringBuilder("(" + string + ")");
        return string.toString();
    }
}
