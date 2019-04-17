package com.example.kamil.androidsoapclient2.callingWebService.dataModel;

import android.os.Parcel;
import android.os.Parcelable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Message implements Parcelable {
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
    protected Message(Parcel in) {
        id = in.readLong();
        messageContent = in.readString();
        creationDate = in.readString();
        author = in.readString();
    }
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }
        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(messageContent);
        dest.writeString(creationDate);
        dest.writeString(author);
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