package kito.lab5.server.user_command_line;

import kito.lab5.common.entities.HumanBeing;
import kito.lab5.common.entities.enums.WeaponType;
import kito.lab5.server.utils.HumanValidator;
import kito.lab5.server.utils.StringToTypeConverter;
import kito.lab5.server.utils.TextSender;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс, считывающий информации о человеке в интерактивном режиме
 */
public class HumanInfoInput {

    static private Scanner scanner = new Scanner(System.in);
    private final HumanBeing newHumanToInput;
    private final String name;
    private final String realHero;
    private final String hasToothpick;
    private final String impactSpeed;
    
    private TextSender sender;

    private String soundtrackname;

    private final String MinutesOfWaiting;

    /**
     * Конструктор создающий человека информацию о котором мы хотим получить автоматически
     */
    public HumanInfoInput(String[] args) {
        this.name = args[0];
        this.realHero = args[1];
        this.hasToothpick = args[2];
        this.impactSpeed = args[3];
        this.MinutesOfWaiting = args[4];
        newHumanToInput = new HumanBeing(false);
        setPrimitives();
    }

    /**
     * Конструктор принимающий человека информацию о котором мы хотим изменить
     * @param newHumanToInput человек информацию о котором мы хотим изменить
     */
    public HumanInfoInput(HumanBeing newHumanToInput, String[] args, TextSender sender) {
        this.name = args[0];
        this.realHero = args[1];
        this.hasToothpick = args[2];
        this.impactSpeed = args[3];
        this.sender = sender;

        this.MinutesOfWaiting = args[4];
        this.newHumanToInput = newHumanToInput;
        setPrimitives();
    }

    private void inputName() {
        newHumanToInput.setName(name);
        boolean validationResult = HumanValidator.validateField(newHumanToInput, "name");
        if (!validationResult) {
            throw new IllegalArgumentException("Ошибка ввода имени человека");
        }
    }

    private void inputX() throws NumberFormatException {
        sender.sendText("Введите X(дробное число): ");
        String userInput = scanner.nextLine();
        try {
            newHumanToInput.getCoordinates().setX((float) StringToTypeConverter.toObject(float.class, userInput));
            boolean validationResult = HumanValidator.validateField(newHumanToInput.getCoordinates(), "X");
            if (!validationResult) {
                inputX();
            }
        } catch (NumberFormatException e) {
            sender.sendError("Ошибка ввода числа X");
            inputX();
        }
    }

    private void inputY() throws NumberFormatException {
        sender.sendText("Введите Y(целое): ");
        String userInput = scanner.nextLine();
        try {
            @javax.validation.constraints.NotNull Integer y = (Integer) StringToTypeConverter.toObject(Integer.class, userInput);
            newHumanToInput.getCoordinates().setY(y);
            boolean validationResult = HumanValidator.validateField(newHumanToInput.getCoordinates(), "y");
            if (!validationResult) {
                inputY();
            }
        } catch (NumberFormatException e) {
            sender.sendError("Ошибка ввода числа Y");
            inputY();
        }
    }

    private void inputRealHero() {
        boolean realHeroValue;
        if (this.realHero.equals("true")) {
            newHumanToInput.setRealHero(true);
        } else if (this.realHero.equals("false")) {
            newHumanToInput.setRealHero(false);
        } else {
            throw new IllegalArgumentException("Ошибка ввода героизма человека");
        }
    }

    private void inputHasToothpick() {
        boolean hasToothpickValue;
        if (this.hasToothpick.equals("true")) {
            newHumanToInput.setHasToothpick(true);
        } else if (this.hasToothpick.equals("false")) {
            newHumanToInput.setHasToothpick(false);
        } else {
            throw new IllegalArgumentException("Ошибка ввода наличия у человека зубочистки");
        }
    }

    private void inputImpactSpeed() throws NumberFormatException {
        if ("".equals(this.impactSpeed)) {
            newHumanToInput.setImpactSpeed(null);
        } else {
            newHumanToInput.setImpactSpeed((Double) StringToTypeConverter.toObject(Double.class, this.impactSpeed));
            boolean validationResult = HumanValidator.validateField(newHumanToInput, "impactSpeed");
            if (!validationResult) {
                throw new IllegalArgumentException("Ошибка ввода скорости удара человека");
            }
        }
    }

    private void inputWeaponType() {
        sender.sendText("Введите тип оружия из предложенных вариантов или оставьте пустую строку, если оружия нет: ");
        sender.sendText(Arrays.toString(WeaponType.values()));
        String userInput = scanner.nextLine();
        if ("".equals(userInput)) {
            newHumanToInput.setWeaponType(null);
        } else {
            try {
                newHumanToInput.setWeaponType((WeaponType) StringToTypeConverter.toObject(WeaponType.class, userInput));
            } catch (IllegalArgumentException e) {
                sender.sendError("Ошибка ввода типа оружия");
                inputWeaponType();
            }
        }

    }





    private void inputCarCoolness() {
        sender.sendText("Машина крутая?[y/n]: ");
        String userInput = scanner.nextLine().toLowerCase();
        if ("y".equals(userInput)) {
            userInput = "true";
        } else if ("n".equals(userInput)) {
            userInput = "false";
        } else {
            sender.sendError("Ошибка ввода крутости машины");
            inputCarCoolness();
        }
        newHumanToInput.getCar().setCool((Boolean) StringToTypeConverter.toObject(Boolean.class, userInput));
    }

    private void inputCar() {
        sender.sendText("Есть ли у человека машина?[y/n]");
        String userAnswer = scanner.nextLine();
        if ("y".equals(userAnswer)) {
            inputCarCoolness();
            inputCarName();

        } else if ("n".equals(userAnswer)) {
            newHumanToInput.setCar(null);
        } else {
            sender.sendError("Ошибка ввода");
            inputCar();
        }
    }

    private void inputCarName() {
        sender.sendText("Введите название машины");
        String userAnswer = scanner.nextLine();
        newHumanToInput.getCar().setCarname(userAnswer);

    }

    private void inputSoundtrackname() {
        sender.sendText("Введите название музыки");
        String userAnswer = scanner.nextLine();
        if ("".equals(userAnswer)) {
            newHumanToInput.setSoundtrackName(null);
        } else {
            newHumanToInput.setSoundtrackName(userAnswer);
        }

    }
    private void inputMinutesOFwaiting() throws NumberFormatException {

        if ("".equals(this.MinutesOfWaiting)) {
            newHumanToInput.setMinutesOfWaiting(null);
        } else {
            newHumanToInput.setMinutesOfWaiting((Float) StringToTypeConverter.toObject(Float.class, this.MinutesOfWaiting));
            boolean validationResult = HumanValidator.validateField(newHumanToInput, "MinutesOfWaiting");
            if (!validationResult) {
                throw new IllegalArgumentException("Ошибка ввода скорости удара человека");
            }
        }
    }

    /**
     * метод отвечающий за присвоение примитивов человеку
     */
    public void setPrimitives() {
        inputName();
        inputRealHero();
        inputHasToothpick();
        inputImpactSpeed();
        inputMinutesOFwaiting();
    }

    /**
     * метод отвечающий за ввод не примитивных типов данных человека
     */
    public void inputHuman() {
        inputImpactSpeed();
        inputX();
        inputY();
        inputSoundtrackname();
        inputWeaponType();
        inputCar();
    }

    /**
     * @return введенный пользователем человек
     */
    public HumanBeing getNewHumanToInput() {
        newHumanToInput.setId();
        return newHumanToInput;
    }
}
