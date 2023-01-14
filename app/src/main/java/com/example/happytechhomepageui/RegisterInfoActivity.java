package com.example.happytechhomepageui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happytechhomepageui.Modals.Cart;
import com.example.happytechhomepageui.Modals.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterInfoActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");
    TextView firstNameInput, lastNameInput, phoneInput, addressInput;
    Button registerBtn;
    String email, password;
    Spinner dropdown;

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
//    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        //Initialize variables
        firstNameInput = (TextView) findViewById(R.id.regFirst);
        lastNameInput = (TextView) findViewById(R.id.regLast);
        phoneInput = (TextView) findViewById(R.id.phone);
        addressInput = (TextView) findViewById(R.id.address);
        registerBtn = (Button) findViewById(R.id.confirm);
        Intent i = getIntent();
        email = (String) i.getExtras().get("email");
        password = (String) i.getExtras().get("password");

        //Dropdown Gender
        dropdown = (Spinner) findViewById(R.id.gender);
        String[] genders = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.layout_spinner, genders);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown);
        dropdown.setAdapter(adapter2);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        //Registration
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationAuth();
            }
        });

    }
    // Authentication
    private void RegistrationAuth() {
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String address = addressInput.getText().toString();
        String gender = dropdown.getSelectedItem().toString();

        if (firstName.isEmpty()){
            firstNameInput.setError("Please input this field");
            firstNameInput.requestFocus();
            return;
        }
        if (lastName.isEmpty()){
            lastNameInput.setError("Please input this field");
            lastNameInput.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            phoneInput.setError("Please input this field");
            phoneInput.requestFocus();
            return;
        }

        progressDialog.setMessage("Please wait a few minutes for the Registration");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        // Create user
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(email, firstName, lastName, phone, address, gender);
                    Cart cart = new Cart(user);
                    databaseReference2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(cart);
                    databaseReference
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterInfoActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Toast.makeText(RegisterInfoActivity.this,"Register Success", Toast.LENGTH_LONG).show();
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterInfoActivity.this,""+ Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterInfoActivity.this,""+ e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
}