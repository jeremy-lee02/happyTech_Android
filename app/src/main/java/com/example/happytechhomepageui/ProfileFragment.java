package com.example.happytechhomepageui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FirebaseUser user;
    DatabaseReference reference;
    String uID;
    TextView fullName, email, phone, address;
    Button logoutBtn;
    boolean isEdit = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ProfileFragment profileFragment = new ProfileFragment();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    firstName.setText(userProfile.getFirstName());
                    lastName.setText(userProfile.getLastName());
                    fullName.setText(userProfile.getFirstName() + " " + userProfile.getLastName());
                    email.setText(userProfile.getEmail());
                    phone.setText(userProfile.getPhoneNumber());
                    address.setText(userProfile.getAddress());
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