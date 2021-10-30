package com.example.aker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class ProfileEditer extends AppCompatActivity {


    String FullPhoneNumber, PhoneWithOutCCode;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String fullphone = "text";
    public static final String shortphoneT = "text2";

    EditText name, age, username;
    Button button;
    String myDataFromActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editer);

        name = findViewById(R.id.name_et_cp);
        age = findViewById(R.id.age_et_cp);
        username = findViewById(R.id.bio_et_cp);
        button = findViewById(R.id.save_profile_btn_cp);


        FullPhoneNumber = getIntent().getStringExtra("FullPhoneNumber");
        PhoneWithOutCCode = getIntent().getStringExtra("PhoneWithOutCCode");


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            savedata();
        }
        lodedata();

        if (FullPhoneNumber.isEmpty()) {
            getdata();
            FullPhoneNumber = myDataFromActivity;
            savedata();
            lodedata();


        }
        Toast.makeText(getApplicationContext(), FullPhoneNumber, Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });


    }

    public void savedata() {
        SharedPreferences sharedPreferencess = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencess.edit();

        editor.putString(fullphone, FullPhoneNumber);
        editor.putString(shortphoneT, PhoneWithOutCCode);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editora = prefs.edit();
        editora.putBoolean("firstStart", false);
        editora.apply();
        editor.apply();


    }

    public void lodedata() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        FullPhoneNumber = sharedPreferences.getString(fullphone, "");
        PhoneWithOutCCode = sharedPreferences.getString(shortphoneT, "");

    }

    public void update() {
        if (!(name.getText().toString().isEmpty()) && !(age.getText().toString().isEmpty()) && !(username.getText().toString().isEmpty())) {
            String agee = age.getText().toString();
            long longage = Long.parseLong(agee);
            if (longage > 18) {
                String nameS = name.getText().toString();
                String ageS = age.getText().toString();
                String UsernameS = username.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("profile").child(FullPhoneNumber);
                reference.child("name").setValue(nameS);
                reference.child("username").setValue(UsernameS);
                reference.child("age").setValue(ageS);
                req();
                iSexist();


            } else {
                Toast.makeText(getApplicationContext(), "You have to me more than 18 to use this", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getApplicationContext(), "You Have to Enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    public void getdata() {
        DashBorde activity = (DashBorde) getApplicationContext();
        myDataFromActivity = activity.getMyData();

    }

    public void iSexist() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("profile").child(FullPhoneNumber);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Intent intent = new Intent(ProfileEditer.this, DashBorde.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("FullPhoneNumber", FullPhoneNumber);
                    intent.putExtra("PhoneWithOutCCode", PhoneWithOutCCode);
                    intent.putExtra("checker", "checker");
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void req(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                }
                else {
                    CreateReq();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void CreateReq(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
        reference.child("birr").setValue("0");
        reference.child("req").setValue("0");
    }

}