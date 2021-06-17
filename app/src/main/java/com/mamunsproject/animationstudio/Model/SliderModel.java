package com.mamunsproject.animationstudio.Model;

public class SliderModel {

    String imageUrl;
    String id;

    public SliderModel(String imageUrl, String id) {
        this.imageUrl = imageUrl;
        this.id = id;
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
