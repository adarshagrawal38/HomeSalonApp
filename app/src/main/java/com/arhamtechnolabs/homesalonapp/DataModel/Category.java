package com.arhamtechnolabs.homesalonapp.DataModel;

public class Category {

    private String catID, cat_img, cat_name, cat_city, cat_ratePerMin;

    public Category(String catID, String cat_img, String cat_name, String cat_city, String cat_ratePerMin) {
        this.cat_img = cat_img;
        this.cat_name = cat_name;
        this.cat_city = cat_city;
        this.cat_ratePerMin = cat_ratePerMin;
        this.catID = catID;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_city() {
        return cat_city;
    }

    public void setCat_city(String cat_city) {
        this.cat_city = cat_city;
    }

    public String getCat_ratePerMin() {
        return cat_ratePerMin;
    }

    public void setCat_ratePerMin(String cat_ratePerMin) {
        this.cat_ratePerMin = cat_ratePerMin;
    }
}
