package com.andreamazzarella.chat_server;

public interface ChatProtocol {
    ChatProtocol messageFrom(User user);
    ChatProtocol addContent(String content);
    String encodeMessage();
    String decodeUserName(String encodedMessage);
    String decodeMessageContent(String encodedMessage);
}
