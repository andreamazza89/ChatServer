package com.andreamazzarella.chat_server;

import java.util.Optional;

public class Message {
    private final Optional<String> userName;
    private final String content;

    public Message(Optional<String> userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Optional<String> getUserName() {
        return userName;
    }
}
