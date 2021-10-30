package com.example.aker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aker.ui.other.OtherViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Allbet extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    TextView allbirr, allbirra, allbirrb, allbirrc, allbirrd, allbirre;
    CardView cardView1, cardView5, cardView10, cardView25, cardView50, cardView100;
    String myDataFromActivity;
    String phone;


    private String mParam1;
    private String mParam2;

    public Allbet() {

    }

    public static Allbet newInstance(String param1, String param2) {
        Allbet fragment = new Allbet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_allbet, container, false);
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
        allbirr = root.findViewById(R.id.allbirr1);
        allbirra = root.findViewById(R.id.allbirr1a);
        allbirrb = root.findViewById(R.id.allbirr1b);
        allbirrc = root.findViewById(R.id.allbirr1c);
        allbirrd = root.findViewById(R.id.allbirr1d);
        allbirre = root.findViewById(R.id.allbirr1e);

        getdata();
        bet();
        abc();


        return root;


    }

    public void getdata() {
        DashBorde activity = (DashBorde) getActivity();
        phone = activity.getMyData();

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
                    allbirr.setText(value + " Birr");
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
                    int all = value * 3;
                    String allS = String.valueOf(all);
                    allbirra.setText(allS + " Birr");

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
                    int all = value * 5;
                    String allS = String.valueOf(all);
                    allbirrb.setText(allS + " Birr");

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
                    int all = value * 10;
                    String allS = String.valueOf(all);
                    allbirrc.setText(allS + " Birr");

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
                    int all = value * 15;
                    String allS = String.valueOf(all);
                    allbirrd.setText(allS + " Birr");

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
                    int all = value * 20;
                    String allS = String.valueOf(all);
                    allbirre.setText(allS + " Birr");

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}