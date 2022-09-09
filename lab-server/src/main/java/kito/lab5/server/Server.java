package kito.lab5.server;

import kito.lab5.server.utils.TextSender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public final class Server {
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    InputStream is;

    OutputStream os;

    public static void main(String[] args) {
        Server server = new Server();
        TextSender.changePrintStream(server.objectOutputStream);
        Application application = new Application(server.is);            // TODO 0709 REPLACED  .objectInputStream);
        System.out.println(System.getenv("HUMAN_INFO"));
        application.launchApplication();
    }
    public Server(){
        setUpConnection();
    }
    public void setUpConnection(){
        ServerSocket ss=null;
        try {
            ss = new ServerSocket(4550);
            Socket s= ss.accept();

            is = s.getInputStream();
            os = s.getOutputStream();
            TextSender.os = os;     // TODO 0709 added


        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
