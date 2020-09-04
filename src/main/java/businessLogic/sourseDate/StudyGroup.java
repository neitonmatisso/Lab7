package businessLogic.sourseDate;

import businessLogic.dataBase.tablesEnum;
import businessLogic.factories.Typer;
import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class StudyGroup {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int shouldBeExpelled; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null
    public StudyGroup(){
        creationDate = new Date();
    }
    private String owner; //Обладатель записи

    public final tablesEnum tableEnum = tablesEnum.STGROUP;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate( Date date){
        this.creationDate = date;
    }

    public int getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public void setShouldBeExpelled(int shouldBeExpelled) {
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public Person getGroupAdmin() {

        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public HashMap<String, String> getEverything(){
        HashMap<String, String> params = new HashMap<>();
        params.put("id", Typer.typeRefact(this.id));
        params.put("name", Typer.typeRefact(this.name));
        params.put("coordinates", Typer.typeRefact((this.coordinates.getX() + "," + this.coordinates.getY())));
        params.put("creationDate", Typer.typeRefact(Typer.typeRefact(this.creationDate)));
        params.put("shouldBeExpelled", Typer.typeRefact(this.shouldBeExpelled));
        params.put("formOfEducation", Typer.typeRefact(this.formOfEducation));
        params.put("semesterEnum", Typer.typeRefact(this.semesterEnum));
        if (groupAdmin != null){
            params.put("groupAdmin_id", Typer.typeRefact(this.groupAdmin.getPassportID()));
        } else{
            params.put("groupAdmin_id", Typer.typeRefact(null));
        }
        return params;
    }

    @Override
    public String toString() {
        return "StudyGroup: " +
                "id=" + id +
                ", name=" + name + ",\n" +
                "coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", shouldBeExpelled=" + shouldBeExpelled +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin:" + groupAdmin +
                ", owner=" + owner + "\n" ;

    }
}
