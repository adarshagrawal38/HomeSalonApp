package com.arhamtechnolabs.homesalonapp.DataModel;

public class BannerMaster {

    String img;

    public BannerMaster(String imageURl) {
        this.img = imageURl;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
