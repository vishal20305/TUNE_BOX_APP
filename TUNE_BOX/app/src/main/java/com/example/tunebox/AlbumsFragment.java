package com.example.tunebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class AlbumsFragment extends Fragment {
    //taking instances of each of the type i.e. TextView, Button
    TextView te;
    Button b;

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //This will Inflate the layout for this fragment and now with help of instances "view" of type View we will set the value on layout top show
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        b = view.findViewById(R.id.button);
        te = view.findViewById(R.id.textView);
        te.setText("Welcome to TuneBox ");
        b.setOnClickListener(new View.OnClickListener() { //now by clicking on the button given on album fragment i.e. "download song" we will proceed to next Activity
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),Network.class);
                startActivity(in);

            }
        });
            return view;

    }
}