package com.example.aker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String mVerificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText UserinputSMSCodeEditText;
    //String p;
    String PhoneWithOutCCode;
    String FullPhoneNumber;
    FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);


        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        UserinputSMSCodeEditText = findViewById(R.id.editTextCodeeditTextCode);
        FullPhoneNumber = getIntent().getStringExtra("FullPhoneNumber");
        PhoneWithOutCCode = getIntent().getStringExtra("PhoneWithOutCCode");
        documentReference = dbFirestore.collection("user").document(FullPhoneNumber);

        TextView PhonetextView = findViewById(R.id.vnPhone);
        PhonetextView.setText("Please type the verification code sent\\nto " + FullPhoneNumber);
       // p = FullPhoneNumber;
        sendVerificationCode(FullPhoneNumber);
        buttonSignInClick();

    }


    // verifyVerificationCode with OTP code user enter
    public void buttonSignInClick() {
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = UserinputSMSCodeEditText.getText().toString().trim();

                if (code.isEmpty()) {
                    UserinputSMSCodeEditText.setError("Enter code...");
                    UserinputSMSCodeEditText.requestFocus();
                    return;
                }
                verifyVerificationCode(code);
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }


// call signInWithPhoneAuthCredential with the (credential) OTP code
    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    //check if signInWithPhoneAuthCredential is successful or not
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login();


                        } else {
                            //if code is not correct
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


// call PhoneAuthProvider with PhoneNumber, timeout, the callback
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    // check:- onCodeSent,onVerificationCompleted,onVerificationFailed
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            // mResendToken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                verifyVerificationCode(code);
                progressBar.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    };


// call moveToMainActivity
    public void login() {

        User user = new User(12, "Someone");
        SessionManagement sessionManagement = new SessionManagement(VerifyPhoneActivity.this);
        sessionManagement.saveSession(user);

        moveToMainActivity();
    }



    // to main activity or profile editer
    private void moveToMainActivity() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("profile").child(FullPhoneNumber);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Intent intent = new Intent(VerifyPhoneActivity.this, DashBorde.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("FullPhoneNumber", FullPhoneNumber);
                    intent.putExtra("PhoneWithOutCCode", PhoneWithOutCCode);
                    intent.putExtra("checker", "checker");
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(VerifyPhoneActivity.this, ProfileEditer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("FullPhoneNumber", FullPhoneNumber);
                    intent.putExtra("PhoneWithOutCCode", PhoneWithOutCCode);
                    intent.putExtra("checker", "checker");
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}