package com.example.sendtivity.Class;

public class MessageClass {
    private String message;
    private String type;
    private String from;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    private String to;
    private String messageID;

    public MessageClass(String message, String type, String from, String to, String messageID) {
        this.message = message;
        this.type = type;
        this.from = from;
        this.to = to;
        this.messageID = messageID;
    }
    public MessageClass(){}
}
