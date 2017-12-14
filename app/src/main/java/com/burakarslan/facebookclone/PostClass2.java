package com.burakarslan.facebookclone;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import static com.burakarslan.facebookclone.R.id.imageView2;

/**
 * Created by taner on 12/14/17.
 */

public class PostClass2 extends ArrayAdapter<String> {

    private final ArrayList<String> groups;

  //  private final ArrayList<String> yönetici;
    private final Activity context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String s, keyfriend, uuidString, keyuser, friendemailtext;
    String emailText, arkadascontrol;
Button btn;
    public PostClass2(ArrayList<String> groups, FragmentAnasayfa context) {
        super(context.getActivity(), R.layout.custom_view, groups);
        this.groups = groups;

        this.context = context.getActivity();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view2, null, true);


        TextView groupadi = (TextView) customView.findViewById(R.id.listgroupadi);
btn=(Button)customView.findViewById(R.id.buttoninvitegroup);

        groupadi.setText(groups.get(position));
final String swap=groups.get(position);
     //

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth= FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String userEmailadd = user.getEmail().toString();
                firebaseDatabase= FirebaseDatabase.getInstance();
                myRef=firebaseDatabase.getReference();
                myRef.child("Groups").child(swap).child("Üye").setValue(userEmailadd);
                //  myRef.child("Groups").child(uuidString).child("Yönetici").setValue(userEmail);
            }
        });


        return customView;
    }

}