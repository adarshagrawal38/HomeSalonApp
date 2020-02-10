package com.arhamtechnolabs.homesalonapp.DataModel;

import androidx.annotation.NonNull;

public class SubSubCategory {

    String SubSubCat_ID, SubSubCat_Name, SubSubCat_price, SubSubCat_timeInMin, SubSubCat_discount, SubSubCat_features,subsubcategory_image;
    String subSubCatProductCost, SubSubuCatSuggestions;

   /* public SubSubCategory(String subSubCat_ID, String subSubCat_Name, String subSubCat_price, String subSubCat_timeInMin, String subSubCat_discount, String subSubCat_features, String subsubcategory_image) {
        SubSubCat_ID = subSubCat_ID;
        SubSubCat_Name = subSubCat_Name;
        SubSubCat_price = subSubCat_price;
        SubSubCat_timeInMin = subSubCat_timeInMin;
        SubSubCat_discount = subSubCat_discount;
        SubSubCat_features = subSubCat_features;
        this.subsubcategory_image = subsubcategory_image;
    }*/

    public SubSubCategory(String subSubCat_ID, String subSubCat_Name, String subSubCat_price, String subSubCat_timeInMin, String subSubCat_discount, String subSubCat_features, String subsubcategory_image, String subSubCatProductCost, String subSubuCatSuggestions) {
        SubSubCat_ID = subSubCat_ID;
        SubSubCat_Name = subSubCat_Name;
        SubSubCat_price = subSubCat_price;
        SubSubCat_timeInMin = subSubCat_timeInMin;
        SubSubCat_discount = subSubCat_discount;
        SubSubCat_features = subSubCat_features;
        this.subsubcategory_image = subsubcategory_image;
        this.subSubCatProductCost = subSubCatProductCost;
        SubSubuCatSuggestions = subSubuCatSuggestions;
    }

    @NonNull
    @Override
    public String toString() {
        String s = SubSubCat_ID+" "+ SubSubCat_Name+" service_cat : "+ SubSubCat_price+" "+ SubSubCat_timeInMin+" "+ SubSubCat_discount+" "+ SubSubCat_features+" "+subsubcategory_image +" product cost: " + subSubCatProductCost+" Suggestions: "+ SubSubuCatSuggestions;
        return s;
    }

    public String getSubSubCatProductCost() {
        return subSubCatProductCost;
    }

    public void setSubSubCatProductCost(String subSubCatProductCost) {
        this.subSubCatProductCost = subSubCatProductCost;
    }

    public String getSubSubuCatSuggestions() {
        return SubSubuCatSuggestions;
    }

    public void setSubSubuCatSuggestions(String subSubuCatSuggestions) {
        SubSubuCatSuggestions = subSubuCatSuggestions;
    }

    public String getSubSubCat_ID() {
        return SubSubCat_ID;
    }

    public void setSubSubCat_ID(String subSubCat_ID) {
        SubSubCat_ID = subSubCat_ID;
    }

    public String getSubSubCat_Name() {
        return SubSubCat_Name;
    }

    public void setSubSubCat_Name(String subSubCat_Name) {
        SubSubCat_Name = subSubCat_Name;
    }

    public String getSubSubCat_price() {
        return SubSubCat_price;
    }

    public void setSubSubCat_price(String subSubCat_price) {
        SubSubCat_price = subSubCat_price;
    }

    public String getSubSubCat_timeInMin() {
        return SubSubCat_timeInMin;
    }

    public void setSubSubCat_timeInMin(String subSubCat_timeInMin) {
        SubSubCat_timeInMin = subSubCat_timeInMin;
    }

    public String getSubSubCat_discount() {
        return SubSubCat_discount;
    }

    public void setSubSubCat_discount(String subSubCat_discount) {
        SubSubCat_discount = subSubCat_discount;
    }

    public String getSubSubCat_features() {
        return SubSubCat_features;
    }

    public void setSubSubCat_features(String subSubCat_features) {
        SubSubCat_features = subSubCat_features;
    }

    public String getSubsubcategory_image() {
        return subsubcategory_image;
    }

    public void setSubsubcategory_image(String subsubcategory_image) {
        this.subsubcategory_image = subsubcategory_image;
    }
}
