package com.andreamazzarella.chat_server;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;


public class ClientConnection implements Connection {

    private final Socket rawClientSocket;
    private final BufferedReader reader;
    private final PrintStream writer;

    public ClientConnection(Socket rawClientSocket) {
        this.rawClientSocket = rawClientSocket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(rawClientSocket.getInputStream()));
            this.writer = new PrintStream(rawClientSocket.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void sendMessage(String message) {
        writer.println(message);
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void connect(SocketAddress serverAddress) throws IOException {
        rawClientSocket.connect(serverAddress);
    }
}
