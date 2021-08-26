package com.example.tunebox;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity  {
    Button start,stop;
    Rece mR = new Rece(); //instance of class "Rece" is created as "mR"
    IntentFilter f = new IntentFilter(); //object of "IntentFilter"is created as "f"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initV();
        stop = findViewById(R.id.stop);//for button stop
        start= findViewById(R.id.play);//for button start

        start.setOnClickListener(new View.OnClickListener() {  //on clicking on stop the foreground service will be started and also song started playing
            @Override
            public void onClick(View v) {
//                registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                Intent intent = new Intent(MainActivity.this, MyForeService.class);
                intent.setAction(MyForeService.SER_START);
                startService(intent);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() { //on clicking on stop the foreground service will be started and also song started playing
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyForeService.class);
                intent.setAction(MyForeService.SER_STOP);
                startService(intent);
            }
        });
        f.addAction(Intent.ACTION_BATTERY_LOW);//action is added for Battery low notification
        f.addAction(Intent.ACTION_BATTERY_OKAY);//action is added for Battery Okay notification
        f.addAction(Intent.ACTION_POWER_DISCONNECTED);//action is added for Battery Disconnected notification
        f.addAction(Intent.ACTION_POWER_CONNECTED);//action is added for Battery Connected notification

        MainActivity.this.registerReceiver(mR, f);//Registering the the Broadcast receiver
    }
    private void initV(){
        ViewPager pager=findViewById(R.id.vp);//getting value of ViewPager from it's id given on layout by using findViewById(R.id.vp)
        TabLayout tabLayout=findViewById(R.id.tl); //getting value of TabLayout from it's id given on layout by using findViewById(R.id.tl)
        ViewPagerAdapter viewPagerAdpter=new ViewPagerAdapter(getSupportFragmentManager());//creating an instance of ViewPagerAdapter as "viewPagerAdapter"
        viewPagerAdpter.addFrag(new SongsFragment(),"LIST OF SONGS"); //Now calling "addFrag" method  to add the fragment and set title of each fragment here title is "LIST OF SONGS"
        viewPagerAdpter.addFrag(new AlbumsFragment(),"Playing"); //Now calling "addFrag" method  to add the fragment and set title of each fragment here title is "Playing"
        pager.setAdapter(viewPagerAdpter); //Setting the viewPageAdapter
        tabLayout.setupWithViewPager(pager);//Setting the Tablayout

    }

    public static class  ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments; //this list is taken to add fragment
        private ArrayList<String> titles; //this list is taken to add titlte of each fragment

        public ViewPagerAdapter(@NonNull FragmentManager fragm) {//constructor of ViewPagerAdapter
            super(fragm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        //getPageTitle will help to show the page title
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        void addFrag(Fragment fragment,String title){ /*this method will do two things first it will add the fragment and secondly it will set the page title */
            fragments.add(fragment);
            titles.add(title);

        }
        @NonNull
        @Override
        public Fragment getItem(int position) { //this method will get the position of the fragment
            return fragments.get(position);
        }
        //getCount will return the total number of fragments
        @Override
        public int getCount() {
            return fragments.size();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.this.unregisterReceiver(mR); //Unregistering the Broadcast receiver
    }
}