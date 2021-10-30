package com.example.aker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_W3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_W3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView oneP, twoP, threeP;
    TextView oneU, twoU, threeU;

    public Fragment_W3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_W3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_W3 newInstance(String param1, String param2) {
        Fragment_W3 fragment = new Fragment_W3();
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
        View root = inflater.inflate(R.layout.fragment__w3, container, false);
        oneP = root.findViewById(R.id.phoneN1);
        twoP = root.findViewById(R.id.phoneN2);
        threeP = root.findViewById(R.id.phoneN3);
        oneU = root.findViewById(R.id.name);
        twoU = root.findViewById(R.id.name2);
        threeU = root.findViewById(R.id.name3);
        profile();
        return root;
    }

    public void profile() {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("winners").child("birr3");
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String _1st = snapshot.child("1st").getValue().toString();
                    String _2nd = snapshot.child("2nd").getValue().toString();
                    String _3rd = snapshot.child("3rd").getValue().toString();
                    winnername(_1st, _2nd, _3rd);
                    oneP.setText(_1st);
                    twoP.setText(_2nd);
                    threeP.setText(_3rd);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void winnername(String st, String nd, String rd) {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("profile").child(st);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String _1stusernme = snapshot.child("username").getValue().toString();
                    oneU.setText(_1stusernme);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        redd = FirebaseDatabase.getInstance().getReference().child("profile").child(nd);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String _1stusernme = snapshot.child("username").getValue().toString();
                    twoU.setText(_1stusernme);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        redd = FirebaseDatabase.getInstance().getReference().child("profile").child(rd);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String _1stusernme = snapshot.child("username").getValue().toString();
                    threeU.setText(_1stusernme);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}