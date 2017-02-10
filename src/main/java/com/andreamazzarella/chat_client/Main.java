package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.ClientConnection;
import com.andreamazzarella.chat_server.Connection;
import com.andreamazzarella.chat_server.RealChatProtocol;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);



        /////////////////////////////// ?Session?: takes the localIO to let the user know what is going on and either
        /////////////////////////////////returns a socket or exits
        System.out.println("Welcome to the ChattyChat; tryihng to connect to server on port " + serverPort);
        Socket rawSocket = null;
        try {
            rawSocket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.out.println("Could not reach the server: please check server address, port number and ensure server is up");
            System.exit(1);
        }
        System.out.println("Connected to server!");
        ///////////////////////////////////////////////////////////////////
        ////////// says goodbye before disconnecting(TO DO) /////////////////
        ///////////////////////////////////////////////////////////////////

        Connection connection = new ClientConnection(rawSocket);
        LocalIO localIO = new Console(System.in, System.out, new RealChatProtocol());

        new ChatClient(localIO, connection).startCommunication();
    }
}
