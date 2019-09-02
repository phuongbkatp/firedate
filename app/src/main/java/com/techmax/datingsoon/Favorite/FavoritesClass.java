package com.techmax.datingsoon.Favorite;

import java.util.Date;

public class FavoritesClass {

    String user_favorite;
    Date user_favorited;

    public FavoritesClass() {
    }

    public FavoritesClass(String user_favorite, Date user_favorited) {
        this.user_favorite = user_favorite;
        this.user_favorited = user_favorited;
    }

    public String getUser_favorite() {
        return user_favorite;
    }

    public void setUser_favorite(String user_favorite) {
        this.user_favorite = user_favorite;
    }

    public Date getUser_favorited() {
        return user_favorited;
    }

    public void setUser_favorited(Date user_favorited) {
        this.user_favorited = user_favorited;
    }
}
