package com.andreamazzarella.chat_server;

import com.andreamazzarella.chat_application.Message;

import java.util.List;

public interface MessageRepository {
    void add(Message message);

    List<Message> all();
}
