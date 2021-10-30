package com.example.aker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addBettinglist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addBettinglist extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    NumberPicker numberPicker;
    FloatingActionButton floatingActionButton;
    Switch mySwitch;
    EditText password1, password2, Bname, Bbirr;
    TextView passwordAtxt;
    Button create;
    DatabaseReference reference;
    String phonenumfromD;
    String userbirr;
    ArrayList<String> phoneNumbers;
    Handler handler = new Handler();
    String found = "F";

    String name = null, phone = null;

    List<String> list;
    private DatabaseReference mCustomerDatabase;
    DatabaseReference myRef;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addBettinglist() {
        // Required empty public constructor
    }


    public static addBettinglist newInstance(String param1, String param2) {
        addBettinglist fragment = new addBettinglist();
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
        View root = inflater.inflate(R.layout.fragment_add_bettinglist, container, false);
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("1");
        create = root.findViewById(R.id.create);
        mySwitch = root.findViewById(R.id.passwordS);
        numberPicker = root.findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(2);
        numberPicker.setValue(5);
        floatingActionButton = root.findViewById(R.id.back_icon);
        password1 = root.findViewById(R.id.password1);
        password2 = root.findViewById(R.id.password2);
        Bname = root.findViewById(R.id.name_et_cp);
        Bbirr = root.findViewById(R.id.birr_et_cp);
        passwordAtxt = root.findViewById(R.id.passwordAgainTxt);
        password1.setEnabled(false);
        password1.setText("");
        password1.setHint("No Password");
        password1.setTextColor(Color.parseColor("#FFFFFF"));
        password1.setBackgroundColor(Color.parseColor("#C4CACE"));
        password2.setVisibility(View.GONE);
        passwordAtxt.setVisibility(View.GONE);


        getdata();
        asca2();
        checkname();
        // Toast.makeText(getContext(),userbirr,Toast.LENGTH_SHORT).show();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherBets nextFrag = new otherBets();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.add_betting, nextFrag, "findThisFragments")
                        .addToBackStack(null)
                        .commit();
            }
        });
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    password1.setEnabled(true);
                    password1.setBackgroundResource(R.drawable.editborder);
                    password2.setVisibility(View.VISIBLE);
                    password1.setHint("********");
                    password1.setTextColor(Color.parseColor("#000000"));
                    passwordAtxt.setVisibility(View.VISIBLE);
                } else {
                    password1.setEnabled(false);
                    password1.setText("");
                    password1.setHint("No Password");
                    password1.setTextColor(Color.parseColor("#FFFFFF"));
                    password1.setBackgroundColor(Color.parseColor("#C4CACE"));
                    password2.setVisibility(View.GONE);
                    passwordAtxt.setVisibility(View.GONE);
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {
                            d();
                        } else {
                            Toast.makeText(getContext(), "Sorry , check your Internet Connection please", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });


            }
        });


        return root;
    }

    public void d() {

        if (!(Bname.getText().toString().isEmpty()) &&
                !(Bbirr.getText().toString().isEmpty())) {

            abca();
            // Toast.makeText(getContext(),found,Toast.LENGTH_SHORT).show();
            if (found.equals("true")) {
                Bname.setError("Sorry,this Name is already taken");
                found = "false";
            } else {

                //   Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                String bb = String.valueOf(Bbirr.getText());
                int bbb = Integer.parseInt(userbirr);
                int a = Integer.parseInt(bb);
                if (a != 0 && a <= bbb) {

                    if (mySwitch.isChecked()) {
                        if (!(password1.getText().toString().isEmpty()) &&
                                !(password2.getText().toString().isEmpty())) {
                            String pass1, pass2;
                            pass1 = String.valueOf(password1.getText());
                            pass2 = String.valueOf(password2.getText());
                            if (pass1.length() >= 6) {
                                if (pass1.equals(pass2)) {
                                    insert(pass1);
                                } else {
                                    password1.setError("password1 and password2 are not equal");
                                    password2.setError("password1 and password2 are not equal");
                                }
                            } else {
                                password1.setError("password should be more than 6 characters ");
                            }


                        } else {
                            String b, b1;
                            b = String.valueOf(password1.getText());
                            b1 = String.valueOf(password2.getText());
                            if (b.equals("")) {
                                password1.setError("Empty field");
                            }
                            if (b1.equals("")) {
                                password2.setError("Empty field");
                            }
                        }
                    } else {
                        insert("nulll");
                    }

                } else {
                    if (a == 0) {
                        Bbirr.setError("Betting Birr can't be 0");
                    } else {
                        Bbirr.setError("Birr is more that u have");
                    }

                }
            }


        } else {
            String b, b1;
            b = String.valueOf(Bname.getText());
            b1 = String.valueOf(Bbirr.getText());
            if (b.equals("")) {
                Bname.setError("Empty field");
            }
            if (b1.equals("")) {
                Bbirr.setError("Empty field");
            }
        }
    }


    public void insert(String passwordP) {


        String name, birr, playernum, players;
        name = String.valueOf(Bname.getText());
        birr = String.valueOf(Bbirr.getText());

        int bbb = Integer.parseInt(userbirr);
        int a = Integer.parseInt(birr);
        int full = bbb - a;
        String fullbirr = String.valueOf(full);
        reference = FirebaseDatabase.getInstance().getReference().child("other").child(name);
        playernum = String.valueOf(numberPicker.getValue());
        players = String.valueOf(Bname.getText());
        reference.child("name").setValue(name);
        reference.child("birr").setValue(birr);
        reference.child("password").setValue(passwordP);
        reference.child("all").setValue(playernum);
        reference.child("pplin").setValue("1");
        reference.child("admin").setValue(phonenumfromD);
        reference = FirebaseDatabase.getInstance().getReference().child("profile").child(phonenumfromD).child("Bet").child(name);
        reference.child("Bname").setValue(name);
        reference.child("Bbirr").setValue(birr);
        reference.child("All").setValue(playernum);
        changeUserBirr(fullbirr);
        addUserOnBet(name);

        Intent intent = new Intent(getActivity(), DashBorde.class);
        startActivity(intent);


    }

    public void changeUserBirr(String birr) {
        reference = FirebaseDatabase.getInstance().getReference().child("req").child(phonenumfromD);
        reference.child("birr").setValue(birr);

    }

    public void addUserOnBet(String name) {
        reference = FirebaseDatabase.getInstance().getReference().child("ppl").child(name).push();
        reference.child("phone").setValue(phonenumfromD);

    }

    public void getdata() {
        DashBorde activity = (DashBorde) getActivity();
        phonenumfromD = activity.getMyData();


    }

    public void asca2() {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(phonenumfromD);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userbirr = snapshot.child("birr").getValue().toString();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkname() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("other");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.exists()) {
                            collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue(), "other");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void collectPhoneNumbers(Map<String, Object> users, final String name) {


        // abc = "birr" + birr;
        phoneNumbers = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("name"));


            // handler.postDelayed(runnable, 100);
        }
        if (phoneNumbers != null) {
            final int ph = phoneNumbers.size();
        }


    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {


            handler.postDelayed(this, 100L);  // 1 second delay


        }
    };

    public void abca() {
        int ph = 0;
        if (phoneNumbers != null) {
            ph = phoneNumbers.size();
        }

        for (int i = 0; i < ph; i++) {
            String path = phoneNumbers.get(i);
            String nam = String.valueOf(Bname.getText());
            if (path.equals(nam)) {
                found = "true";
                //  Toast.makeText(getContext(),"true",Toast.LENGTH_SHORT).show();
                i = ph;
            }
        }
    }

}