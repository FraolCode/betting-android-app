package com.example.aker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.tv_name);
        ImageView imageView = findViewById(R.id.iv_full);

        String name = getIntent().getStringExtra("n");
        String image = getIntent().getStringExtra("u");
        textView.setText(name);
        Picasso.get().load(image).into(imageView);
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();


    }
}