package com.burakarslan.facebookclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak on 9/24/2017.
 */

public class FragmentAnasayfa extends Fragment {

    ArrayList<String> useremailsFromFB;
    ArrayList<String> groupsFromFB;
    ArrayList<String> userimagesFromFB;
    ArrayList<String> usercommentsFromFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    PostClass adapter;
    PostClass2 adapter2;
    ListView listView,listView2;

    TextView textView;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_anasayfa,container,false);
        useremailsFromFB=new ArrayList<String>();
        usercommentsFromFB=new ArrayList<String>();
        userimagesFromFB=new ArrayList<String>();
        groupsFromFB=new ArrayList<String>();

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        adapter= new PostClass(useremailsFromFB,userimagesFromFB,usercommentsFromFB,this);
adapter2=new PostClass2(groupsFromFB,this);
        listView=(ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView2=(ListView) view.findViewById(R.id.listView2);
        listView2.setAdapter(adapter2);


        textView=(TextView) view.findViewById(R.id.textView);
        button=(Button) view.findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UploadActivity.class);
                startActivity(intent);
            }
        });
        getDataFromFirebase();
        getDataFromFirebase2();
        return view;



    }


    protected void getDataFromFirebase(){

        DatabaseReference newReferance=firebaseDatabase.getReference("Posts");
        newReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                    useremailsFromFB.add(hashMap.get("useremail"));
                    userimagesFromFB.add(hashMap.get("downloadURL"));
                    usercommentsFromFB.add(hashMap.get("comment"));
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    protected void getDataFromFirebase2(){

        DatabaseReference newReferance=firebaseDatabase.getReference("Groups");
        newReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                  groupsFromFB.add(hashMap.get("Groupname"));

                    adapter2.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
