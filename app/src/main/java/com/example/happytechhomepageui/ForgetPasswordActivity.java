package com.example.happytechhomepageui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button resetBtn;
    TextView emailInput;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        resetBtn = (Button) findViewById(R.id.reset_btn);
        emailInput = (TextView) findViewById(R.id.inputEmail);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = emailInput.getText().toString();
        if (email.isEmpty()){
            emailInput.setError("Email is required!");
            emailInput.requestFocus();
            return;
        }
        if (!email.matches(emailPattern)){
            emailInput.setError("Email is invalid");
            emailInput.requestFocus();
            return;
        }
        progressDialog.setMessage("Checking for email ...");
        progressDialog.setTitle("Reset Password");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(ForgetPasswordActivity.this, "Please check your email inbox or spam inbox for resting the password!", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgetPasswordActivity.this, Objects.requireNonNull(task.getException().getLocalizedMessage()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}