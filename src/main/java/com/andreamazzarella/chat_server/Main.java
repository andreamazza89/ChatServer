package com.andreamazzarella.chat_server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        int portNumber = Integer.parseInt(args[0]);

        start(chatRoom, portNumber);
    }

    static void start(ChatRoom chatRoom, int portNumber) {
        System.out.println("Server running on port " + portNumber);
        ExecutorService connections = Executors.newCachedThreadPool();

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

                Socket rawSocket = serverSocket.accept();

                connections.submit(() -> {
                    MessageExchanger messageExchanger = new MessageExchanger(new ClientConnection(rawSocket), chatRoom);
                    chatRoom.addSubscriber(messageExchanger);
                    messageExchanger.startListening();
                });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}