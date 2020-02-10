package com.arhamtechnolabs.homesalonapp.DataModel;

public class BookingAppoinementModel {

    String booking_id, user_id,date, booking_time,address, SubSubCat_ID, amount, payment_mode;

    public BookingAppoinementModel(String booking_id, String user_id, String date, String booking_time, String address, String subSubCat_ID, String amount, String payment_mode) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.date = date;
        this.booking_time = booking_time;
        this.address = address;
        SubSubCat_ID = subSubCat_ID;
        this.amount = amount;
        this.payment_mode = payment_mode;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
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

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
