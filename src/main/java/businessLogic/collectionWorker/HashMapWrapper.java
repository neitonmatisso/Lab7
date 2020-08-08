package businessLogic.collectionWorker;

import businessLogic.sourseDate.Person;
import businessLogic.sourseDate.StudyGroup;

import java.util.*;
import java.util.stream.Collectors;

/*
     Реализация интерфейса для Map.Содержит классические методы для каждого контейнера+ особые методы для мапы
 */
public class HashMapWrapper implements CollectionWrapper {
    private Map<Long,StudyGroup> groupMap;
    private Date createDate;
    private String owner = "hz";


    public HashMapWrapper(){
        groupMap = new HashMap<>();
        createDate = new Date();
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String printAdmins(){
        if(groupMap.size() == 0){
            return "админов нет";
        }
        Set<Long> keySet = groupMap.keySet();
        List<Person> adminList = new ArrayList<>();

        adminList = groupMap.values().stream()
                .map(StudyGroup::getGroupAdmin)
                .collect(Collectors.toList());

        adminList.sort(Comparator.comparing(Person::getPassportID));

        return adminList.toString();

    }

    public String removeBySBE(int a){
        groupMap.keySet().removeIf( aLong -> (groupMap.get(aLong).getShouldBeExpelled() == a && groupMap.get(aLong).getOwner().equals(owner)));
        return "Удаление прошло успешно";

    }

    public String removeLowerKey(Long l){
        long buffer = 0L;
        for(Long i : groupMap.keySet()){
            if ( i < l){
                buffer++;
            }
        }
        if ( buffer == 0){
            return "нету id ниже заданого";
        }
        groupMap.keySet().removeIf(x -> (groupMap.get(x).getId() < l && groupMap.get(x).getOwner().equals(owner)));
        return "Удаление прошло успешно";
    }

    public int sumOfElement(){
        Set<Long> keySet = groupMap.keySet();
       int result = 0;
       result = groupMap.values()
                        .stream()
                        .mapToInt(StudyGroup::getShouldBeExpelled)
                        .sum();
        return result;
    }

    public String addElementWithID(Long id, StudyGroup st) {
        groupMap.put(id, st);
        return "Новый элемент успешно добавлен!";
    }

    @Override
    public String addElement(StudyGroup st) {
        groupMap.put(st.getId(),st);
        return "Новый элемент успешно добавлен!";
    }

    @Override
    public String removeElement(long id) {
        if (groupMap.get(id) == null){
            return "элемента с таким айди не существует";
        }
        if (groupMap.get(id).getOwner().equals(owner)){
            return "У вас нет доступа к этой записи";
        }
        groupMap.remove(id);
        return "Элемент успешно удален!";

    }

    @Override
    public String info() {
        return "\n" + "Тип коллекции: HashMap" + "\n"+
                "Дата создания: " + createDate + "\n" +
                "Количество элементов: " + groupMap.size();
    }

    @Override
    public String show() {
        if(groupMap.size() == 0) {
            return "коллекция пуста!";
        }

        String s = "";
        Set<Long> keys = groupMap.keySet();


        for(Long key : keys){
            s+=groupMap.get(key).toString();
        }
        return s;
    }

    @Override
    public String updateById(long id, StudyGroup st) {
        if(groupMap.get(id) == null){
            return "По данному адресу нет элемента , запись не будет произведена";
        }
        st.setId(id);
        groupMap.replace(id, st);
        return "Объект: " + st.getName() + " теперь хранится по адресу "+ id;
    }

    @Override
    public String clear() {
        groupMap.clear();
        return "коллекция была очищена";
    }

    @Override
    public Boolean isEmpty() {
        return groupMap.isEmpty();
    }

    @Override
    public Iterator getIterator() {
        return groupMap.keySet().iterator();
    }

    @Override
    public StudyGroup getStudyGroup(long l) {
        return groupMap.get(l);
    }


}
