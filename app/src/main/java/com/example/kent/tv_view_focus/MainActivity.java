package com.example.kent.tv_view_focus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kent.tv_view_focus.view.SelectionView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SelectionView sView = findViewById(R.id.selectionView);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
