package com.andreamazzarella.chat_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;

public interface ClientSocket {
    OutputStream getOutputStream() throws IOException;
    InputStream getInputStream() throws IOException;

    void connect(SocketAddress serverAddress) throws IOException;
}
