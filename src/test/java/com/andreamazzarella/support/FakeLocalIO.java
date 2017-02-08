package com.andreamazzarella.support;

import com.andreamazzarella.chat_client.LocalIO;

import java.io.*;

public class FakeLocalIO extends FakeIO implements LocalIO {

    @Override
    public InputStream getInputStream() {
        return pipedInputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
