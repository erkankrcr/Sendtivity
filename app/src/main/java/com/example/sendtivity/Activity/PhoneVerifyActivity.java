package com.example.sendtivity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.sendtivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerifyActivity extends Activity {
    private FirebaseAuth mAuth;
    private String number;
    private String VerificationID;
    private ProgressBar progressBar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.PhoneVerifyProgress);
        editText = findViewById(R.id.PhoneVerifyET);

        Intent intent = getIntent();
        number = intent.getStringExtra("Number");
        SendVerificationCode(number);

        findViewById(R.id.PhoneSuccesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String code = editText.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(code);
            }
        });


    }

    private void VerifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,code);
        SignInWithCredential(credential);

    }

    private void SignInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(PhoneVerifyActivity.this,PhoneSignActivity.class);
                    startActivity(intent);
                }else{
                    System.out.println("SignInWithCredential Failed");
                }
            }
        });
    }

    private void SendVerificationCode(String Number){
        System.out.println("Doğrulama Kodu Gönderiliyor");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallback

        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VerificationID = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            Toast.makeText(
                    PhoneVerifyActivity.this,
                    "Doğrulama Tamamlandı",
                    Toast.LENGTH_LONG
            ).show();
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(code);
            }else{
                System.out.println("Code Boş dondu");
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            System.out.println("Başarısız");
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
                System.out.println("FirebaseAuthInvaliedCredentialsException");
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
                System.out.println("FirebaseTooManyRequestException");


            }
        }
    };


}
