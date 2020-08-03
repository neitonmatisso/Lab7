package businessLogic.factories;

import java.util.Date;

public class Typer {

    public static <Т> String typeRefact (Т param){

        if (param == null){
            return "null";
        }


        String type_things = param.getClass().getTypeName();

        String[] things = type_things.split("\\.");
        String type = things[things.length - 1];

        //switch (type){
            //case "String":
        //}
        if (type.equals("String")){
            return "'" + param + "'";
        }

        if (type.equals("Date")){
            String[] strings = param.toString().split(" ");
            return (strings[2] + "-" + strings[1] + "-" + strings[5]);
        }


        return param + "";

    }

}
