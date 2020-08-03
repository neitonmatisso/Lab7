package businessLogic.dataBase;

import javafx.util.Pair;

import java.util.ArrayList;

public class paramsMaker {

    public static String makeParams(String[] params){
        StringBuilder string = new StringBuilder();
        for (String elem : params){
            string.append(elem);
        }
        return string.toString();
    }

    public static String makeValues(ArrayList<Pair<String, String>> params){
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < params.size(); i++){
            if (i == (params.size()-1)){
                values.append(params.get(i).getKey());
            }
            values.append(params.get(i).getKey()).append(", ");

        }
        values = new StringBuilder("(" + values + ")");
        return values.toString();
    }

    public static String makeParams(ArrayList<Pair<String, String>> params){
        return makeValues(params);
    }
}
