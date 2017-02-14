package com.andreamazzarella.chat_server;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageRepository implements MessageRepository {
    List<Message> messages = new ArrayList<>();

    @Override
    public void add(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> all() {
        return messages;
    }
}
