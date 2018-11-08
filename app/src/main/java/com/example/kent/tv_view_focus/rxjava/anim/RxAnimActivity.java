package com.example.kent.tv_view_focus.rxjava.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kent.tv_view_focus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Kent Song on 2018/11/8.
 */
public class RxAnimActivity extends AppCompatActivity {

    @BindView(R.id.target_img)
    ImageView mImgView;
    @BindView(R.id.btn_action1)
    Button btnAction1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_anim);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_action1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_action1:
                animCombo();
                break;
        }
    }


    private void animCombo() {

//        Completable.complete()
//                .andThen(enlarge(mImgView))
//                .andThen(shrink(mImgView))
//                .subscribe();


        float x = mImgView.getX();
        float y = mImgView.getY();
        Timber.d(">> x = %s", x);
        Timber.d(">> y = %s", y);

        Completable animAlpha0 = Completable.create(e ->
                mImgView.animate()
                        .alpha(0)
                        .setDuration(1000)
                        .withEndAction(e::onComplete));

        Completable animAlpha1 = Completable.create(e ->
                mImgView.animate()
                        .alpha(1)
                        .setDuration(1000)
                        .withEndAction(e::onComplete));

        Completable animMoveGo = Completable.create(e ->
                mImgView.animate()
                        .translationX(100)
                        .translationY(100)
                        .withStartAction(() -> Timber.d(">> anim3 subscribe"))
                        .withEndAction(() -> {
                            Timber.d(">> anim3 x = %s", mImgView.getX());
                            Timber.d(">> anim3 y = %s", mImgView.getY());
                            e.onComplete();
                        })
        );

        Completable animMoveBack = Completable.create(e ->
                mImgView.animate()
                        .translationX(0)
                        .translationY(0)
                        .withStartAction(() -> Timber.d(">> anim4 subscribe"))

                        .withEndAction(() -> {
                            Timber.d(">> anim4 x = %s", mImgView.getX());
                            Timber.d(">> anim4 y = %s", mImgView.getY());
                            e.onComplete();
                        })
        );


        Completable.complete()
                .andThen(Completable.mergeArray(animMoveGo, enlarge(mImgView)))
                .andThen(Completable.mergeArray(animMoveBack, shrink(mImgView)))
                .andThen(animAlpha0)
                .andThen(animAlpha1)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.d(">> rx 动画开始");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d(">> rx 动画完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(">> rx 动画错误");
                    }
                });
    }


    public Completable enlarge(final View view) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                Timber.d(">> enlarge subscribe");
                AnimatorSet animSet = new AnimatorSet();

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 2.1f, 2.08f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 2.1f, 2.08f);
                animSet.setDuration(1000);
                animSet.playTogether(scaleX, scaleY);
                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Timber.d(">> enlarge onAnimationEnd");
                        emitter.onComplete();
                    }
                });
                animSet.start();
            }
        });


    }

    public Completable shrink(final View view) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                Timber.d(">> shrink subscribe");

                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 2.08f, 0.99f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 2.08f, 0.99f, 1f);
                animSet.setDuration(1000);
                animSet.setInterpolator(new DecelerateInterpolator());
                animSet.play(scaleX).with(scaleY);
                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Timber.d(">> shrink onAnimationEnd");
                        emitter.onComplete();
                    }
                });
                animSet.start();
            }
        });
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, RxAnimActivity.class);
        context.startActivity(intent);
    }
}
