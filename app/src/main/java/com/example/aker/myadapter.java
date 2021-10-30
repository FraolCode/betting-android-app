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


public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder> {

    Context context;
    ArrayList<String> Names;
    String search;
    String phoneN;
    String userbirr;
    String birrfromuserDB;
    DatabaseReference reference;
    boolean ch = false;


    int aaa, bbb, ccc, ddd;

    public String bbirr, min, max;

    String variableToStoreStringInClass;

    public myadapter(Context context, @NonNull FirebaseRecyclerOptions<model> options, String search, String phoneN) {
        super(options);
        this.context = context;
        this.search = search;
        this.phoneN = phoneN;


    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        holder.name.setText(model.getName());
        holder.birr.setText(model.getBirr());
        String nameF = model.getName();
        String pplin = model.getPplin();
        String all = model.getAll();
        String full = pplin + "/" + all;
        String passwords = model.getPassword();

        checkname();


        holder.amount.setText(full);


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView name, birr, amount;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            birr = (TextView) itemView.findViewById(R.id.coursetext);
            amount = (TextView) itemView.findViewById(R.id.emailtext);
            userBirr(new Ubirr() {
                @Override
                public void onCallbackd(String bbb) {
                    birrfromuserDB = bbb;
                }
            });

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            checkname();

            int postion = getAdapterPosition();
            final String namefromArray = Names.get(postion);
            ch = true;
            DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("other").child(namefromArray);
            redd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        max = snapshot.child("all").getValue().toString();
                        min = snapshot.child("pplin").getValue().toString();
                        bbirr = snapshot.child("birr").getValue().toString();

                        regForBet(namefromArray, max, min, bbirr);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


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


    private interface FirebaseCallback {
        void onCallback(String a, String b, String c);
    }

    private interface Ubirr {
        void onCallbackd(String bbb);
    }

    public void regForBet(final String BetName, String Dmax, String Dmin, String Dbirr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.button_alert_dialog, null);

        final int intDmax = Integer.parseInt(Dmax);
        final int intDmin = Integer.parseInt(Dmin);
        final int intDbirr = Integer.parseInt(Dbirr);
        final int intDUbirr = Integer.parseInt(birrfromuserDB);

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        Button acceptButton = view.findViewById(R.id.acceptButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        TextView tv = view.findViewById(R.id.tv);

        if (ch == true) {
            tv.setText("Do you Accept to Bet ");
            alertDialog.show();
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                    if (intDmax > intDmin) {
                        //
                        if (intDUbirr >= intDbirr) {
                            int finalBirr = intDUbirr - intDbirr;
                            String finalBirrS = String.valueOf(finalBirr);
                            int accc = intDmin + 1;
                            String minS = String.valueOf(accc);

                            reference = FirebaseDatabase.getInstance().getReference().child("ppl").child(BetName).push();
                            reference.child("phone").setValue(phoneN);
                            reference = FirebaseDatabase.getInstance().getReference().child("req").child(phoneN);
                            reference.child("birr").setValue(finalBirrS);
                            reference = FirebaseDatabase.getInstance().getReference().child("other").child(BetName);
                            reference.child("pplin").setValue(minS);

                            Toast.makeText(context, "Good Luck", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(context, "Sorry, You don't have that much birr", Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "Add some birr and try again", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(context, "sorry,its Full ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Try another or create Another", Toast.LENGTH_SHORT).show();
                    }


                    alertDialog.cancel();

                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                }
            });
            ch = false;
        }


    }
}
/**/