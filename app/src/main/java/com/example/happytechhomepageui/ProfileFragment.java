package com.example.happytechhomepageui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.happytechhomepageui.Modals.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {
    FirebaseUser user;
    DatabaseReference reference;
    String uID;
    User userProfile;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        uID = user.getUid();
        TextView firstName = (TextView) getView().findViewById(R.id.firstTxt);
        TextView lastName = (TextView) getView().findViewById(R.id.lastTxt);
        TextView fullName = (TextView) getView().findViewById(R.id.nameTxt);
        TextView email = (TextView) getView().findViewById(R.id.emailTxt);
        TextView phone = (TextView) getView().findViewById(R.id.phoneTxt);
        TextView address = (TextView) getView().findViewById(R.id.addressTxt);
        TextView gender = (TextView) getView().findViewById(R.id.genderTxt);
        Button logoutBtn = (Button) getView().findViewById(R.id.logoutBtn);
        Button edit = (Button) getView().findViewById(R.id.editProfile);
        Button save = (Button) getView().findViewById(R.id.saveProfile);
        ImageView avatar = (ImageView) getView().findViewById(R.id.avatar);


        //TODO: Edit Profile
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                firstName.setFocusableInTouchMode(true);
                lastName.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                phone.setFocusableInTouchMode(true);
                address.setFocusableInTouchMode(true);
                // TODO: Do sth to notify user when edit is clicked!
            }
        });
        //TODO: Save Profile
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                firstName.setFocusable(false);
                lastName.setFocusable(false);
                email.setFocusable(false);
                phone.setFocusable(false);
                address.setFocusable(false);
                //TODO: Do Update Function Here!
                User updatedUser = new User(email.getText().toString(), firstName.getText().toString(),
                        lastName.getText().toString(),phone.getText().toString(),address.getText().toString(),
                        gender.getText().toString());
                reference.child(uID).setValue(updatedUser);
                Toast.makeText(getContext(), "Update Success!", Toast.LENGTH_SHORT).show();

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    firstName.setText(userProfile.getFirstName());
                    lastName.setText(userProfile.getLastName());
                    fullName.setText(userProfile.getFirstName() + " " + userProfile.getLastName());
                    email.setText(userProfile.getEmail());
                    phone.setText(userProfile.getPhoneNumber());
                    address.setText(userProfile.getAddress());
                    gender.setText(userProfile.getGender());
                    //Check gender
                    if (userProfile.getGender().equals("Male")){
                        avatar.setImageResource(R.drawable.man);
                    }else {
                        avatar.setImageResource(R.drawable.female);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "SomeThing went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void updateUser(User updatedUser, DatabaseReference reference, String uId){
        reference.child("Users").child(uId).setValue(updatedUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}