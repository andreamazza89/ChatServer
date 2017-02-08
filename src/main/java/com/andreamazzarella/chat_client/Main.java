package com.andreamazzarella.chat_client;

import com.andreamazzarella.chat_server.ClientConnection;
import com.andreamazzarella.chat_server.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        System.out.println("welcome");

        Socket socket = new Socket(serverAddress, serverPort);
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
