package com.andreamazzarella.chat_server;

import java.io.*;

public class ClientHandler {
    private final BufferedReader fromClient;
    private final PrintStream toClient;
    private final ClientConnection clientSocket;
    private final Notifiable fIND_a_good_name_for_this_please;

    public ClientHandler(ClientConnection clientSocket, Notifiable fIND_A_GOOD_NAME_FOR_THIS_PLEASE) {
        this.clientSocket = clientSocket;
        this.fIND_a_good_name_for_this_please = fIND_A_GOOD_NAME_FOR_THIS_PLEASE;
        try {
            toClient = new PrintStream(clientSocket.getOutputStream());
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void forward(String message) {
        toClient.println(message);
    }

    public void start() {
        String message_received;
        try {
            while ((message_received = fromClient.readLine()) != null) {
                fIND_a_good_name_for_this_please.messageFromClient(message_received, clientSocket);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
