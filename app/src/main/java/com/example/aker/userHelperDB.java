package com.example.aker;

public class userHelperDB {
    String phone, amount;

    public userHelperDB() {

    }

    public userHelperDB(String phone, String amount) {
        this.phone = phone;
        this.amount = amount;
    }

    public userHelperDB(String id) {
        this.phone = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
