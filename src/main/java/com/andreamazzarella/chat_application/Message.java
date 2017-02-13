package com.andreamazzarella.chat_application;

import com.andreamazzarella.chat_server.User;

public class Message {
    private final User user;
    private String content;

    public Message(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }
}
