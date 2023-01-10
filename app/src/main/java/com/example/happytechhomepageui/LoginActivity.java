package com.example.happytechhomepageui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView inputEmail, inputPass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    Button createBtn, loginBtn, forgotPass;

    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        createBtn  = (Button) findViewById(R.id.createAccount);
        loginBtn = (Button) findViewById(R.id.login_btn);
        forgotPass = (Button) findViewById(R.id.forgotPassword);
        inputEmail = (TextView) findViewById(R.id.inputEmail);
        inputPass = (TextView) findViewById(R.id.inputPassword);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAuth();
            }
        });
    }

    private void LoginAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPass.getText().toString();

        //Input Validation
        if (!email.matches(emailPattern)){
            inputEmail.setError("Invalid Email (Ex: test@gmail.com)");
            inputEmail.requestFocus();
        }else if (password.isEmpty()){
            inputPass.setError("Please input password");
            inputPass.requestFocus();
        }else {
            progressDialog.setMessage("Checking for authentication ...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //Login authentication
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}