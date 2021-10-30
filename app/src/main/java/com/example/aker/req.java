package com.example.aker;


public class req {

    String req, birr;

    public req() {

    }

    public req(String req, String birr) {
        this.req = req;
        this.birr = birr;
    }


    public String getReqbirr() {
        return req;
    }

    public void setReqbirr(String reqbirr) {
        this.req = reqbirr;
    }

    public String getUserbirr() {
        return birr;
    }

    public void setUserbirr(String userbirr) {
        this.birr = userbirr;
    }
}
