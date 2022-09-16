package kito.lab5.server.utils;

import kito.lab5.common.util.Response;
import kito.lab5.common.util.Serializer;

import java.io.*;
import java.nio.ByteBuffer;

public class TextSender {

    public static final String MESSAGE_COLOR = "\u001B[32m"; //ANSI_GREEN
    public static final String ERROR_COLOR = "\u001B[31m"; //ANSI_RED
    public static final String ANSI_RESET = "\u001B[0m";
    public static PrintStream printStream = System.out;
    public ObjectOutputStream oos;       // TODO 0709 remove if useless
    public OutputStream os;

    public TextSender(OutputStream os) {
        this.os = os;
    }

    public void sendObjectNeeded(String[] args) {
        try {
            Response response = new Response();
            response.setObjectNeeded(true);
            response.setArgs(args);

            ByteBuffer buffer = Serializer.serializeResponse(response);
            os.write(buffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendText(String text) {
        try {
            Response response = new Response();
            response.setMessage(text);

            ByteBuffer buffer = Serializer.serializeResponse(response);     // TODO added 0709
            os.write(buffer.array());

//            oos.writeObject(response);        // TODO commented out 0709
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendError(String text) {
        try {
            Response response = new Response();
            response.setMessage(text);

            ByteBuffer buffer = Serializer.serializeResponse(response);     // TODO added 0709
            os.write(buffer.array());


//            oos.writeObject(response);        // TODO commented out 0709
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String text) {
        try {
            Response response = new Response();
            response.setMessage(text);

            ByteBuffer buffer = Serializer.serializeResponse(response);     // TODO added 0709
            os.write(buffer.array());

//            oos.writeObject(response);        // TODO commented out 0709
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changePrintStream(ObjectOutputStream newPrintStream) {
        oos = newPrintStream;
    }
}
