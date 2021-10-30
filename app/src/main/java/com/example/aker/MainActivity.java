package com.example.aker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//1
public class MainActivity extends AppCompatActivity {

    private static int spl = 5000;
    Animation topAnim, bottomAnim;

    TextView txt1, txt2;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String Text = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        anim();


    }


    public void anim() {


        topAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation);


        txt1 = findViewById(R.id.Bet_Game);
        txt2 = findViewById(R.id.textView2);


        txt1.setAnimation(bottomAnim);
        txt2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(txt1, "logo_img");
                pairs[1] = new Pair<View, String>(txt1, "logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());

            }
        }, 5000);


    }


}