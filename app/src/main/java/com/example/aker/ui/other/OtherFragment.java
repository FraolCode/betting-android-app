package com.example.aker.ui.other;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aker.DashBorde;
import com.example.aker.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OtherFragment extends Fragment {

    private OtherViewModel slideshowViewModel;

    private RecyclerView recyclerView;
    DatabaseReference reference; // Create object of the
    long count;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;
    int bet;
    int fullbet;
    TextView numb1, people1;
    TextView numb1a, people1a;
    TextView numb1b, people1b;
    TextView numb1c, people1c;
    TextView numb1d, people1d;
    TextView numb1e, people1e;
    CardView cardView1, cardView5, cardView10, cardView25, cardView50, cardView100;
    String myDataFromActivity;
    TextView notbet;
    String ch = "0";


    String phone;
    // Firebase Realtime Database


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(OtherViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_other, container, false);

        numb1 = root.findViewById(R.id.numb1);
        people1 = root.findViewById(R.id.people1);
        numb1a = root.findViewById(R.id.numb1a);
        people1a = root.findViewById(R.id.people1a);
        numb1b = root.findViewById(R.id.numb1b);
        people1b = root.findViewById(R.id.people1b);
        numb1c = root.findViewById(R.id.numb1c);
        people1c = root.findViewById(R.id.people1c);
        numb1d = root.findViewById(R.id.numb1d);
        people1d = root.findViewById(R.id.people1d);
        numb1e = root.findViewById(R.id.numb1e);
        people1e = root.findViewById(R.id.people1e);
        cardView1 = root.findViewById(R.id.b1);
        cardView5 = root.findViewById(R.id.b5);
        cardView10 = root.findViewById(R.id.b10);
        cardView25 = root.findViewById(R.id.b25);
        cardView50 = root.findViewById(R.id.b50);
        cardView100 = root.findViewById(R.id.b100);
        notbet = root.findViewById(R.id.nobet);

        getdata();
        bet();
        abc();


        return root;
    }

    public void bet() {

        reference = FirebaseDatabase.getInstance().getReference().child("1");
        reference.keepSynced(true);
        Query query = reference.orderByChild("phone").equalTo(phone);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    numb1.setText(Integer.toString(value));
                    cardView1.setVisibility(View.VISIBLE);
                    notbet.setVisibility(View.GONE);
                } else {
                    cardView1.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("3");
        reference.keepSynced(true);
        Query query1 = reference.orderByChild("phone").equalTo(phone);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    numb1a.setText(Integer.toString(value));
                    cardView5.setVisibility(View.VISIBLE);
                    notbet.setVisibility(View.GONE);
                    ch = "s";
                } else {
                    cardView5.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("5");
        reference.keepSynced(true);
        Query query2 = reference.orderByChild("phone").equalTo(phone);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    numb1b.setText(Integer.toString(value));
                    cardView10.setVisibility(View.VISIBLE);
                    notbet.setVisibility(View.GONE);
                    ch = "s";
                } else {
                    cardView10.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("10");
        reference.keepSynced(true);
        Query query3 = reference.orderByChild("phone").equalTo(phone);
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    numb1c.setText(Integer.toString(value));
                    notbet.setVisibility(View.GONE);
                    ch = "s";

                } else {
                    cardView25.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("15");
        reference.keepSynced(true);
        Query query4 = reference.orderByChild("phone").equalTo(phone);
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    numb1d.setText(Integer.toString(value));
                    cardView50.setVisibility(View.VISIBLE);
                    notbet.setVisibility(View.GONE);
                    ch = "s";
                } else {
                    cardView50.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("20");
        reference.keepSynced(true);
        Query query5 = reference.orderByChild("phone").equalTo(phone);
        query5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    numb1e.setText(Integer.toString(value));
                    cardView100.setVisibility(View.VISIBLE);
                    notbet.setVisibility(View.GONE);
                    ch = "s";
                } else {
                    cardView100.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void abc() {

        reference = FirebaseDatabase.getInstance().getReference().child("1");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    people1.setText(Integer.toString(value));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("3");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    people1a.setText(Integer.toString(value));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("5");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    people1b.setText(Integer.toString(value));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("10");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    people1c.setText(Integer.toString(value));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("15");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    people1d.setText(Integer.toString(value));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("20");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int value = (int) snapshot.getChildrenCount();
                    people1e.setText(Integer.toString(value));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getdata() {
        DashBorde activity = (DashBorde) getActivity();
        phone = activity.getMyData();

    }


}