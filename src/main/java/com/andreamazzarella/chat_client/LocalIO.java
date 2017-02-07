package com.andreamazzarella.chat_client;

import java.io.InputStream;
import java.io.OutputStream;

public interface LocalIO {
    InputStream getInputStream();
    OutputStream getOutputStream();
}
