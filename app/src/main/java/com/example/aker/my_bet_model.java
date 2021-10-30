package com.example.aker;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class my_bet_model {

    String Bname, Bbirr, All;


    my_bet_model() {

    }

    public my_bet_model(String Bname, String Bbirr, String All) {
        this.Bname = Bname;
        this.Bbirr = Bbirr;
        this.All = All;

    }

    public String getBname() {
        return Bname;
    }

    public void setBname(String bname) {
        Bname = bname;
    }

    public String getBbirr() {
        return Bbirr;
    }

    public void setBbirr(String bbirr) {
        Bbirr = bbirr;
    }

    public String getAll() {
        return All;
    }

    public void setAll(String all) {
        All = all;
    }


}

