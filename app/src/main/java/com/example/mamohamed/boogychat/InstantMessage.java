package com.example.mamohamed.boogychat;

/**
 * Created by mamohamed on 3/23/2018.
 */

public class InstantMessage {
    private String message;
    private String author;
    public InstantMessage(String message, String author){

        this.message = message;
        this.author = author;

    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public InstantMessage() {



    }
}
