package com.andreamazzarella.chat_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientConnection implements Connection {

    private final Socket rawClientSocket;

    public ClientConnection(Socket rawClientSocket) {
        this.rawClientSocket = rawClientSocket;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return rawClientSocket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return rawClientSocket.getInputStream();
    }

    @Override
    public void connect(SocketAddress serverAddress) throws IOException {
        rawClientSocket.connect(serverAddress);
    }
}
