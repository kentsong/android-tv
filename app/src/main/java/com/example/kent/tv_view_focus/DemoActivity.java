package com.example.kent.tv_view_focus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kent.tv_view_focus.feature.animator.AnimatorActivity;
import com.example.kent.tv_view_focus.feature.grid_recyclerview.GirdRvActivity;
import com.example.kent.tv_view_focus.feature.keepfocus.FocusKeepActivity;
import com.example.kent.tv_view_focus.feature2.Feature2Activity;
import com.example.kent.tv_view_focus.feature3.Feature3Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent Song on 2018/9/6.
 */
public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.btn_main)
    Button btnMain;
    @BindView(R.id.btn_feature2)
    Button btnFeature2;
    @BindView(R.id.btn_feature3)
    Button btnFeature3;
    @BindView(R.id.btn_animator)
    Button btnAnimator;
    @BindView(R.id.btn_grid_recyclerview)
    Button btnGridRecyclerview;
    @BindView(R.id.btn_keep_focus)
    Button btnKeepFocus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_main, R.id.btn_feature2, R.id.btn_feature3, R.id.btn_animator, R.id.btn_grid_recyclerview, R.id.btn_keep_focus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main:
                MainActivity.launch(this);
                break;
            case R.id.btn_feature2:
                Feature2Activity.launch(this);
                break;
            case R.id.btn_feature3:
                Feature3Activity.launch(this);
                break;
            case R.id.btn_animator:
                AnimatorActivity.launch(this);
                break;
            case R.id.btn_grid_recyclerview:
                GirdRvActivity.launch(this);
                break;
            case R.id.btn_keep_focus:
                FocusKeepActivity.launch(this);
                break;
        }
    }
}
