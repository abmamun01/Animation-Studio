package com.mamunsproject.animationstudio.Model;

public class MainModel {
    String title;
    int thumbnail;

    public MainModel() {
    }
    public MainModel(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
