package com.example.registrationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    TextView txtEmailVerification;
    Button btnVerifyNow;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmailVerification = findViewById(R.id.tvEmailVerification);
        btnVerifyNow = findViewById(R.id.btnEmailVerify);

        fauth = FirebaseAuth.getInstance();
        final FirebaseUser user = fauth.getCurrentUser();

        if(!user.isEmailVerified()){
            btnVerifyNow.setVisibility(View.VISIBLE);
            txtEmailVerification.setVisibility(View.VISIBLE);

            btnVerifyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this,"Verification link has been sent.",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"OnFailure: Email not sent"+e.getMessage());
                        }
                    });
                }
            });
        }
    }



    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LogIn.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        if(backPressedTime  + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else{
            backToast = Toast.makeText(getBaseContext(),"Press back button again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

    }
}