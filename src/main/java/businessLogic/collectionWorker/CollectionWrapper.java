package businessLogic.collectionWorker;

import businessLogic.sourseDate.StudyGroup;

import java.util.Iterator;
/*
Общий интерфейс для оберток над коллекциями. В последствии можно заменить одну коллекцию на другую без переписиогромного количества кода
 */
public interface CollectionWrapper {
    String addElement(StudyGroup st);
    String removeElement(long id);
    String info();
    String show();
    String updateById(long id, StudyGroup st);
    String clear();
    Boolean isEmpty();
    Iterator getIterator();
    StudyGroup getStudyGroup(long l);
}
