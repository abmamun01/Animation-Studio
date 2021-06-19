package com.mamunsproject.animationstudio.Model;

public class SliderModel {

    String imageUrl;
    String id;
    String playlist;


    public SliderModel(String imageUrl, String id,String playlist) {
        this.imageUrl = imageUrl;
        this.id = id;
        this.playlist = playlist;
    }


    public String getplaylist() {
        return playlist;
    }

    public void setplaylist(String playlist) {
        this.playlist = playlist;
    }

    public SliderModel(String imageUrl) {
        this.imageUrl = imageUrl;

    }
    public SliderModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
