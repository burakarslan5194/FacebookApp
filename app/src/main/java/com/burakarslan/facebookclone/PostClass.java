package com.burakarslan.facebookclone;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static com.burakarslan.facebookclone.R.id.imageView2;

/**
 * Created by Burak on 9/24/2017.
 */

public class PostClass extends ArrayAdapter<String> {

    private final ArrayList<String> userEmail;
    private final ArrayList<String> userComment;
    private final ArrayList<String> userImage;
    private  final Activity context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String s,keyfriend,uuidString,keyuser,friendemailtext;
    String emailText,arkadascontrol;

    public PostClass(ArrayList<String> userEmail,  ArrayList<String> userImage,ArrayList<String> userComment, FragmentAnasayfa context) {
        super(context.getActivity(),R.layout.custom_view,userEmail);
        this.userEmail = userEmail;
        this.userComment = userComment;
        this.userImage = userImage;
        this.context = context.getActivity();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.custom_view,null,true);

        final TextView userEmailText=(TextView) customView.findViewById(R.id.userName);
        TextView commentText=(TextView) customView.findViewById(R.id.commentText);
        ImageView imageView=(ImageView) customView.findViewById(imageView2);

        userEmailText.setText(userEmail.get(position));
        commentText.setText(userComment.get(position));


    /*   DatabaseReference mRef = firebaseDatabase.getReference().child("Users").child(keyuser).child("FriendRequests");
        getDataFromFirebase(mRef);
        arkadascontrol="Hayir";
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                    String keycontrol=ds.getKey();
                    if(keycontrol.equals(keyfriend))
                    {
                        friendemailtext=hashMap.get("friendEmail").toString();
                        if(friendemailtext.equals(emailText))
                        {
                            arkadascontrol="Evet";
                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





*/


        Picasso.with(context).load(userImage.get(position)).into(imageView);
        final String useremail=userEmailText.getText().toString();
        userEmailText.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context,FriendsProfile.class);
        intent.putExtra("Email",useremail);
      //  intent.putExtra("arkcontrolintent",arkadascontrol);
        context.startActivity(intent);
    }
});

        return customView;
    }


    protected void getDataFromFirebase(DatabaseReference mRef){

        //mRef=firebaseDatabase.getReference().child("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                    s=hashMap.get("useremail").toString();
                    String name=hashMap.get("name").toString();
                    String surname=hashMap.get("surname").toString();


                    String value=ds.getValue().toString();

                    FirebaseUser user=mAuth.getCurrentUser();
                    String userEmail=user.getEmail().toString();
                    if(s.equals(emailText))
                    {
                        keyfriend=ds.getKey();
                      //  textViewName.setText(name);
                        //textViewSurname.setText(surname);
                        //textViewEmail.setText(s);

                    }
                    if(s.equals(userEmail))
                    {
                        keyuser=ds.getKey();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}

