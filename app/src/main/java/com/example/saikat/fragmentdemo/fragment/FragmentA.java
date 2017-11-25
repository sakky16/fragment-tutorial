package com.example.saikat.fragmentdemo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.saikat.fragmentdemo.Communicator;
import com.example.saikat.fragmentdemo.MainActivity;
import com.example.saikat.fragmentdemo.R;

/**
 * Created by trisys on 25/11/17.
 */

public class FragmentA extends android.app.Fragment {
    Button button;
    int count=0;
    String hello;
    public Communicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.a_fragment_layout,container,false);
        button=(Button)view.findViewById(R.id.button);
        onClickListener();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            count=0;
        }
        else {
            count=savedInstanceState.getInt("counter");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MainActivity){
            communicator=(Communicator)activity;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",count);
    }

    private void onClickListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(communicator!=null){
                    count++;
                    communicator.respond("The button has clicked "+count+" times");
                }
            }
        });
    }
}
