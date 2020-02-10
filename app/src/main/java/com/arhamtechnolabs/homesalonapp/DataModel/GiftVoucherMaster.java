package com.arhamtechnolabs.homesalonapp.DataModel;

public class GiftVoucherMaster {

    String voucher_img, voucher_price, description, note;

    public GiftVoucherMaster(String voucher_img, String voucher_price, String description, String note) {
        this.voucher_img = voucher_img;
        this.voucher_price = voucher_price;
        this.description = description;
        this.note = note;
    }



    public String getVoucher_img() {
        return voucher_img;
    }

    public void setVoucher_img(String voucher_img) {
        this.voucher_img = voucher_img;
    }

    public String getVoucher_price() {
        return voucher_price;
    }

    public void setVoucher_price(String voucher_price) {
        this.voucher_price = voucher_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
