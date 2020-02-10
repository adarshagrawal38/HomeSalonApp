package com.arhamtechnolabs.homesalonapp.DataModel;

public class Cart {

    String sub_sub_cat_id, sub_sub_cat_name ,sub_sub_cat_amount,sub_sub_cat_features,sub_sub_cat_image;

    public Cart() {
    }

    public Cart(String sub_sub_cat_id, String sub_sub_cat_name, String sub_sub_cat_amount, String sub_sub_cat_features, String sub_sub_cat_image) {
        this.sub_sub_cat_id = sub_sub_cat_id;
        this.sub_sub_cat_name = sub_sub_cat_name;
        this.sub_sub_cat_amount = sub_sub_cat_amount;
        this.sub_sub_cat_features = sub_sub_cat_features;
        this.sub_sub_cat_image = sub_sub_cat_image;
    }

    public String getSub_sub_cat_id() {
        return sub_sub_cat_id;
    }

    public void setSub_sub_cat_id(String sub_sub_cat_id) {
        this.sub_sub_cat_id = sub_sub_cat_id;
    }

    public String getSub_sub_cat_name() {
        return sub_sub_cat_name;
    }

    public void setSub_sub_cat_name(String sub_sub_cat_name) {
        this.sub_sub_cat_name = sub_sub_cat_name;
    }

    public String getSub_sub_cat_amount() {
        return sub_sub_cat_amount;
    }

    public void setSub_sub_cat_amount(String sub_sub_cat_amount) {
        this.sub_sub_cat_amount = sub_sub_cat_amount;
    }

    public String getSub_sub_cat_features() {
        return sub_sub_cat_features;
    }

    public void setSub_sub_cat_features(String sub_sub_cat_features) {
        this.sub_sub_cat_features = sub_sub_cat_features;
    }

    public String getSub_sub_cat_image() {
        return sub_sub_cat_image;
    }

    public void setSub_sub_cat_image(String sub_sub_cat_image) {
        this.sub_sub_cat_image = sub_sub_cat_image;
    }
}
