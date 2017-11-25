package com.example.saikat.fragmentdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saikat.fragmentdemo.R;

/**
 * Created by trisys on 25/11/17.
 */

public class FragmentB extends android.app.Fragment {
    TextView textView;
    String data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.b_fragment_layout,container,false);
        textView=(TextView)view.findViewById(R.id.textView);
        if(savedInstanceState!=null){
            String data=savedInstanceState.getString("data");
            textView.setText(data);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data",data);
    }

    public void onChange(String data){
        this.data=data;
        textView.setText(data);
    }




}
