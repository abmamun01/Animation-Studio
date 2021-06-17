package com.mamunsproject.animationstudio.Model;

public class SliderModel {

    String imageUrl;

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
}
