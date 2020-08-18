package businessLogic.factories;

import businessLogic.exceptions.CreateObjectException;
import businessLogic.sourseDate.*;

import java.util.*;
/*
    Фабрика
 */
public class StudyGroupFactory {
    private Scanner scanner;
    private String owner;
    public StudyGroupFactory(String owner){
        scanner = new Scanner(System.in);
        this.owner = owner;
    }
    public void updateScanner(){
        scanner = new Scanner(System.in);
    }

    public StudyGroup createStudyGroupWithParams (String params){
        List<String> para = Arrays.asList(params.split("-"));
        para.removeIf(x -> x.equals(" "));
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setOwner(owner);
        studyGroup.setName(para.get(0));
        studyGroup.setShouldBeExpelled(Integer.parseInt(para.get(1)));
        studyGroup.setCoordinates(new Coordinates(Double.parseDouble(para.get(2)), Integer.parseInt(para.get(3))));
        if (para.get(4).equals("empty")){
            studyGroup.setFormOfEducation(null);
        } else {
            studyGroup.setFormOfEducation(FormOfEducation.valueOf(para.get(4)));
        }
        if (para.get(5).equals("empty")) {
            studyGroup.setGroupAdmin(null);
        } else {
        studyGroup.setGroupAdmin(new Person(para.get(5),para.get(6)));
        }
        if (para.get(7).equals("empty")){
            studyGroup.setSemesterEnum(null);
        } else {
            studyGroup.setSemesterEnum(Semester.valueOf(para.get(7)));
        }
        studyGroup.setId(IdGenerator.getNewId());
        return studyGroup;
    }
    public StudyGroup createStudyGroup(){
        StudyGroup st = new StudyGroup();
        st.setOwner(owner);
        st.setName(createName());
        st.setShouldBeExpelled(shouldBeExpelled());
        st.setCoordinates(createCoordinates());
        st.setFormOfEducation(createForm());
        st.setGroupAdmin(createPerson());
        st.setSemesterEnum(createSemester());
        st.setId(IdGenerator.getNewId());
        return st;
    }
    public int shouldBeExpelled(){
        updateScanner();
        System.out.println("Введите колличество отчисляемых: ");
        int should;
        while (true){
            try {
                should = scanner.nextInt();
                if(should < 0){
                    throw new CreateObjectException();
                }
                break;
            } catch (CreateObjectException | InputMismatchException exception){
                System.out.println("введен невеный формат , попробуйте снова");
                updateScanner();
            }

        }
        return should;

    }
    public String createName(){
        updateScanner();
        System.out.println("Введите имя группы: ");
        String name = "";
        while (true) {
            try {
                name = scanner.nextLine();
                if (name.equals("")) {
                    throw new CreateObjectException();
                }
                break;
            } catch (CreateObjectException ex){
                System.out.println("имя не может быть пустым. Попробуйте снова");
            }
        }
        return name;

    }
    public Coordinates createCoordinates(){
        updateScanner();
        double x ;
        int y ;
        System.out.println("введите координаты: ");
        while (true) {
            try {
                String stringX = scanner.nextLine();
                String stringY = scanner.nextLine();
                x = Double.parseDouble(stringX);
                y = Integer.parseInt(stringY);
                if (x < -81 || stringX.equals("") || stringY.equals("")) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("введеное вами значение не соответсвует типу координат. Попробуйте снова");

            }
        }
        return new Coordinates(x,y);
    }
    public Person createPerson(){
        updateScanner();
        System.out.println("Введите данные для Person ");
        String name;
        String ID;
        while (true) {
            try {
                System.out.println("Введите имя");
                name = scanner.nextLine();
                if (name.equals("")){
                    return null;
                }
                System.out.println("Введите ID");
                ID = scanner.nextLine();
                if (ID.equals("")) {
                    throw new CreateObjectException();
                }
                break;
            } catch (CreateObjectException ex){
                System.out.println("Данное поле не может быть пустым!");
                name = "";
                ID= "";
            }
        }
        return new Person(name, ID);
    }
    public FormOfEducation createForm(){
        System.out.println("Выберите 1 из предложенных вариантов: \n DISTANCE_EDUCATION,\n" +
                "FULL_TIME_EDUCATION,\n" +
                "EVENING_CLASSES;");
        String s = scanner.nextLine();
        switch (s){
            case "FULL_TIME_EDUCATION": return FormOfEducation.FULL_TIME_EDUCATION;
            case "DISTANCE_EDUCATION": return FormOfEducation.DISTANCE_EDUCATION;
            case "EVENING_CLASSES": return FormOfEducation.EVENING_CLASSES;
            default: return null;
        }
    }
    public FormOfEducation createFormWithOpt(String s){
        if(s == null){
            return null;
        }
        return FormOfEducation.valueOf(s);
    }
    public Semester createSemester(){
        System.out.println("выберите 1 из предложенных варинтов:   \n FIRST,\n" +
                "THIRD,\n" +
                "FOURTH,\n" +
                "EIGHTH;");
        String s = scanner.nextLine();
        switch (s){
            case "FIRST": return Semester.FIRST;
            case "THIRD": return Semester.THIRD;
            case "FOURTH": return Semester.FOURTH;
            case "EIGHTH": return Semester.EIGHTH;
            default: return createSemester();
        }
    }


}
