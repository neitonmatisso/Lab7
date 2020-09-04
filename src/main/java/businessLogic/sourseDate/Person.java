package businessLogic.sourseDate;

import businessLogic.dataBase.tablesEnum;
import businessLogic.factories.Typer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Person  {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Значение этого поля должно быть уникальным, Строка не может быть пустой, Поле не может быть null

    public tablesEnum tableEnum = businessLogic.dataBase.tablesEnum.PERSON;

    public Person(String name, String passportID) {
        this.name = name;
        this.passportID = passportID;
    }

    public String getPassportID(){
        return passportID;
    }
    @Override
    public String toString() {
        return  " name= " + name +
                ", passportID=" + passportID ;
    }

    public String getName(){
        return this.name;
    }

    public HashMap<String, String> getEverything(){
        HashMap<String, String> params = new HashMap<>();
        //params.add(new Pair<>("", Typer.typeRefact()));
        params.put("name", Typer.typeRefact(this.name));
        params.put("passportID", Typer.typeRefact(this.passportID));

        return params;
    }



}
