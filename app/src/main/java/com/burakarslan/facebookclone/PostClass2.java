package com.burakarslan.facebookclone;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    String s, keyfriend, uuidString, keyuser, friendemailtext,swap;
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
 swap=groups.get(position);
     //



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase= FirebaseDatabase.getInstance();
                myRef=firebaseDatabase.getReference();
                mAuth= FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
               String uye="Üye";
                String userEmailadd = user.getEmail().toString();
                String uyeler="Üyeler";
              //  String uyeSayisi=myRef.child("Groups").child(swap).child("Uye Sayisi").getKey();
              //  int uyesayisiInt=Integer.parseInt(uyeSayisi);
                //uyesayisiInt=uyesayisiInt+1;
                //String uyesayisiString=Integer.toString(uyesayisiInt);
                //myRef.child("Groups").child(swap).child("Uye Sayisi").setValue(uyesayisiString);

                myRef.child("Groups").child(swap).child("Üye").setValue(userEmailadd);
                //  myRef.child("Groups").child(uuidString).child("Yönetici").setValue(userEmail);
            }
        });


        return customView;
    }

}