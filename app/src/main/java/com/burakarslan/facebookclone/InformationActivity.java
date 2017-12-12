package com.burakarslan.facebookclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class InformationActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText editTextName;
    EditText editTextSurname;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String email,password,name,surname,uuidString;
    ImageView imageView;
    Uri selected;


    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        editTextName=(EditText) findViewById(R.id.editTextname);
        editTextSurname=(EditText) findViewById(R.id.editTextSurname);
        imageView=(ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        UUID uuid = UUID.randomUUID();
        uuidString = uuid.toString();

        selected = Uri.parse("android.resource://com.burakarslan.facebookclone/drawable/profile.jpg");
        try {
            InputStream stream = getContentResolver().openInputStream(selected);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void Register(View view) {



        name = editTextName.getText().toString();
        surname = editTextSurname.getText().toString();
        myRef.child("Users").child(uuidString).child("useremail").setValue(email);
        myRef.child("Users").child(uuidString).child("password").setValue(password);
       // myRef.child("Users").child(uuidString).child("Friends");
     //   myRef.child("Users").child(uuidString).child("FriendsRequest");

        myRef.child("Users").child(uuidString).child("name").setValue(name);
        myRef.child("Users").child(uuidString).child("surname").setValue(surname);

        Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
        startActivity(intent);

        /*UUID uuidImage = UUID.randomUUID();
        String imageName = "images/" + uuidImage + ".jpg";
        StorageReference storageReference = mStorageRef.child(imageName);
        storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                String downloadURL = taskSnapshot.getDownloadUrl().toString();





                myRef.child("Users").child(uuidString).child("profilepicURL").setValue(downloadURL);


                Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });*/


    }}