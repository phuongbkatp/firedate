package com.techmax.datingsoon.Loves;

import java.util.Date;

public class LovesClass {

    String user_loves;
    Date user_loved;

    public LovesClass() {
    }

    public LovesClass(String user_loves, Date user_loved) {
        this.user_loves = user_loves;
        this.user_loved = user_loved;
    }

    public String getUser_loves() {
        return user_loves;
    }

    public void setUser_loves(String user_loves) {
        this.user_loves = user_loves;
    }

    public Date getUser_loved() {
        return user_loved;
    }

    public void setUser_loved(Date user_loved) {
        this.user_loved = user_loved;
    }
}
