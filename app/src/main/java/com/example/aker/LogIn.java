package com.example.aker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    private Spinner spinner;
    private EditText PhoneNumber;
    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        PhoneNumber = findViewById(R.id.editTextMobile);
        Next = findViewById(R.id.buttonContinue);
        buttonClick();


    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }


    public void buttonClick() {
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Phonecode = "+251";
                String UserPhone = PhoneNumber.getText().toString().trim();
                if (UserPhone.isEmpty()) {
                    PhoneNumber.setError("Number is required");
                    PhoneNumber.requestFocus();
                    return;
                }
                String firstChar = String.valueOf(UserPhone.charAt(0));
                if (firstChar.equals("0")) {
                    UserPhone = UserPhone.substring(1);
                }

                String FullPhoneNumber = Phonecode + UserPhone;
                Toast.makeText(getApplicationContext(), FullPhoneNumber, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogIn.this, VerifyPhoneActivity.class);
                intent.putExtra("FullPhoneNumber", FullPhoneNumber);
                intent.putExtra("PhoneWithOutCCode", UserPhone);
                startActivity(intent);


            }
        });
    }

    private void checkSession() {
        //check if user is logged in
        //if user is logged in --> move to mainActivity

        SessionManagement sessionManagement = new SessionManagement(LogIn.this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            //user id logged in so move to mainActivity
            moveToMainActivity();
        } else {
            //do nothing
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(LogIn.this, DashBorde.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}