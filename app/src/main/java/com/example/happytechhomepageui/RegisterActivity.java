package com.example.happytechhomepageui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;





public class RegisterActivity extends AppCompatActivity {
    Button alreadyHaveAccount, registerBtn;
    TextView inputEmail, inputPass, inputConfirmPass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveAccount = (Button) findViewById(R.id.lg_btn);
        registerBtn = (Button) findViewById(R.id.reg);
        inputEmail = (TextView) findViewById(R.id.regEmail);
        inputPass = (TextView) findViewById(R.id.regPass);
        inputConfirmPass = (TextView) findViewById(R.id.regRePass);



        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }

    private void Validation() {
        String email = inputEmail.getText().toString();
        String password = inputPass.getText().toString();
        String rePass = inputConfirmPass.getText().toString();

        // Validation
        if (!email.matches(emailPattern)){
            inputEmail.setError("Invalid Email (Ex: test@gmail.com)");
            inputEmail.requestFocus();
        }else if (password.isEmpty() || password.length()<6){
            //ToDO: Change password to 8 characters and create password pattern
            inputPass.setError("Password must have at least 6 characters");
            inputPass.requestFocus();
        }else if (!password.equals(rePass)){
            inputConfirmPass.setError("Re-type password does not match");
            inputConfirmPass.requestFocus();
        }else {
            Intent intent = new Intent(RegisterActivity.this, RegisterInfoActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("password", password);
            startActivity(intent);
        }

    }
}