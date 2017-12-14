package com.burakarslan.facebookclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

/**
 * Created by Burak on 9/24/2017.
 */

public class FragmentProfil extends Fragment {

    TextView textView;
     Button buttongrupekle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profil,container,false);
        textView=(TextView) view.findViewById(R.id.textView);
buttongrupekle=(Button)view.findViewById(R.id.buttonaddgrup);
        buttongrupekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),GroupAddActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
}
