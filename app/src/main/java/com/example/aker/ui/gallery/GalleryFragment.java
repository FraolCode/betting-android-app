package com.example.aker.ui.gallery;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.aker.DashBorde;
import com.example.aker.R;
import com.example.aker.otherBets;
import com.example.aker.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    Handler handler = new Handler();
    Handler handler2 = new Handler();
    Runnable runnable;
    Runnable runnable2;
    ProgressDialog progressDialog;
    String nnn;
    int delay = 5000;
    int delay2 = 100;
    int cou = 0;
    int phoneC = 0;
    int value;
    String userbirr;

    TextView phone;
    boolean stop = false;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView winnerPhone;
    ArrayList<String> Names;
    ArrayList<String> Birr;
    ArrayList<String> adminPhone;
    ArrayList<String> UadminThis;
    ArrayList<String> max;
    ArrayList<String> maxAdmin;
    ArrayList<String> minAdmin;
    ArrayList<String> birrAdmin;
    ArrayList<String> min;
    ArrayList<String> FullBetName;
    ArrayList<String> FullBetBirrSize;
    ArrayList<String> maxppl;
    ArrayList<String> AllBetPhoneNumber;
    ArrayList<String> winnerL;
    ArrayList<String> finishedBetName;
    ArrayList<String> finishedBetPhone;
    ArrayList<String> finishedBetwinner;
    ArrayList<String> aaaccc;
    ArrayList<String> myB;

    String winnerPhonefromUserDb;
    String winnerNamefromUserDb;

    DatabaseReference reference;


    Button win;
    ImageView hide, next, show, winnerN;
    LinearLayout fullBet;
    boolean checkme = true;
    int showCounter = 1;

    String maxS, minS;


    String Fullphone;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static int int_items = 2;

    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tab_layout);

        DashBorde activity = (DashBorde) getActivity();
        Fullphone = activity.getMyData();
        checkname();
        myB = new ArrayList<>();
        win = root.findViewById(R.id.winB);
        fullBet = root.findViewById(R.id.full);
        next = root.findViewById(R.id.next);
        hide = root.findViewById(R.id.hide);
        show = root.findViewById(R.id.show);
        winnerN = root.findViewById(R.id.winnerNotfi);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullBet.setVisibility(View.GONE);
                show.setVisibility(View.VISIBLE);
                checkme = false;

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullBet.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);
                checkme = true;
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return root;
    }

    @Override
    public void onResume() {

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);

                checkname();
                checkWinnerNotf();
                asd();


            }
        }, delay);
        super.onResume();
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new otherBets();


            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    String categorya = "Home";
                    return categorya;
                case 1:
                    String category = "Other Bets";
                    return category;


            }
            return null;
        }
    }


    public void checkname() {
        Query ref = FirebaseDatabase.getInstance().getReference().child("other");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.exists()) {
                            collectname((Map<String, Object>) dataSnapshot.getValue(), "other");
                        } else {
                            fullBet.setVisibility(View.GONE);
                            show.setVisibility(View.GONE);
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
        adminPhone = new ArrayList<>();
        max = new ArrayList<>();
        min = new ArrayList<>();
        Birr = new ArrayList<>();


        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            Names.add((String) singleUser.get("name"));
            adminPhone.add((String) singleUser.get("admin"));
            max.add((String) singleUser.get("all"));
            min.add((String) singleUser.get("pplin"));
            Birr.add((String) singleUser.get("birr"));


        }
        if (Names != null) {
            checkadminPhone();
        }


    }

    public void checkadminPhone() {
        UadminThis = new ArrayList<>();
        maxAdmin = new ArrayList<>();
        minAdmin = new ArrayList<>();
        birrAdmin = new ArrayList<>();
        int size = adminPhone.size();
        for (int i = 0; i < size; i++) {
            String list = adminPhone.get(i);
            if (list.equals(Fullphone)) {
                UadminThis.add(Names.get(i));
                maxAdmin.add(max.get(i));
                minAdmin.add(min.get(i));
                birrAdmin.add(Birr.get(i));

            }
        }
        if (UadminThis != null) {
            lastCH();

        }


    }

    public void lastCH() {
        boolean ch = false;
        boolean noFullBet = true;
        FullBetName = new ArrayList<>();
        FullBetBirrSize = new ArrayList<>();
        maxppl = new ArrayList<>();
        if (UadminThis == null && maxAdmin == null && minAdmin == null) {
            Toast.makeText(getContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
        } else {
            int count = 0;

            for (int i = 0; i < maxAdmin.size(); i++) {
                if (maxAdmin.get(i).equals(minAdmin.get(i))) {
                    count = count + 1;
                    FullBetName.add(UadminThis.get(i));
                    FullBetBirrSize.add(birrAdmin.get(i));
                    maxppl.add(maxAdmin.get(i));
                    ch = true;
                    noFullBet = false;

                }
            }
            if (ch) {

                String aa = String.valueOf(count);
                next.setVisibility(View.VISIBLE);
                nextBt();
                winnerClicked();
                if (checkme) {
                    fullBet.setVisibility(View.VISIBLE);
                    int allbirr = Integer.parseInt(FullBetBirrSize.get(0));
                    int maxpplA = Integer.parseInt(maxppl.get(0));
                    allbirr = allbirr * maxpplA;
                    float per = (allbirr * 0.10f);
                    int i = Math.round(per);
                    allbirr = allbirr - i;
                    win.setText("Name: " + FullBetName.get(0) + "\n" + "Winner wins " + allbirr + " Birr");
                    checkme = false;
                }


            } else {
                win.setText("Name: NULL");
                next.setVisibility(View.INVISIBLE);
            }


        }
    }

    public void nextBt() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = FullBetName.size();
                if (showCounter < size) {

                    int allbirr = Integer.parseInt(FullBetBirrSize.get(showCounter));
                    int maxpplA = Integer.parseInt(maxppl.get(showCounter));
                    allbirr = allbirr * maxpplA;
                    float per = (allbirr * 0.10f);
                    int i = Math.round(per);
                    allbirr = allbirr - i;
                    win.setText("Name: " + FullBetName.get(showCounter) + "\n" + "Winner wins " + allbirr + " Birr");
                    showCounter++;

                } else {
                    showCounter = 0;
                }

            }
        });

    }

    public void clickedNamePhones(String name) {
        Query ref = FirebaseDatabase.getInstance().getReference().child("ppl").child(name);

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.exists()) {
                            collectphone((Map<String, Object>) dataSnapshot.getValue(), "other");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public void collectphone(Map<String, Object> users, final String name) {

        AllBetPhoneNumber = new ArrayList<>();

        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            AllBetPhoneNumber.add((String) singleUser.get("phone"));
        }
    }

    public void abbbbc(String name) {
        Query ref = FirebaseDatabase.getInstance().getReference().child("ppl").child(name);

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.exists()) {
                            abb((Map<String, Object>) dataSnapshot.getValue(), "other");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public void abb(Map<String, Object> users, final String name) {

        aaaccc = new ArrayList<>();

        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            aaaccc.add((String) singleUser.get("phone"));
        }
    }

    public void winnerClicked() {


        win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullBet.setVisibility(View.GONE);
                int ass = 0;

                if (showCounter != 0) {
                    ass = showCounter - 1;
                }
                final String BetName = FullBetName.get(ass);
                clickedNamePhones(FullBetName.get(ass));
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.pick_winner);
                final Button acceptButton = (Button) dialog.findViewById(R.id.pickWinner);
                Button cancelButton = (Button) dialog.findViewById(R.id.cancelB);
                final LinearLayout winL = (LinearLayout) dialog.findViewById(R.id.winL);
                final LinearLayout picW = (LinearLayout) dialog.findViewById(R.id.picW);
                Button BackToHome = (Button) dialog.findViewById(R.id.backToHome);
                winnerPhone = (TextView) dialog.findViewById(R.id.winnerPhone);
                final Button checkAllIn = (Button) dialog.findViewById(R.id.checkAllIn);
                phone = (TextView) dialog.findViewById(R.id.phonea);
                BackToHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                checkAllIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AllBetPhoneNumber == null) {
                            Toast.makeText(getContext(), "Please,Check Your internet connection" + "\n" + "Try again", Toast.LENGTH_SHORT).show();
                        } else {
                            phone.setText(AllBetPhoneNumber.get(0));
                            checkAllIn.setVisibility(View.GONE);
                            acceptButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
                // if button is clicked, close the custom dialog

                final int finalAss = ass;
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acceptButton.setVisibility(View.INVISIBLE);

                        progressDialog = new ProgressDialog(getContext(), 0);
                        progressDialog.setTitle("picking Winner");

                        cou = 0;
                        final int maxx = AllBetPhoneNumber.size() - 1;
                        final int winner = new Random().nextInt((maxx - 0) + 1) + 0;


                        final String winName = AllBetPhoneNumber.get(winner);
                        int abirr = Integer.parseInt(FullBetBirrSize.get(finalAss));
                        int maxpplA = Integer.parseInt(maxppl.get(finalAss));
                        abirr = abirr * maxpplA;
                        float per = (abirr * 0.15f);
                        int i = Math.round(per);
                        abirr = abirr - i;
                        asca2(winName, abirr, BetName);


                        final int finalAbirr = abirr;

                        handler2.postDelayed(runnable2 = new Runnable() {
                            public void run() {


                                handler2.postDelayed(runnable2, delay2);

                                int max = AllBetPhoneNumber.size() - 1;
                                final int random = new Random().nextInt((max - 0) + 1) + 0;
                                phone.setText(AllBetPhoneNumber.get(random));
                                progressDialog.setMessage(AllBetPhoneNumber.get(random));
                                progressDialog.show();
                                cou++;

                                if (cou == 50) {
                                    winning(winName, finalAbirr, BetName, maxx);
                                    handler2.removeMessages(0);
                                    winnerPhone.setText(winName);
                                    winL.setVisibility(View.VISIBLE);
                                    picW.setVisibility(View.GONE);

                                }


                            }
                        }, delay2);

                    }

                });


                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().recreate();
                        dialog.dismiss();
                    }
                });

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.70);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(width, height);
                dialog.show();


            }
        });
    }

    public void winning(final String Wphone, int winBirr, final String betNm, final int maxxx) {

        int userBB = Integer.parseInt(userbirr);
        userBB = userBB + winBirr;
        String fullB = String.valueOf(userBB);

        reference = FirebaseDatabase.getInstance().getReference().child("req").child(Wphone);
        reference.child("birr").setValue(fullB)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            reference = FirebaseDatabase.getInstance().getReference().child("other").child(betNm);
                            reference.removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                reference = FirebaseDatabase.getInstance().getReference().child("winName").child(betNm);
                                                reference.child("name").setValue(betNm)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    reference = FirebaseDatabase.getInstance().getReference().child("winName").child(betNm);
                                                                    reference.child("winner").setValue(Wphone)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        reference = FirebaseDatabase.getInstance().getReference().child("profile").child(Fullphone)
                                                                                        .child("Bet").child(betNm);
                                                                                        reference.removeValue()
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if(task.isSuccessful()){
                                                                                                            for (int i = 0; i < maxxx + 1; i++) {
                                                                                                                reference = FirebaseDatabase.getInstance().getReference().child("winName").child(betNm);
                                                                                                                reference.child(AllBetPhoneNumber.get(i)).setValue(AllBetPhoneNumber.get(i));

                                                                                                            }
                                                                                                            if (cou == 50) {
                                                                                                                progressDialog.dismiss();
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                });



                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    public void asca2(final String Wphone, final int winBirr, final String betName) {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(Wphone);
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

    public void checkWinnerNotf() {
        Query ref = FirebaseDatabase.getInstance().getReference().child("winName");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.exists()) {
                            getproWinnerWinner((Map<String, Object>) dataSnapshot.getValue(), "winName");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public void getproWinnerWinner(Map<String, Object> users, final String name) {

        finishedBetName = new ArrayList<>();
        finishedBetPhone = new ArrayList<>();
        finishedBetwinner = new ArrayList<>();
        winnerL = new ArrayList<>();


        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            finishedBetName.add((String) singleUser.get("name"));
            finishedBetPhone.add((String) singleUser.get(Fullphone));
            finishedBetwinner.add((String) singleUser.get("winner"));
        }
        if (finishedBetName != null && finishedBetPhone != null) {
            if (phoneC >= finishedBetName.size()) {
                phoneC = 0;
            }


            abbbbc(finishedBetName.get(phoneC));
            if (aaaccc != null) {
                if (finishedBetPhone.get(phoneC) != null) {

                    for (int j = phoneC; j < finishedBetPhone.size(); j++) {
                        if (finishedBetPhone.get(j) == null) {
                            phoneC = phoneC + 1;
                        } else {
                            j = finishedBetName.size();
                        }
                    }
                    for (int i = 0; i < aaaccc.size(); i++) {
                        if (aaaccc.get(i).equals(Fullphone)) {
                            if (myB.size() == 0) {
                                myB.add(finishedBetName.get(phoneC));
                                myB.add(finishedBetPhone.get(phoneC));
                                myB.add(finishedBetwinner.get(phoneC));
                            }
                            // found(finishedBetName.get(phoneC));
                            i = aaaccc.size();

                        } else {
                            if (i == aaaccc.size()) {
                                phoneC++;
                            }
                        }
                    }


                } else {
                    phoneC++;


                }
            } else {
                phoneC++;

            }


        }


    }

    public void found(String nn) {

        DatabaseReference redd1 = FirebaseDatabase.getInstance().getReference().child("winName").child(nn);

        redd1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    winnerNamefromUserDb = snapshot.child("name").getValue().toString();
                    winnerPhonefromUserDb = snapshot.child("winner").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void asd() {


        if (myB.size() == 3) {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final String Bname = myB.get(0);
            final String phoneMY = myB.get(1);
            final String winn = myB.get(2);

            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("winName").child(Bname);
            reference.keepSynced(true);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        value = (int) snapshot.getChildrenCount();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if(value != 0){
                winnerN.setVisibility(View.VISIBLE);
            }

            winnerN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    winnerN.setVisibility(View.GONE);
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.win_alert_dialog);
                    final Button BackToHome = (Button) dialog.findViewById(R.id.cloz);
                    final TextView winTxt = (TextView) dialog.findViewById(R.id.winTxt);
                    final TextView phoneaa = (TextView) dialog.findViewById(R.id.phoneaa);





                    if (value != 0) {
                        Query applesQuery = ref.child("ppl").child(Bname).orderByChild("phone").equalTo(phoneMY);

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("TAG", "onCancelled", databaseError.toException());
                            }
                        });
                    }

                    if (value == 3) {
                        reference.removeValue();
                    } else if (value > 3) {
                        DatabaseReference eference = FirebaseDatabase.getInstance().getReference().child("winName").child(Bname).child(phoneMY);
                        eference.removeValue();
                        eference = FirebaseDatabase.getInstance().getReference().child("profile").child(Fullphone).child("Bet").child(Bname);
                        eference.removeValue();
                    }

                    winTxt.setText("Winner for Bet \"" + Bname + "\"");
                    phoneaa.setText(winn);

                    myB.clear();
                    finishedBetName.clear();
                    finishedBetPhone.clear();

                    BackToHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                    int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setLayout(width, height);
                    dialog.show();
                }
            });
        }


    }


}