package kito.lab5.server;

import kito.lab5.server.utils.TextSender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionManager {
    private String login;
    private Socket s;
    private InputStream is;
    private OutputStream os;
    private Application app;
    private TextSender sender;

    public ConnectionManager(Socket s) {
        this.s = s;
        try {
            is = s.getInputStream();
            os = s.getOutputStream();
            sender = new TextSender(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            app = new Application(is,sender);
        }).start();
    }




}
