package com.burakarslan.facebookclone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class InformationActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText emailText;
    EditText passwordText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();


    }
    public void Register()
    {
        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){



                            UUID uuid = UUID.randomUUID();
                            String uuidString = uuid.toString();

                            String email=emailText.getText().toString();
                            String password2=passwordText.getText().toString();
                            myRef.child("Users").child(uuidString).child("useremail").setValue(email);
                            myRef.child("Users").child(uuidString).child("password").setValue(password2);
                            myRef.child("Users").child(uuidString).child("Friends");
                            myRef.child("Users").child(uuidString).child("FriendsRequest");

                            Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),FeedActivity.class);
                            startActivity(intent);
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
