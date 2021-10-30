package com.example.aker;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aker.ui.home.HomeFragment;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.aker.R.id.nav_host_fragment;
import static com.example.aker.R.id.nav_profile;

public class DashBorde extends AppCompatActivity {
    Toolbar toolbar;
    public String FullPhoneNumber, PhoneWithOutCCode;
    public String abc = "false";
    public String abcItem = "false";
    public String sendBirr = "0";
    public String sendBirritem = "0";
    //public static String test;
    int BetBirrFromMessage;
    int nav_Once = 0;
    String userbirr;
    String fullname;




    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_PREFS_Date = "sharedPrefs";
    public static final String sharedPreferencessTextfullphone = "text";
    public static final String sharedPreferencessTextshortphone = "text2";
    public static final String TextDate = "text3";
    public static final String TextDateItem = "text5";
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int qq = 0;
    int i = 0;
    int PhoneNumberFinderFromMessage = 0;
    int readMessagestarter = 0;
    String date = "0";
    String LastMessageDate = "0.0";
    String dateItem = "0";
    String PdateItem = "0";
    String checkM;
    boolean ch = false;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private AppBarConfiguration mAppBarConfiguration;


    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2000;

    boolean check = false;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_borde);


        //get phone num
        FullPhoneNumber = getIntent().getStringExtra("FullPhoneNumber");
        PhoneWithOutCCode = getIntent().getStringExtra("PhoneWithOutCCode");
        checkM = getIntent().getStringExtra("checker");



        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (checkM != null) {
            savedata();
            lodedata();
            update();
        }




        if (firstStart) {
            savedata();
            lodedata();
        }

        lodedata();
        update();

        checkAndRequestPermissions();

        nav(0);

        userBirr();

        if (savedInstanceState != null) {
            abc = savedInstanceState.getString("key");
            abcItem = savedInstanceState.getString("key2");
            sendBirr = savedInstanceState.getString("birr");
        }


        if (!isconnected(DashBorde.this)) {
            toolbar.setTitle("Connecting...");
        }

        // hcas();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hederView = navigationView.getHeaderView(0);

        // dial to enter birr
        ImageView addbirr = (ImageView) hederView.findViewById(R.id.addBirr);
        addbirr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBirrForBet();
            }
        });

    }


    // save data to SharedPreferences (FullPhoneNumber,PhoneWithOutCCode,LastMessageDate,PdateItem)
    public void savedata() {
        SharedPreferences sharedPreferencess = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        //  SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencess.edit();
        //SharedPreferences.Editor editors = prefs.edit();

        //to not override phone number from sharedPreferencess
        if (ch == false) {
            editor.putString(sharedPreferencessTextfullphone, FullPhoneNumber);
            editor.putString(sharedPreferencessTextshortphone, PhoneWithOutCCode);
        }

        editor.putString(TextDate, LastMessageDate);
        editor.putString(TextDateItem, PdateItem);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editora = prefs.edit();
        editora.putBoolean("firstStart", false);
        editora.apply();
        editor.apply();


    }

    //get data(FullPhoneNumber,PhoneWithOutCCode,LastMessageDate,PdateItem) form SharedPreferences
    public void lodedata() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        FullPhoneNumber = sharedPreferences.getString(sharedPreferencessTextfullphone, "");
        PhoneWithOutCCode = sharedPreferences.getString(sharedPreferencessTextshortphone, "");
        LastMessageDate = sharedPreferences.getString(TextDate, "");
        PdateItem = sharedPreferences.getString(TextDateItem, "");


    }

    //set phone on the Header (Phone, name and Birr i have)
    public void update() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hederView = navigationView.getHeaderView(0);
        TextView navPhoneNumber = (TextView) hederView.findViewById(R.id.textViewww);
        TextView navBiT = (TextView) hederView.findViewById(R.id.textViewww2);
        TextView navname = (TextView) hederView.findViewById(R.id.nameHeader);
        NavHeaderBirrDisplay();
        navPhoneNumber.setText(FullPhoneNumber);
       // test = FullPhoneNumber;
        //Toast.makeText(getApplicationContext(),FullPhoneNumber + " 4",Toast.LENGTH_SHORT).show();

    }

//checkAndRequestPermissions for READ_SMS,READ_SMS
    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        int locationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);

        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }


//navigation
    public void nav(int nav) {
        if(nav == 0){
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_Other, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


//get how much birr user have in database
    public void userBirr() {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
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


//display how much birr user have on navigation
    public void NavHeaderBirrDisplay() {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String birr = snapshot.child("birr").getValue().toString();
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);
                    TextView navBiT = (TextView) headerView.findViewById(R.id.textViewww2);
                    navBiT.setText(birr);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



// check if there is connection or not
    private boolean isconnected(DashBorde dashBorde) {
        ConnectivityManager connectivityManager = (ConnectivityManager) dashBorde.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (data != null && data.isConnected())) {
            return true;
        } else {
            return false;
        }
    }


//add birr to db ===dialog
    public void AddBirrForBet() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.addbirr_alert_dialog);
        Button acceptButton = (Button) dialog.findViewById(R.id.acceptButton);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        final EditText AdBirr = (EditText) dialog.findViewById(R.id.AdBirr);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AdBirrS = String.valueOf(AdBirr.getText());
                if (!(AdBirrS.equals(""))) {
                    dailNumber(AdBirrS);
                }
            }
        });
        dialog.show();
    }


//dail 806 so send birr
    private void dailNumber(String userAddBirr) {
        String USSDCode = "*" + "806*0913357402*" + userAddBirr + Uri.encode("#");


        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + USSDCode)));
        Toast.makeText(this, "Good Luck, You are about to Add " + userAddBirr + " birr", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.addbirr_alert_dialog);
        dialog.dismiss();

    }


//??
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", abc);
        outState.putString("key2", abcItem);
        outState.putString("birr", sendBirr);
    }



    // check the read message if the is connection
    @Override
    protected void onResume() {

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);

                final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
               // setSupportActionBar(toolbar);



                //toolbar.setLogo(R.drawable.icon);
                if (isconnected(DashBorde.this)) {

                    DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                    connectedRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            boolean connected = snapshot.getValue(Boolean.class);
                            if (connected) {
                                nav(nav_Once);
                                nav_Once = 1;
                                readMessage();
                            }
                            else{
                                toolbar.setTitle("Connecting...");
                                nav(0);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });


                }
                else {
                    toolbar.setTitle("Waiting for network...");
                }


                if (check == true) {

                    recreate();
                }

                String requiredPermission = Manifest.permission.READ_SMS;
                int checkVal = getApplicationContext().checkCallingOrSelfPermission(requiredPermission);
                if (checkVal == PackageManager.PERMISSION_GRANTED) {

                   // readMessageItem();
                }
            }
        }, delay);
        super.onResume();
    }



// read message to check if there is additional birr is added
    public void readMessage() {
        Uri inboxUri = Uri.parse("content://sms/inbox");
        int c = 0;
        int c1 = 0;
        readMessagestarter = 0;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(inboxUri, null, null, null, null);
        cursor.moveToFirst();
        while (readMessagestarter == 0) {

            String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
            date = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();


            String[] text = body.split("\\s");
            int index = body.indexOf("transfer");
            int index2 = body.indexOf("913357402");
            int index3 = body.indexOf("subscriber");


            if (number.equals("251994")) {

                if (index > -1) {

                    if (index3 == -1) {


                        if (index2 > -1) {

                            long oldMessageDate = Long.parseLong(LastMessageDate);
                            long NewMessageDate = Long.parseLong(date);

                            while (i == 0) {
                                //PhoneNumberFinderFromMessage is 0 when it started
                                if (text[PhoneNumberFinderFromMessage].equals("transfer")) {
                                    i = -1;
                                }

                                PhoneNumberFinderFromMessage++;

                            }

                            try {
                                BetBirrFromMessage = NumberFormat.getInstance().parse(text[PhoneNumberFinderFromMessage]).intValue();
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG);
                            }


                            String StringBetBirrFromMessage = String.valueOf(BetBirrFromMessage);

                            if (oldMessageDate == NewMessageDate) {
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "there is no new bet " , Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                                readMessagestarter = 1;
                            } else if (oldMessageDate < NewMessageDate) {
                                if (checkM == null) {

                                    String mysms = "You Added: " + StringBetBirrFromMessage + " date: " + date;




                                    View parentLayout = findViewById(android.R.id.content);
                                    Snackbar.make(parentLayout, mysms, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                    int UserBirrBefore = Integer.parseInt(userbirr);
                                    int betbirr = Integer.parseInt(StringBetBirrFromMessage);
                                    UserBirrBefore = UserBirrBefore + betbirr;
                                    String sendFinalBirr = String.valueOf(UserBirrBefore);
                                    AddFullBirrToDB(sendFinalBirr);
                                    abc = "true";
                                    sendBirr = StringBetBirrFromMessage;
                                    LastMessageDate = date;

                                    //to not override phone number from sharedPreferencess
                                    ch = true;
                                    savedata();

                                    //recreate the activity
                                    if (check == false) {
                                        check = true;
                                    }
                                }
                                LastMessageDate = date;
                                ch = true;
                                savedata();
                                checkM = null;

                                readMessagestarter = 1;
                            }


                        }


                    }

                }

            } else if (cursor.isLast() && readMessagestarter == 0) {
                View parentLayout = findViewById(android.R.id.content);
               /* Snackbar.make(parentLayout, "not found ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                checkM = null;
                readMessagestarter = 1;
            }


            cursor.moveToNext();
        }

        cursor.close();

    }



//save total birr to db after user add additional birr
    public void AddFullBirrToDB(String finalBirr){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
        reference.child("birr").setValue(finalBirr);
    }


//stop checking read message
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }



// check if all permission is granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PERMISSION_NOT_GRANTED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }



//create the menu for logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_borde, menu);
        return true;
    }



    //call logout
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                logout();
        }


        return super.onOptionsItemSelected(item);
    }


//??
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//logout
    public void logout() {
        //this method will remove session and open login screen
        SessionManagement sessionManagement = new SessionManagement(DashBorde.this);
        sessionManagement.removeSession();

        Intent intent = new Intent(DashBorde.this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    public String getMyData() {
        return FullPhoneNumber;
    }



    public String date() {
        return LastMessageDate;
    }



    public String abc() {
        return abc;
    }



    public String abcItem() {
        return abcItem;
    }


    public String getSendBirr() {
        return sendBirr;
    }


    public String getSendBirrItem() {
        return sendBirritem;
    }


    public String getState() {
        return FullPhoneNumber;
    }


    public void setState(String input) {
        this.FullPhoneNumber = input;
    }


    public void hcas() {
        final DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(FullPhoneNumber);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.exists())) {
                    redd.child("req").setValue("0");
                    redd.child("birr").setValue("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}









