package com.andreamazzarella.chat_server;

import java.io.IOException;
import java.net.SocketAddress;

public interface Connection {
    void sendMessage(String message);
    String readLine();
    void connect(SocketAddress serverAddress) throws IOException;
}
