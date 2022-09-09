package kito.lab5.server.user_command_line;

import kito.lab5.server.abstractions.AbstractMessage;

public class SuccessMessage extends AbstractMessage {

    private static final String MESSAGE_COLOR = "\u001B[32m"; //ANSI_GREEN

    public SuccessMessage(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE_COLOR + super.getMessage();
    }
}
