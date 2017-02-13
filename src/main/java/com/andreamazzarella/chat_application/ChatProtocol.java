package com.andreamazzarella.chat_application;

import com.andreamazzarella.chat_server.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatProtocol {

    private final static String FLAG_ANCHOR = "~";
    private final static String USERNAME_FLAG = FLAG_ANCHOR + "userName" + FLAG_ANCHOR;
    private final static String MESSAGE_CONTENT_FLAG = FLAG_ANCHOR + "messageContent" + FLAG_ANCHOR;
    private final static String EMPTY_FIELD_FLAG = "";

    private String messageContent = "";
    private String userName = "";

    public ChatProtocol messageFrom(User user) {
        this.userName = user.getUserName();
        return this;
    }

    public ChatProtocol addContent(String content) {
        this.messageContent = content;
        return this;
    }

    public String encodeMessage() {
        String userName = encodeUserName();
        String messageContent = encodeMessageContent();

        resetFields();

        return userName + messageContent;
    }

    private void resetFields() {
        messageContent = EMPTY_FIELD_FLAG;
        userName = EMPTY_FIELD_FLAG;
    }

    private String encodeMessageContent() {
        return MESSAGE_CONTENT_FLAG + messageContent;
    }


    private String encodeUserName() {
        return USERNAME_FLAG + userName;
    }

    public String decodeUserName(String encodedMessage) {
        return decode(encodedMessage, USERNAME_FLAG);
    }

    public String decodeMessageContent(String encodedMessage) {
        return decode(encodedMessage, MESSAGE_CONTENT_FLAG);
    }

    private String decode(String encodedMessage, String field) {
        Pattern pattern = Pattern.compile(".*" + field + "([^~]*)" + ".*");
        Matcher matcher = pattern.matcher(encodedMessage);
        matcher.matches();

        return matcher.group(1);
    }
}
