package kito.lab5.server;

import kito.lab5.server.user_command_line.CommandManager;

public class Config {
    private static final String sysEnvironment = "C:\\Users\\nikit\\OneDrive\\Рабочий стол\\LaboratoryWork5_just_rename_folder-main_0907\\lab-server\\humans.csv";     // "HUMAN_INFO";
    private static final CollectionManager collectionManager = new CollectionManager(sysEnvironment);
    private static final CommandManager commandManager = new CommandManager();

    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public static CommandManager getCommandManager() { return commandManager; }

    public static String getSystemEnvironment() {
        return sysEnvironment;
    }

    public static String getFilePath() {
        return "C:\\Users\\nikit\\OneDrive\\Рабочий стол\\LaboratoryWork5_just_rename_folder-main_0907\\lab-server\\humans.csv";
    }
}
