package com.arhamtechnolabs.homesalonapp.DataModel;

public class Booking {

    String user_id, date, SubSubCat_ID, amount, payment_mode;

    public Booking(String user_id, String date, String subSubCat_ID, String amount, String payment_mode) {
        this.user_id = user_id;
        this.date = date;
        SubSubCat_ID = subSubCat_ID;
        this.amount = amount;
        this.payment_mode = payment_mode;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubSubCat_ID() {
        return SubSubCat_ID;
    }

    public void setSubSubCat_ID(String subSubCat_ID) {
        SubSubCat_ID = subSubCat_ID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }
}
