package com.andreamazzarella.chat_server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        int portNumber = Integer.parseInt(args[0]);

        while (true) {
            try (
                ServerSocket serverSocket = new ServerSocket(portNumber)
                ) {
                Socket rawSocket = serverSocket.accept();
                MessageExchanger messageExchanger = new MessageExchanger(new RemoteSocket(rawSocket), chatRoom);
                chatRoom.addMessageExchanger(messageExchanger);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}