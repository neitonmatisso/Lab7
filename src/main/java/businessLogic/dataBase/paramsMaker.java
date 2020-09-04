package businessLogic.dataBase;

import businessLogic.factories.Typer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class paramsMaker {

    public static String[] stGroupValues = new String[] {"name", "coordinates", "creationDate",
            "shouldBeExpelled","formOfEducation", "semesterEnum", "groupAdmin_id"};

    public static String[] personValues = new String[] {"name", "passportID"};


    public static String makeParams(HashMap<String, String> array, tablesEnum tenum, String owner){
        StringBuilder params = new StringBuilder();
        for (String value : getValue(tenum)) {
            assert getValue(tenum) != null;
            if (array.containsKey(value)) {
                params.append(array.get(value)).append(", ");
            }
        }
        params = new StringBuilder("(" + idParam(tenum) + params + "'" + owner + "')");
        assert getValue(tenum) != null;
        System.out.printf(makeValues(getValue(tenum), tenum) + " values " + params.toString());
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
            case PERSON: return personValues;
            default: return new String[] {};
        }
    }
}
