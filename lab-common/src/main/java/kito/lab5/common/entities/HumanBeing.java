package kito.lab5.common.entities;

import javax.validation.Valid;
import javax.validation.constraints.*;

import kito.lab5.common.entities.enums.WeaponType;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 *  Класс человек, коллекцией экземпляров которого управляет коллекция и пользователь в интерактивном режиме
 */

public class HumanBeing implements Comparable<HumanBeing>, Serializable {

    private static Integer idCounter = 1;
    private Integer id;
    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")
    @NotNull
    @NotEmpty
    private String name;
    @Valid
    @NotNull
    private Coordinates coordinates = new Coordinates();
    private @NotNull ZonedDateTime creationDate;
    @NotNull
    private Boolean RealHero;
    private boolean hasToothpick;
    private @Max(324) Double impactSpeed;
    private @NotNull String soundtrackName;
    private @NotNull Float MinutesOfWaiting;
    private @NotNull WeaponType weaponType;
    private @NotNull Car car = new Car();

    /**
     * Конструктор класса
     * @param setIdAutomatically если true, то значение ID устанавливается автоматически в конструкторе
     */
    public HumanBeing(boolean setIdAutomatically) {
        if (setIdAutomatically) {
            setId();
        }
        creationDate = ZonedDateTime.now();
    }

    public void setRealHero(Boolean realHero) {
        this.RealHero = realHero;
    }

    /**
     * @return ID человека
     */
    public long getId() {
        return id;
    }

    /**
     * Метод, устанавливающий ID в зависимости от idCounter
     */
    public void setId() {
        this.id = idCounter++;
    }

    /**
     * @return имя человека
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, позволяющий задать имя человека
     * @param name значение имени
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return координаты человека
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод, позволяющий задать значение координат человека
     * @param coordinates значение координат
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return дата создания человека
     */
    public @NotNull ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, позволяющий задать дату создания координат человека
     * @param creationDate дата создания
     */
    public void setCreationDate(@NotNull ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return героизм человека
     */


    /**
     * Метод, позволяющий задать героизм человека
     * @param realHero героизм человека в строковом формате
     */


    /**
     * @return наличие зубочистки у человека
     */
    public boolean isHasToothpick() {
        return hasToothpick;
    }

    /**
     * Метод, позволяющий задать наличие зубочистки у человека
     * @param hasToothpick нализчие зубочистки у человека в строковом формате
     */
    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    /**
     * @return скорость удара человека
     */
    public @Max(324) Double getImpactSpeed() {
        return impactSpeed;
    }

    /**
     * Метод, позволяющий задать значение координат человека
     * @param impactSpeed скорость удара в строковом формате
     */
    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    /**
     * @return тип оружия человека
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Метод, позволяющий задать тип оружия человека
     * @param weaponType тип оружия в строковом формате
     */
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    /**
     * @return настроение человека
     */
    public static Integer getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(Integer idCounter) {
        HumanBeing.idCounter = idCounter;
    }
    public Boolean isRealHero() {
        return RealHero;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRealHero() {
        return RealHero;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    public Float getMinutesOfWaiting() {
        return MinutesOfWaiting;
    }

    public void setMinutesOfWaiting(Float minutesOfWaiting) {
        this.MinutesOfWaiting = minutesOfWaiting;
    }


    /**
     * @return машина человека
     */
    public Car getCar() {
        return car;
    }

    /**
     * Метод, позволяющий задать машину человека
     * @param car значение машины
     */
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public int compareTo(HumanBeing o) {
        if (o == null) {
            return 1;
        }
        int result = new Float(getCoordinates().getX()).compareTo(new Float(o.getCoordinates().getX()));
        if (result == 0) {
            result = getCoordinates().getY().compareTo(o.getCoordinates().getY()); //TODO проверить сортировку когда закончу сериализацию
        }
        return result;
    }

    @Override
    public String toString() {
        return id + ". " + name + ", X: "
                + coordinates.getX() + ", Y: " + coordinates.getY()
                + ", CREATION DATE: " + creationDate + ", REAL HERO: " + RealHero
                + ", HAS TOOTHPICK: " + hasToothpick + ", IMPACT SPEED: " + impactSpeed
                + ", WEAPON TYPE: " + weaponType +    ", CAR INFO: " + (car == null ? "no car" : car.toString());
    }
}
