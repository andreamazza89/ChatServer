package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_application.DataExchange;
import com.andreamazzarella.chat_application.ClientConnection;
import com.andreamazzarella.chat_application.ChatProtocol;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);
        Socket rawSocket = null;

        System.out.println("Welcome to the ChattyChat; trying to connect to server on port " + serverPort);
        try {
            rawSocket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.out.println("Could not reach the server: please check server address, port number and ensure server is up");
            System.exit(1);
        }
        System.out.println("Connected to server!");

        DataExchange connection = new ClientConnection(rawSocket);
        DataExchange localIO = new Console(System.in, System.out, new ChatProtocol());

        new ChatClient(localIO, connection).startCommunication();
    }
}
