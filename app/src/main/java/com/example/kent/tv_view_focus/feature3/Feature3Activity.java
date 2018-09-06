package com.example.kent.tv_view_focus.feature3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.feature2.Feature2Activity;

/**
 * Created by Kent Song on 2018/8/15.
 */
public class Feature3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature3);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, Feature3Activity.class);
        context.startActivity(intent);
    }
}
