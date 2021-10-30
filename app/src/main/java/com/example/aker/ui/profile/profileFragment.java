package com.example.aker.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.aker.DashBorde;
import com.example.aker.Member;
import com.example.aker.ProfileEditer;
import com.example.aker.R;
import com.example.aker.ViewHolder;
import com.example.aker.req;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.WriteResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class profileFragment extends Fragment {

    private profileshowViewModel slideshowViewModel;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference reference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    TextView nameEt, bioEt, ageEt, Phone, diptxt, reqtxt, nowBettxt;
    ImageView editB;
    ImageView imageView;
    String fullPhone;
    String fullname;
    String username;
    String age;
    String image;
    ProgressBar progressBar;
    String phonenumfromD;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(profileshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = root.findViewById(R.id.progressba);
        getdata();
        fullPhone = phonenumfromD;
        editB = root.findViewById(R.id.editB);
        nameEt = root.findViewById(R.id.fullname);

        Phone = root.findViewById(R.id.phone);
        bioEt = root.findViewById(R.id.tv_name);
        ageEt = root.findViewById(R.id.tv_address);
        diptxt = root.findViewById(R.id.diptxt);
        reqtxt = root.findViewById(R.id.reqtxt);
        imageView = root.findViewById(R.id.imageV);

        //asca();


        Phone.setText(fullPhone);
        progressBar.setVisibility(View.VISIBLE);

        profile();


        diptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDemo();
            }
        });


        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileEditer.class);
                startActivity(intent);

            }
        });

        asca2();
        update();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public void getdata() {
        DashBorde activity = (DashBorde) getActivity();
        phonenumfromD = activity.getMyData();


    }

    public void fromdatabase() {
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {
                            String name_result = task.getResult().getString("name");
                            String age_result = task.getResult().getString("age");
                            String bio_result = task.getResult().getString("bio");
                            String Url = task.getResult().getString("url");


                            fullname = name_result;
                            age = age_result;
                            username = bio_result;
                            image = Url;


                        } else {
                            Toast.makeText(getContext(), "No Profile exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

    public void update() {
        nameEt.setText(fullname);
        bioEt.setText(username);
        progressBar.setVisibility(View.INVISIBLE);


    }

    void alertDialogDemo() {

        // get alert_dialog.xml view
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        String birr = diptxt.getText().toString();
                        String userin = userInput.getText().toString();
                        String req = reqtxt.getText().toString();
                        int b = Integer.parseInt(birr);
                        int a = Integer.parseInt(userin);
                        int c = Integer.parseInt(req);
                        int all = b + c;
                        if (a > all) {
                            Toast.makeText(getContext(), "Sorry,You can't receive more that You have!", Toast.LENGTH_LONG).show();
                        } else if (c == 0) {
                            int r = b - a;
                            String bi = String.valueOf(r);
                            String bi2 = String.valueOf(a);
                            add_field(bi2, bi);


                        } else {
                            int r = all - a;
                            String bi = String.valueOf(r);
                            String bi2 = String.valueOf(a);
                            add_field(bi2, bi);


                        }


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void add_field(String req, String birr) {
        reference = FirebaseDatabase.getInstance().getReference().child("req").child(fullPhone);
        reference.child("req").setValue(req);
        reference.child("birr").setValue(birr);

    }


    public void asca2() {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("req").child(fullPhone);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String birr = snapshot.child("birr").getValue().toString();
                    String req = snapshot.child("req").getValue().toString();
                    diptxt.setText(birr);
                    reqtxt.setText(req);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void profile() {
        DatabaseReference redd = FirebaseDatabase.getInstance().getReference().child("profile").child(fullPhone);
        redd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();
                    bioEt.setText(username);
                    nameEt.setText(name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}






