package com.arhamtechnolabs.homesalonapp.DataModel;

public class LoginDetails {

    String phone_no, status;

    public LoginDetails(String phone_no, String status) {
        this.phone_no = phone_no;
        this.status = status;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
