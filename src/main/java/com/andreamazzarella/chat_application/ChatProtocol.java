package com.andreamazzarella.chat_application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatProtocol {

    private final static String FLAG_ANCHOR = "~";
    private final static String USERNAME_FLAG = FLAG_ANCHOR + "userName" + FLAG_ANCHOR;
    private final static String MESSAGE_CONTENT_FLAG = FLAG_ANCHOR + "messageContent" + FLAG_ANCHOR;

    public String encodeMessage(Message message) {
        String userName = encodeUserName(message);
        String messageContent = encodeMessageContent(message);
        return userName + messageContent;
    }

    private String encodeMessageContent(Message message) {
        return MESSAGE_CONTENT_FLAG + message.getContent();
    }

    private String encodeUserName(Message message) {
        return USERNAME_FLAG + message.getUser().getUserName();  // Demeter?
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
