package com.burakarslan.facebookclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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


    String s;
    String emailText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    TextView textViewName,textViewSurname,textViewEmail;

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_profile);
        Intent intent = getIntent();
        emailText= intent.getStringExtra("Email");
        textViewName=(TextView) findViewById(R.id.textViewName);
        textViewSurname=(TextView) findViewById(R.id.textViewSurname);
        textViewEmail=(TextView) findViewById(R.id.textViewEmail);
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
                    s=hashMap.get("useremail").toString();
                    String name=hashMap.get("name").toString();
                    String surname=hashMap.get("surname").toString();

               String key2=ds.getKey();
                    String value=ds.getValue().toString();

                    if(s.equals(emailText))
                    {
                    textViewName.setText(name);
                        textViewSurname.setText(surname);
                        textViewEmail.setText(s);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
