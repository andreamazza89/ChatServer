package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.ClientConnection;
import com.andreamazzarella.chat_application.RealChatProtocol;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);
        ChatRoom chatRoom = new ChatRoom(new RealChatProtocol());

        start(chatRoom, portNumber);
    }

    private static void start(ChatRoom chatRoom, int portNumber) {
        System.out.println("Server running on port " + portNumber);
        ExecutorService connections = Executors.newCachedThreadPool();

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

                Socket rawSocket = serverSocket.accept();

                connections.submit(() -> {
                    User user = new RealUser(new ClientConnection(rawSocket), new RealChatProtocol());
                    user.greet();
                    user.askUserName();
                    chatRoom.addSubscriber(user);
                    user.startConversation();
                });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}