package kito.lab5.server.client;



import kito.lab5.server.exceptions.EndOfFileException;
import kito.lab5.server.utils.*;
import kito.lab5.common.util.Request;
import kito.lab5.common.util.Response;
import kito.lab5.common.util.Serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }



// по услови задания на сервере потоки ввода-вывода
    public static void main(String[] args) throws IOException, ClassNotFoundException, EndOfFileException {
        Scanner scanner = new Scanner(System.in);
        HumanFactory humanFactory = new HumanFactory(new ScannerFieldsGetter(scanner), new ReaderFieldsGetter(null));
        Selector selector = Selector.open();
        SocketAddress clientAddress = new InetSocketAddress(InetAddress.getLocalHost(), 4550);
        SocketChannel clientChannel = SocketChannel.open(clientAddress);
        System.out.println("Connected!");
        System.out.println("введите help чтобы увидеть список допустимых команд.");
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_WRITE);

        UserInputHandler userInputHandler = new UserInputHandler();
        exit:
        while (true) {
            selector.select(2000);                      // TODO 0709 timeout added
            System.out.println("timeout reached");                      // TODO 0709 added
            Set<SelectionKey> keys = selector.selectedKeys();
            System.out.println(keys);
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isReadable()) {
//                    SocketChannel serverChannel = (SocketChannel) key.channel();    // TODO 0709 REMOVED
//                    serverChannel.configureBlocking(false);    // TODO 0709 REMOVED
//                    // TODO new code below
//                    Response response = new Response();
//                    ObjectOutputStream oos = new ObjectOutputStream(serverChannel.socket().getOutputStream());
//                    oos.writeObject(response);
//                    System.out.println(response.getMessage());
//                    // TODO new code above
                    ByteBuffer buffer = ByteBuffer.allocate(4096);
                    System.out.println(Arrays.toString(buffer.array()));            // TODO UNDO IMPORT
                    clientChannel.read(buffer);
                    System.out.println(Arrays.toString(buffer.array()));            // TODO UNDO IMPORT
                    Response response = Serializer.deSerializeResponse(buffer.array());
                    ResponseHandler.handleResponse(response);
                    if (response.isObjectNeeded() && !response.isUpdate()) {
                        Request request = new Request();
                        request.setHuman(humanFactory.start(true, response.getArgs()));// Что нужно передать в объекте только строку или всю коллекцию(HumanBeing)?
                        request.setCommandNameAndArguments("addfinal");

                        try {
                            ByteBuffer bufferToSend = Serializer.serializeRequest(request);
                            clientChannel.write(bufferToSend);
                            bufferToSend.clear();
                            clientChannel.register(selector, SelectionKey.OP_READ);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (response.isObjectNeeded() && response.isUpdate()) {
                        Request request = new Request();
                        request.setHuman(humanFactory.start(true, response.getArgs()));// Что нужно передать в объекте только строку или всю коллекцию(HumanBeing)?
                        request.setCommandNameAndArguments("updatefinal");

                        try {
                            ByteBuffer bufferToSend = Serializer.serializeRequest(request);
                            clientChannel.write(bufferToSend);
                            bufferToSend.clear();
                            clientChannel.register(selector, SelectionKey.OP_READ);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                } else {
                        clientChannel.register(selector, SelectionKey.OP_WRITE);
                    }
                } else {                             //      if (key.isWritable()) {     // TODO 0709 REMOVED

                    Request request = new Request();
                    request.setCommandNameAndArguments(userInputHandler.getCommand());
                    if (request.getCommandNameAndArguments().equals("exit")){
                        break exit;
                    }
                    try {
                        System.out.println(request.getCommandNameAndArguments());       // TODO 0709 ADDED
                        ByteBuffer buffer = Serializer.serializeRequest(request);
                        System.out.println(Serializer.deSerializeRequest(buffer.array()));       // TODO 0709 ADDED
                        clientChannel.write(buffer);
                        buffer.clear();
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
