package com.example.kamil.androidsoapclient2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Message {
    private long id;
    private String messageContent;
    private String creationDate;
    private String author;
    public Message(){}
    public Message(long id, String messageContent, String author) {
        this.id = id;
        this.messageContent = messageContent;
        this.creationDate = LocalDateTime.now().toString();
        this.author = author;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMessageContent() {
        return messageContent;
    }
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        try{
            this.creationDate = LocalDateTime.parse(creationDate).toString();
        }catch (DateTimeParseException e){
            this.creationDate = LocalDateTime.now().toString();
        }
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

}
