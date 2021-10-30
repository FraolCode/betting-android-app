package com.example.aker;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class model {

    String name, birr, password;
    String all, pplin;


    model() {

    }

    public model(String name, String birr, String password, String all, String pplin) {
        this.name = name;
        this.birr = birr;
        this.password = password;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getPplin() {
        return pplin;
    }

    public void setPplin(String pplin) {
        this.pplin = pplin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirr() {
        return birr;
    }

    public void setBirr(String birr) {
        this.birr = birr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

