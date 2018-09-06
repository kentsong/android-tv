package com.example.kent.tv_view_focus.feature.animator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.feature3.Feature3Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Kent Song on 2018/6/18.
 */
public class AnimatorActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;

    ObjectAnimator animator1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
        animator1 = ObjectAnimator.ofFloat(button1, "rotation", 0f, 360f);
        animator1.setDuration(5000)
                .setRepeatCount(5);
        init();
    }

    private void init() {


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d(">> animator1.isPaused() = %s", animator1.isPaused());
                if (animator1.isPaused()) {
                    animator1.resume();
                    Timber.d(">>  animator1.resume() ");
                } else {
                    animator1.start();
                    Timber.d(">>  animator1.start() ");
                }


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d(">> animator1.isRunning() = %s", animator1.isRunning());
                animator1.cancel();
//                if (animator1.isRunning()) {
//                    animator1.pause();
//                    Timber.d(">>  animator1.pause() ");
//                } else {
//                    animator1.cancel();
//                    Timber.d(">>  animator1.cancel() ");
//                }
            }
        });


    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, AnimatorActivity.class);
        context.startActivity(intent);
    }
}
