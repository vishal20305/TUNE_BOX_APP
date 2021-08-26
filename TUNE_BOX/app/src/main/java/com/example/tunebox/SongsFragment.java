package com.example.tunebox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;


public class SongsFragment extends Fragment {
    public SongsFragment() {
        // Required empty public constructor
    }

    ListView l;
    ArrayList<String> lst;
    ArrayAdapter slist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_songs, container, false);
        l=view.findViewById(R.id.layout);
        lst=new ArrayList<String>();
        Field[] f=R.raw.class.getFields();
        for(int i=0;i<f.length;i++){
            lst.add(f[i].getName());

        }
        slist = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lst);
        l.setAdapter(slist);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (m != null) {
//                    m.release();
//                }
//
//              //  int resId = getResources().getIdentifier(lst.get(position), "raw", getActivity().getPackageName());
//                int resId = getResources().getIdentifier(lst.get(position), "raw", getActivity().getPackageName());
//                m = MediaPlayer.create(getActivity(), resId);
//
//                m.start();
            }
        });
        return view;
    }
}
