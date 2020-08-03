package businessLogic.sourseDate;

public class Person  {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Значение этого поля должно быть уникальным, Строка не может быть пустой, Поле не может быть null

    public Person(String name, String passportID) {
        this.name = name;
        this.passportID = passportID;
    }

    public String getPassportID(){
        return passportID;
    }
    @Override
    public String toString() {
        return  " name= " + name + '\'' +
                ", passportID='" + passportID ;
    }

    public String getName(){
        return this.name;
    }



}
