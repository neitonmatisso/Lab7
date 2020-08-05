package businessLogic.dataBase;

import businessLogic.factories.Typer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class paramsMaker {

    private String owner;

    public paramsMaker(String owner){
        this.owner = owner;
    }

    public static String[] stGroupValues = new String[] {"name", "coordinates", "creationDate",
            "shouldBeExpelled","formOfEducation", "semesterEnum", "groupAdmin_id"};


    public String makeParams(ArrayList<Pair<String, String>> array, tablesEnum tenum){
        StringBuilder params = new StringBuilder();
        for (Pair<String, String> stringPair : array) {
            assert getValue(tenum) != null;
            if (Arrays.asList(getValue(tenum)).contains(stringPair.getKey())) {
                params.append(stringPair.getValue()).append(", ");
            }
        }
        params = new StringBuilder("(" + idParam(tenum) + params + "'" + owner + "')");
        assert getValue(tenum) != null;
        return makeValues(getValue(tenum), tenum) + " values " + params.toString();
    }

    public static String makeValues(String[] values, tablesEnum tenum){
        StringBuilder string = new StringBuilder();
        for (String value : values) {
            string.append(value).append(", ");
        }
        string = new StringBuilder("(" + idValue(tenum) + string + "owner)");
        return string.toString();
    }

    private static String idParam(tablesEnum tenum){ //for Unique fields
        switch (tenum){
            case STGROUP: return "nextval('id_stgroup_serial'), ";
            default:  return "";
        }
    }

    private static String idValue(tablesEnum tenum){
        switch (tenum){
            case STGROUP: return "id, ";
            default: return "";
        }
    }

    private static String[] getValue(tablesEnum tenum){
        switch (tenum){
            case STGROUP: return stGroupValues;
        }
        return null;

    }
}
