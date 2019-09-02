package com.techmax.datingsoon.Feeds;

import java.util.Date;

public class FeedsClass {
    Date feed_date;
    String feed_like;
    String feed_cover;
    String feed_user;
    String feed_show;
    String feed_uid;

    public FeedsClass() {
    }

    public FeedsClass(Date feed_date, String feed_like, String feed_cover, String feed_user, String feed_show, String feed_uid) {
        this.feed_date = feed_date;
        this.feed_like = feed_like;
        this.feed_cover = feed_cover;
        this.feed_user = feed_user;
        this.feed_show = feed_show;
        this.feed_uid = feed_uid;
    }

    public Date getFeed_date() {
        return feed_date;
    }

    public void setFeed_date(Date feed_date) {
        this.feed_date = feed_date;
    }

    public String getFeed_like() {
        return feed_like;
    }

    public void setFeed_like(String feed_like) {
        this.feed_like = feed_like;
    }

    public String getFeed_cover() {
        return feed_cover;
    }

    public void setFeed_cover(String feed_cover) {
        this.feed_cover = feed_cover;
    }

    public String getFeed_user() {
        return feed_user;
    }

    public void setFeed_user(String feed_user) {
        this.feed_user = feed_user;
    }

    public String getFeed_show() {
        return feed_show;
    }

    public void setFeed_show(String feed_show) {
        this.feed_show = feed_show;
    }

    public String getFeed_uid() {
        return feed_uid;
    }

    public void setFeed_uid(String feed_uid) {
        this.feed_uid = feed_uid;
    }
}
