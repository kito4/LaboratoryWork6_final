package kito.lab5.server;

import kito.lab5.common.entities.Coordinates;
import kito.lab5.common.entities.HumanBeing;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией экземпляров HumanBeing
 */
public class CollectionManager {

    private final ConcurrentSkipListSet<HumanBeing> humanQueue = new ConcurrentSkipListSet<>(); //change priorityqueue to treeset to concurrentSkipListSet
    private final LocalDate initializationDate;

    /**
     * Конструктор, задаюший дату инициализации коллекции и поле fileName
     * @param fileName имя файла, в котором хранятся данные о коллекции
     */
    public CollectionManager(String fileName) {
        initializationDate = LocalDate.now();
    }

    private ArrayList<HumanBeing> getSortedArrayListFromQueue () {
        PriorityQueue<HumanBeing> bufferQueue = new PriorityQueue<>(humanQueue);
        ArrayList<HumanBeing> sortedArrayList = new ArrayList<>();
        while (!bufferQueue.isEmpty()){
            sortedArrayList.add(bufferQueue.poll());
        }
        return sortedArrayList;
    }

    /**
     * Метод, добавляющий экземпляр HumanBeing в коллекцию
     * @param human экзепмпляр HumanBeing
     */
    public void addHuman(HumanBeing human) {
        humanQueue.add(human);
        //сортировка по местоположению;

    }

    /**
     * Метод, удаляющий человека из коллекции по заданному ID
     * @param id id человека
     */
    public boolean removeHumanById(int id) {

        return humanQueue.remove(humanQueue.stream().filter(human ->human.getId()==id).collect(Collectors.toList()).get(0));

//        humanQueue.removeIf(human -> human.getId() == id);
    }

    /**
     * Метод, удаляющий любого человека из коллекции по заданному значению настроения
     * @param mood значение настроения
     */
//    public void removeHumanByAnyMood(Mood mood) {
//        for (HumanBeing human : humanQueue) {
//            if (human.getMood() == mood) {
//                humanQueue.remove(human);
//                break;
//            }
//        }
//    }

    /**
     * Метод, позволяющий получить человека из коллекции по id
     * @param id id человека
     * @return экземпляр HumanBeing, соответствующий полученному значению ID
     * @throws IllegalArgumentException
     */
    public HumanBeing getHumanById(int id) throws IllegalArgumentException {

        return  humanQueue.stream().filter(human -> human.getId() == id).collect(Collectors.toList()).get(0);


    }

    /**
     * Метод, позволяющий обновить информацию о человеке из коллекции по id
     * @param id id человека
     * @param humanToSet новая информация о человеке
     * @throws IllegalArgumentException
     */
    public void setHumanById(int id, HumanBeing humanToSet) throws IllegalArgumentException { // how

        if (!removeHumanById(id)) {
            throw new IllegalArgumentException("Человек с таким ID не найден");
        }
        humanQueue.add(humanToSet);


    }

    /**
     * Метод выводящий информацию о коллекции
     */
    public String getStringForShowing() {
        ArrayList<HumanBeing> listToShow = getSortedArrayListFromQueue();
        return listToShow.stream()
                .map(HumanBeing::toString)
                .collect(Collectors.joining("\n"));
    }

    public ConcurrentSkipListSet<HumanBeing> getHumanQueue() {
        return humanQueue;
    }

    /**
     * Метод, позволяющий заполнить коллекцию при помощи массива HumanBeing
     * @param arrayOfPeople массив HumanBeing
     */
    public void fillWithArray(ArrayList<HumanBeing> arrayOfPeople) {

        for (HumanBeing human : arrayOfPeople) {
            Coordinates coor = new Coordinates();

            System.out.println(human);                      // TODO 0709 added this and 5 lines above
            addHuman(human);
        }
    }

    /**
     * @return наибольший элемент коллекции
     */


    /**
     * @return массив людей коллекции, расположенных в порядке возрастания
     */
    public ArrayList<HumanBeing> returnDescending() {
        ArrayList<HumanBeing> descendingList = getSortedArrayListFromQueue();
        Collections.sort(descendingList);
        return descendingList;
    }

    /**
     * @return Возвращает длину коллекции
     */
    public int getLength() {
        return humanQueue.size();
    }

    /**
     * @param speed скорость машины
     * @return все элементы коллекции, скорость которых меньше заданной
     */


    /**
     * Метод, добавляющий человека в коллекцию, если его значение является минимальным
     * @param newHuman человек для добавления
     */
    public void addIfMin(HumanBeing newHuman) {
        for (HumanBeing human : humanQueue) {
            if (newHuman.compareTo(human) > 0) {
                return;
            }
        }
        addHuman(newHuman);
    }

    /**
     * Метод, полностью очищающий коллекцию
     */
    public void clearCollection() {
        humanQueue.clear();
    }

    /**
     * @return информация о коллекции в строковом формате
     */
    public String getInfoAboutCollection() {
        return "Информация о коллекции. Тип: " + humanQueue.getClass() + " Дата инициализации: "
                + initializationDate.toString() + " Количество элементов: " + humanQueue.size();
    }

    /**
     * @return информация обо всех элементах коллекции в строковом формате
     */
    public List<String> getArrayOfInfo() {
        ArrayList<String> arrayOfInfo = new ArrayList<>();
        for (HumanBeing human : humanQueue) {
            String humanInfo = human.getName() + "," + human.getCoordinates().getX() + ","
                    + human.getCoordinates().getY() + "," + human.getCreationDate().toString()
                     + "," + human.isHasToothpick() + ","
                    + (human.getImpactSpeed() == null ? "null," : human.getImpactSpeed() + ",")
                    + (human.getSoundtrackName() == null ? "null," : human.getSoundtrackName() + ",")
                    + (human.getMinutesOfWaiting() == null ? "null," : human.getMinutesOfWaiting() + ",")
                    + (human.getWeaponType() == null ? "null," : human.getWeaponType() + ",")
                    + (human.getCar().getCool() +",")
                    + (human.getCar().getCarname() == null ? "null" : (human.getCar().getCarname()   + ",")
                    + (human.getRealHero() == null ? "null," : human.getRealHero()));
            arrayOfInfo.add(humanInfo);
        }
        return arrayOfInfo;
    }
}
