package com.techmax.datingsoon.Chats;

import java.util.Date;

public class ChatUserClass {

    Date user_datesent;
    String user_receiver;
    String user_sender;
    String user_message;
    String user_unread;

    public ChatUserClass() {
    }

    public ChatUserClass(Date user_datesent, String user_receiver, String user_sender, String user_message, String user_unread) {
        this.user_datesent = user_datesent;
        this.user_receiver = user_receiver;
        this.user_sender = user_sender;
        this.user_message = user_message;
        this.user_unread = user_unread;
    }

    public Date getUser_datesent() {
        return user_datesent;
    }

    public void setUser_datesent(Date user_datesent) {
        this.user_datesent = user_datesent;
    }

    public String getUser_receiver() {
        return user_receiver;
    }

    public void setUser_receiver(String user_receiver) {
        this.user_receiver = user_receiver;
    }

    public String getUser_sender() {
        return user_sender;
    }

    public void setUser_sender(String user_sender) {
        this.user_sender = user_sender;
    }

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public String getUser_unread() {
        return user_unread;
    }

    public void setUser_unread(String user_unread) {
        this.user_unread = user_unread;
    }
}
