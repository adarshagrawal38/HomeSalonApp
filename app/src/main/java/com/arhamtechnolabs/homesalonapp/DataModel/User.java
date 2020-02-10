package com.arhamtechnolabs.homesalonapp.DataModel;

public class User {

    String id,name, gender, email, phone_no, user_waller, refer_code;

    public User(String id, String name, String gender, String email, String phone_no, String user_waller, String refer_code) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone_no = phone_no;
        this.user_waller = user_waller;
        this.refer_code = refer_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_waller() {
        return user_waller;
    }

    public void setUser_waller(String user_waller) {
        this.user_waller = user_waller;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
