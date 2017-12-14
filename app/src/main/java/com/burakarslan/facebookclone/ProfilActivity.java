package com.burakarslan.facebookclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    TextView textViewName,textViewSurname,textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        textViewName=(TextView) findViewById(R.id.textViewName);
        textViewSurname=(TextView) findViewById(R.id.textViewSurname);
        Button buttonaddgrup=(Button) findViewById(R.id.buttongrupekle);
        textViewEmail=(TextView) findViewById(R.id.textViewEmail);

    }
    public void grupekle (View view)
    {
        Intent intent=new Intent(getApplicationContext(),GroupAddActivity.class);
        startActivity(intent);
    }
}
