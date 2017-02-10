package com.andreamazzarella.support;

import com.andreamazzarella.chat_server.ChatProtocol;
import com.andreamazzarella.chat_server.User;

public class FakeProtocol implements ChatProtocol{
    @Override
    public ChatProtocol messageFrom(User user) {
        return null;
    }

    @Override
    public ChatProtocol addContent(String content) {
        return this;
    }

    @Override
    public String encodeMessage() {
        return null;
    }

    @Override
    public String decodeUserName(String encodedMessage) {
        return null;
    }

    @Override
    public String decodeMessageContent(String encodedMessage) {
        return null;
    }
}
