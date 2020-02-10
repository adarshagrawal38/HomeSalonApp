package com.arhamtechnolabs.homesalonapp.DataModel;

import androidx.annotation.NonNull;

public class CartData {

    int id;
    String categoryName;
    String features;
    String amount;
    String img;
    int timeRequired;
    int numberOfTime;
    int numberOfProducts;
    int amountOfProducts;



    public int getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfTime() {
        return numberOfTime;
    }

    public void incrementNumberofTime() {
        numberOfTime++;
    }

    public void setNumberOfTime(int numberOfTime) {
        this.numberOfTime = numberOfTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImg() {
        return img;
    }

    @NonNull
    @Override
    public String toString() {
        String s = id+ " " + categoryName+" " + features + " " + amount + " " + img +  " NumberOfTimes" + numberOfTime;
        return s;
    }

    public void setImg(String img) {
        this.img = img;
        numberOfTime=0;
    }

   /* public CartData(int id, String categoryName, String features, String amount, String img, int rime) {
        this.id = id;
        this.categoryName = categoryName;
        this.features = features;
        this.amount = amount;
        this.img = img;
        this.timeRequired = rime;
        numberOfTime = 1;
    }*/

    /*public CartData(int id, String categoryName, String features, String amount, String img, int timeRequired, int numberOfTime) {
        this.id = id;
        this.categoryName = categoryName;
        this.features = features;
        this.amount = amount;
        this.img = img;
        this.timeRequired = timeRequired;
        this.numberOfTime = numberOfTime;
    }*/

    public CartData(int id, String categoryName, String features, String amount, String img, int timeRequired, int numberOfTime, int numberOfProducts, int amountOfProducts) {
        this.id = id;
        this.categoryName = categoryName;
        this.features = features;
        this.amount = amount;
        this.img = img;
        this.timeRequired = timeRequired;
        this.numberOfTime = numberOfTime;
        this.numberOfProducts = numberOfProducts;
        this.amountOfProducts = amountOfProducts;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public int getTotalAmount() {
        return Integer.valueOf(amount) * numberOfTime + Integer.valueOf(amountOfProducts) * numberOfProducts *0;
    }


    public boolean checkEquals(CartData data) {

        if (categoryName.equals(data.getCategoryName()) && amount.equals(data.getAmount()))
            return true;

        return false;

    }
    public void merger(CartData data) {

        numberOfTime+=data.getNumberOfTime();

    }


    public CartData(){}
}
