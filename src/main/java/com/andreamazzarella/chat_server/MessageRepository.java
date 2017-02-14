package com.andreamazzarella.chat_server;

import java.util.List;

public interface MessageRepository {
    void add(Message message);
    List<Message> all();
}
