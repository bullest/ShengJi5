package com.bullest.shengji5.data;

import java.util.Date;

/**
 * Created by yunfezhang on 2/12/18.
 */

public class Message {
    private String message;
    private boolean fromSystem;

    private Date date;

    public Message() {
    }

    public Message(String message, boolean isFromSystem, Date date) {
        this.message = message;
        this.fromSystem = isFromSystem;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(boolean fromSystem) {
        fromSystem = fromSystem;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
