package com.example.aker;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    ProgressBar progressBar;
    DatabaseReference reference;
    int value;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setDetails(Context context, String image, String title, String birr) {

        ImageView imageView = itemView.findViewById(R.id.iv_item);
        TextView textView = itemView.findViewById(R.id.tv_item);
        cardView = itemView.findViewById(R.id.cv_item);
        textView1 = itemView.findViewById(R.id.textDesc);
        textView2 = itemView.findViewById(R.id.textppl);
        progressBar = itemView.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        abc(title);

        Picasso.get().load(image).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
        textView.setText(title);

        textView1.setText("bet birr " + birr + " to win " + title);
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.setAnimation(animation);
    }

    public void abc(String title) {

        reference = FirebaseDatabase.getInstance().getReference().child("Item").child(title);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    value = (int) snapshot.getChildrenCount();
                    textView2.setText("people: " + Integer.toString(value));
                } else {
                    textView2.setText("people: 0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setDetailsbirr(Context context, String reqbirr, String userbirr) {
        textView3 = itemView.findViewById(R.id.diptxt);
        textView4 = itemView.findViewById(R.id.reqtxt);
        textView3.setText(userbirr);
        textView4.setText(reqbirr);

    }
}
