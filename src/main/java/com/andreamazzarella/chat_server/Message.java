package com.andreamazzarella.chat_server;

import java.util.Optional;

public class Message {
    private final Optional<User> user;
    private String content;

    public Message(Optional<User> user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user.orElse(new NullUser());
    }
}
