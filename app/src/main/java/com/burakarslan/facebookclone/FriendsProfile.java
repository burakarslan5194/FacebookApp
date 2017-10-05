package com.burakarslan.facebookclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class FriendsProfile extends AppCompatActivity {


    String s,emailText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_profile);
        Intent intent = getIntent();
        emailText= intent.getStringExtra("Email");
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        getDataFromFirebase();

    }

    protected void getDataFromFirebase(){

        DatabaseReference newReferance=firebaseDatabase.getReference("Users");
        newReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();

               String key2=ds.getKey();
                    String value=ds.getValue().toString();

                    if(s==emailText)
                    {

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
