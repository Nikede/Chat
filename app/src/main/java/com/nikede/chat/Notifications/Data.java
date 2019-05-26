package com.nikede.chat.Notifications;

public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sented;
    private String key;

    public Data(String user, int icon, String body, String title, String sented, String key) {
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sented = sented;
        this.key = key;
    }

    public Data() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
