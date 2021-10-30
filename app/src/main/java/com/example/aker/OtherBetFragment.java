package com.example.aker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class OtherBetFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recviews;
    adb_my_bet adapters;
    String fullPhone;
    ArrayList nameB;
    ArrayList allB;


    public OtherBetFragment() {
        // Required empty public constructor
    }


    public static OtherBetFragment newInstance(String param1, String param2) {
        OtherBetFragment fragment = new OtherBetFragment();
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

        View root = inflater.inflate(R.layout.fragment_other_bet, container, false);

        getdata();
        recviews = (RecyclerView) root.findViewById(R.id.my_recycler_view_other);
        recviews.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<my_bet_model> options =
                new FirebaseRecyclerOptions.Builder<my_bet_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("profile").child(fullPhone).child("Bet"), my_bet_model.class)
                        .build();

        adapters = new adb_my_bet(getContext(), options, "", "+251913357402");
        adapters.startListening();
        recviews.setAdapter(adapters);

        return root;
    }

    public void getdata() {
        DashBorde activity = (DashBorde) getActivity();
        fullPhone = activity.getMyData();
        Toast.makeText(getContext(),fullPhone,Toast.LENGTH_SHORT).show();


    }


}