package com.burakarslan.facebookclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class GroupAddActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    Button buttongrupadd;
    EditText textadi;
    String ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        textadi=(EditText) findViewById(R.id.editTextgroupadi) ;

        mAuth= FirebaseAuth.getInstance();
       // mStorageRef= FirebaseStorage.getInstance().getReference();
 buttongrupadd=(Button)findViewById(R.id.buttongrupolustur);
buttongrupadd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        ad= textadi.getText().toString();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail().toString();
        String uyeSayisi="0";
        myRef.child("Groups").child(ad).child("Groupname").setValue(ad);
        myRef.child("Groups").child(ad).child("Yönetici").setValue(userEmail);
        myRef.child("Groups").child(ad).child("Uye Sayisi").setValue(uyeSayisi);
        Toast.makeText(getApplicationContext(), "Grup Oluşturuldu", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
        startActivity(intent);

    }
});


    }
}
