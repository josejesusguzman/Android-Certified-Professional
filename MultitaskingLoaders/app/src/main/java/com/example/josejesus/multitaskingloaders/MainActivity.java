package com.example.josejesus.multitaskingloaders;

import android.support.v4.app.FragmentManager; //SE USA LA SUPPORT V4
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CursorLoaderListFragment listFragment = new CursorLoaderListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager(); //SE USA LA SUPPORT V4
        fragmentManager.beginTransaction().add(android.R.id.content, listFragment).commit();


    }
}
