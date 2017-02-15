package com.andreamazzarella.chat_server;


import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLMessageRepository implements MessageRepository {

    private final PGSimpleDataSource source;

    SQLMessageRepository() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("chat_server");
        source.setUser("Andrea");
        source.setPortNumber(5432);
        this.source = source;
    }

    @Override
    public void add(Message message) {
        String messageContent = message.getContent();
        String userName = message.getUserName().orElse("");
        String addUserSQL = "INSERT INTO MESSAGES VALUES ('"+messageContent+"','"+userName+"');";

        try (Connection connection = source.getConnection()) { // not sure if I should connect once instead of reastablishing a connection every time?
            Statement statement = connection.createStatement();
            statement.execute(addUserSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> all() {
        List<Message> allMessages = new ArrayList<>();

        try (Connection connection = source.getConnection()) { // not sure if I should connect once instead of reastablishing a connection every time?
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MESSAGES;");

            if (resultSet.next()) {
                System.out.println(resultSet.getRow());
                Message message = new Message(Optional.of(resultSet.getString(2)), resultSet.getString(1));
                allMessages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allMessages;
    }
}
