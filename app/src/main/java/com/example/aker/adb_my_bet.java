package com.example.aker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class adb_my_bet extends FirebaseRecyclerAdapter<my_bet_model, adb_my_bet.myviewholder> {

    Context context;
    ArrayList<String> Names;
    String search;
    String phoneN;
    String userbirr;


    public adb_my_bet(Context context, @NonNull FirebaseRecyclerOptions<my_bet_model> options, String search, String phoneN) {
        super(options);
        this.context = context;
        this.search = search;
        this.phoneN = phoneN;

    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull my_bet_model my_bet_model) {
        holder.name.setText(my_bet_model.getBname());
        holder.birr.setText(my_bet_model.getBbirr());
        holder.all.setText(my_bet_model.getAll());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_my_bet, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView name, birr, all;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Bname1);
            birr = (TextView) itemView.findViewById(R.id.BBirr1);
            all = (TextView) itemView.findViewById(R.id.Bpeople1);


        }

    }

    public void checkname() {
        Query ref = FirebaseDatabase.getInstance().getReference().child("other").orderByChild("name").startAt(search).endAt(search + "\uf8ff");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.exists()) {
                            collectname((Map<String, Object>) dataSnapshot.getValue(), "other");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


    }


    private void collectname(Map<String, Object> users, final String name) {


        Names = new ArrayList<>();


        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            Names.add((String) singleUser.get("name"));
            Collections.sort(Names, String.CASE_INSENSITIVE_ORDER);


        }


    }


    public void userBirr(final Ubirr ubirr) {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(phoneN);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userbirr = snapshot.child("birr").getValue().toString();

                    ubirr.onCallbackd(userbirr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private interface Ubirr {
        void onCallbackd(String bbb);
    }

}
/**/