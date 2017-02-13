package com.andreamazzarella.chat_application;

import com.andreamazzarella.chat_server.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RealChatProtocol implements ChatProtocol {

    private final static String FLAG_ANCHOR = "~";
    private final static String USERNAME_FLAG = FLAG_ANCHOR + "userName" + FLAG_ANCHOR;
    private final static String MESSAGE_CONTENT_FLAG = FLAG_ANCHOR + "messageContent" + FLAG_ANCHOR;
    private final static String EMPTY_FIELD_FLAG = "";

    private String messageContent = "";
    private String userName = "";

    @Override
    public ChatProtocol messageFrom(User user) {
        this.userName = user.getUserName();
        return this;
    }

    @Override
    public ChatProtocol addContent(String content) {
        this.messageContent = content;
        return this;
    }

    @Override
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

    @Override
    public String decodeUserName(String encodedMessage) {
        Pattern userNamePattern = Pattern.compile(".*" + USERNAME_FLAG + "([^~]*)" + ".*");
        Matcher matcher = userNamePattern.matcher(encodedMessage);
        matcher.matches();

        return matcher.group(1);
    }

    @Override
    public String decodeMessageContent(String encodedMessage) {
        Pattern messageContentPattern = Pattern.compile(".*" + MESSAGE_CONTENT_FLAG + "([^~]*)" + ".*");
        Matcher matcher = messageContentPattern.matcher(encodedMessage);
        matcher.matches();

        return matcher.group(1);
    }
}
