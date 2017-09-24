package com.burakarslan.facebookclone;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class FeedActivity extends AppCompatActivity {

    SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

            sectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
        viewPager=(ViewPager) findViewById(R.id.container);
        setupPager(viewPager);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);

            }


            private void setupPager(ViewPager viewPager){
                SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
                adapter.addFragment(new FragmentAnasayfa(),"Anasayfa");
                adapter.addFragment(new FragmentBildirimler(),"Bildirimler");
                adapter.addFragment(new FragmentProfil(),"Profil");
                viewPager.setAdapter(adapter);

            }
    }




