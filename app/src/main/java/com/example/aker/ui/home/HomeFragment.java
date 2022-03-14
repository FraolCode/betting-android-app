package com.example.aker.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aker.DashBorde;
import com.example.aker.R;
import com.example.aker.userHelperDB;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

//1
    private HomeViewModel homeViewModel;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String Text = "text";

    String FullPhoneNumber, LastMessageDate; 
    int peoplevalue;
    String BirrAboutToBet;
    DatabaseReference reference;
    DatabaseReference redd;
    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Boolean ch = false;
    int permissionReadMessage;
    int permissionCall;
    String fromA;
    String getSendBirr;
    String UserCurrentBirr;
    String Us;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final LinearLayout linearLayout = root.findViewById(R.id.ly1);
        final LinearLayout linearLayout1 = root.findViewById(R.id.ly5);
        final LinearLayout linearLayout2 = root.findViewById(R.id.ly10);
        final LinearLayout linearLayout3 = root.findViewById(R.id.ly25);
        final LinearLayout linearLayout4 = root.findViewById(R.id.ly50);
        final LinearLayout linearLayout5 = root.findViewById(R.id.ly100);
        textView1 = root.findViewById(R.id.p1);
        textView2 = root.findViewById(R.id.p5);
        textView3 = root.findViewById(R.id.p10);
        textView4 = root.findViewById(R.id.p25);
        textView5 = root.findViewById(R.id.p50);
        textView6 = root.findViewById(R.id.p100);


        getdata();

        

        checkAndRequestPermissions();



        String requiredPermission = Manifest.permission.CALL_PHONE;
        int checkVal = getContext().checkCallingOrSelfPermission(requiredPermission);


        if (checkVal == PackageManager.PERMISSION_GRANTED) {

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BirrAboutToBet = "1";
                    checkAndRequestPermissions();
                    // dialog();
                    showDialog();
                }
            });
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BirrAboutToBet = "3";
                    checkAndRequestPermissions();
                    showDialog();
                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BirrAboutToBet = "5";
                    checkAndRequestPermissions();
                    showDialog();
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BirrAboutToBet = "10";
                    checkAndRequestPermissions();
                    showDialog();
                }
            });
            linearLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BirrAboutToBet = "15";
                    checkAndRequestPermissions();
                    showDialog();
                }
            });
            linearLayout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BirrAboutToBet = "20";
                    checkAndRequestPermissions();
                    showDialog();
                }
            });


        }



        DisplayPPlAndBetBirr();
        getReqBirr();

        return root;


    }







    public void getdata() {
        DashBorde activity = (DashBorde) getActivity();
        FullPhoneNumber = activity.getMyData();
        LastMessageDate = activity.date();
        fromA = activity.abc();
        getSendBirr = activity.getSendBirr();
    }


    private boolean checkAndRequestPermissions() {
        int id = 0;
        permissionReadMessage = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_SMS);
        permissionCall = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            id++;

        }
        if (permissionReadMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
            id++;
        }


        if (id == 2) {
            ch = true;
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }


        return true;
    }


    void showDialog() {

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View view = inflater.inflate(R.layout.button_alert_dialog, null);

                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setView(view)
                            .create();



                    Button acceptButton = view.findViewById(R.id.acceptButton);
                    Button cancelButton = view.findViewById(R.id.cancelButton);
                    TextView tv = view.findViewById(R.id.tv);
                    tv.setText("Do you Accept to Bet " + BirrAboutToBet + " !");

                    acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getReqBirr();

                            int UserCurrentBirrInt = 0;
                            int BirrAboutToBetInt = 0;
                            String UserBirrAfterBetString;
                            try {
                                UserCurrentBirrInt = Integer.parseInt(UserCurrentBirr);
                                BirrAboutToBetInt = Integer.parseInt(BirrAboutToBet);

                            } catch (NumberFormatException nfe) {

                            }
                            if (UserCurrentBirrInt >= BirrAboutToBetInt) {
                                int UserBirrAfterBet = UserCurrentBirrInt - BirrAboutToBetInt;
                                UserBirrAfterBetString = String.valueOf(UserBirrAfterBet);
                                upload(BirrAboutToBet);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
                                reference.child("birr").setValue(UserBirrAfterBetString);

                            } else {
                               Toast.makeText(getContext(),"You don't Have enough money to bet",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });


                    alertDialog.show();


                } else {
                    Toast.makeText(getContext(), "Sorry , check your Internet Connection please", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


    }

// get UserCurrentBirr
    public void getReqBirr() {
        redd = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserCurrentBirr = snapshot.child("birr").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void upload(String BirrAboutToBet) {

        reference = FirebaseDatabase.getInstance().getReference().child(BirrAboutToBet);

        userHelperDB userHelper = new userHelperDB(FullPhoneNumber);
        reference.push().setValue(userHelper);
        Toast.makeText(getContext(), "Good luck! You bet Successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getContext(), DashBorde.class);
        startActivity(intent);

    }


    public void DisplayPPlAndBetBirr() {

        reference = FirebaseDatabase.getInstance().getReference().child("1");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    peoplevalue = (int) snapshot.getChildrenCount();
                    textView1.setText("people: " + Integer.toString(peoplevalue));
                } else {
                    textView1.setText("people: 0");
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
                    peoplevalue = (int) snapshot.getChildrenCount();
                    textView2.setText("people: " + Integer.toString(peoplevalue));

                } else {
                    textView2.setText("people: 0");
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
                    peoplevalue = (int) snapshot.getChildrenCount();
                    textView3.setText("people: " + Integer.toString(peoplevalue));

                } else {
                    textView3.setText("people: 0");
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
                    peoplevalue = (int) snapshot.getChildrenCount();
                    textView4.setText("people: " + Integer.toString(peoplevalue));

                } else {
                    textView4.setText("people: 0");
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
                    peoplevalue = (int) snapshot.getChildrenCount();
                    textView5.setText("people: " + Integer.toString(peoplevalue));

                } else {
                    textView5.setText("people: 0");
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
                    peoplevalue = (int) snapshot.getChildrenCount();
                    textView6.setText("people: " + Integer.toString(peoplevalue));

                } else {
                    textView6.setText("people: 0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }















}


