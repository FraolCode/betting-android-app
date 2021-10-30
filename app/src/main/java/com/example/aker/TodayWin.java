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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class TodayWin extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    DatabaseReference reference; // Create object of the
    long count;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;
    int bet;
    int fullbet;
    TextView numb1, people1, time1;
    TextView numb1a, people1a, time1a;
    TextView numb1b, people1b, time1b;
    TextView numb1c, people1c, time1c;
    TextView numb1d, people1d, time1d;
    TextView numb1e, people1e, time1e;
    CardView cardView1, cardView5, cardView10, cardView25, cardView50, cardView100;
    String myDataFromActivity;
    String phone;

    public TodayWin() {
        // Required empty public constructor
    }

    public static TodayWin newInstance(String param1, String param2) {
        TodayWin fragment = new TodayWin();
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
        View root = inflater.inflate(R.layout.fragment_today_win, container, false);

        numb1 = root.findViewById(R.id.numb1);
        people1 = root.findViewById(R.id.people1);
        time1 = root.findViewById(R.id.time1);
        time1a = root.findViewById(R.id.time1a);
        time1b = root.findViewById(R.id.time1b);
        time1c = root.findViewById(R.id.time1c);
        time1d = root.findViewById(R.id.time1d);
        time1e = root.findViewById(R.id.time1e);
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

        getdata();
        time();
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

    public void time() {

        reference = FirebaseDatabase.getInstance().getReference().child("1").child("time");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String a = snapshot.getValue().toString();
                    time1.setText(a);
                    cardView1.setVisibility(View.VISIBLE);
                } else {
                    cardView1.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("3").child("time");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String a = snapshot.getValue().toString();
                    time1a.setText(a);
                    cardView5.setVisibility(View.VISIBLE);
                } else {
                    cardView5.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("5").child("time");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String a = snapshot.getValue().toString();
                    time1b.setText(a);
                    cardView10.setVisibility(View.VISIBLE);
                } else {
                    cardView10.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("10").child("time");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String a = snapshot.getValue().toString();
                    time1c.setText(a);
                    cardView25.setVisibility(View.VISIBLE);
                } else {
                    cardView25.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("15").child("time");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String a = snapshot.getValue().toString();
                    time1d.setText(a);
                    cardView50.setVisibility(View.VISIBLE);
                } else {
                    cardView50.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("20").child("time");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String a = snapshot.getValue().toString();
                    time1e.setText(a);
                    cardView100.setVisibility(View.VISIBLE);
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
/*    public void timedb(final String birr){
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child(birr).child("time");
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String timefromdb = snapshot.child("time").getValue().toString();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
   /* public void settime(String birr, String time){
        if(birr.equals("1")){
            time1.setText(time);
        }
        else if(birr.equals("3")){
            time1a.setText(time);
        }
        else if(birr.equals("5")){
            time1b.setText(time);
        }
        else if(birr.equals("10")){
            time1c.setText(time);
        }
        else if(birr.equals("15")){
            time1d.setText(time);
        }
        else if(birr.equals("20")){
            time1e.setText(time);
        }
    }*/
}