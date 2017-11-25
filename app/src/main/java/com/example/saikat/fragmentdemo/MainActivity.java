package com.example.saikat.fragmentdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.saikat.fragmentdemo.fragment.FragmentA;
import com.example.saikat.fragmentdemo.fragment.FragmentB;

public class MainActivity extends AppCompatActivity implements Communicator {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public void respond(String data) {
        FragmentManager manager=getFragmentManager();
        FragmentB fragmentB=(FragmentB)manager.findFragmentById(R.id.fragment2);
        fragmentB.onChange(data);
    }
}
