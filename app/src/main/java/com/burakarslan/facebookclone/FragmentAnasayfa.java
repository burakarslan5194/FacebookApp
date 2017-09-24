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

/**
 * Created by Burak on 9/24/2017.
 */

public class FragmentAnasayfa extends Fragment {

    TextView textView;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_anasayfa,container,false);
        textView=(TextView) view.findViewById(R.id.textView);
        button=(Button) view.findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UploadActivity.class);
                startActivity(intent);
            }
        });

        return view;



    }

}
