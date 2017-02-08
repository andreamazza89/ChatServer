package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.ClientConnection;
import com.andreamazzarella.chat_server.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);



        /////////////////////////////// Session: takes the localIO to let the user know what is going on and either
        /////////////////////////////////returns a socket or exits
        System.out.println("Welcome to the ChattyChat; tryihng to connect to server on port " + serverPort);
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.out.println("Could not reach the server: please check server address, port number and ensure server is up");
            System.exit(1);
            e.printStackTrace();
        }
        System.out.println("Connected to server!");
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////



        Connection connection = new ClientConnection(socket);
        LocalIO localIO = new LocalIO() {
            @Override
            public InputStream getInputStream() {
                return System.in;
            }

            @Override
            public OutputStream getOutputStream() {
                return System.out;
            }
        };

        new ChatClient(localIO, connection).startCommunication();
    }
}
