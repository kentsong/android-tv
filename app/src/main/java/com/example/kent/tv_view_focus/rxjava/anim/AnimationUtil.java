package com.example.kent.tv_view_focus.rxjava.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;


/**
 * 动画工具
 * Created by andyliu on 2018/3/22.
 */

public class AnimationUtil {

    private static AnimationUtil sAnimationUtil;

    private AnimatorSet mEnlargeAnimatorSet;
    private AnimatorSet mShrinkAnimatorSet;

    private AnimationUtil() {
    }

    public static AnimationUtil getInstance() {
        if (sAnimationUtil == null) {
            sAnimationUtil = new AnimationUtil();
        }
        return sAnimationUtil;
    }

    /**
     * 放大
     *
     * @param view
     */
    public void enlarge(View view) {
        if (mEnlargeAnimatorSet == null) {
            mEnlargeAnimatorSet = new AnimatorSet();
        }
        if (!mEnlargeAnimatorSet.isStarted()) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1.08f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1.08f);
            mEnlargeAnimatorSet.setDuration(800);
            mEnlargeAnimatorSet.setInterpolator(new DecelerateInterpolator());
            mEnlargeAnimatorSet.play(scaleX).with(scaleY);
            mEnlargeAnimatorSet.start();
            mEnlargeAnimatorSet = null;
        }
    }

    /**
     * 视差放大(添加PivotX与PivotY，修改动画轴)
     *
     * @param view
     */
    public void enlargeParallax(View view) {
        if (mEnlargeAnimatorSet == null) {
            mEnlargeAnimatorSet = new AnimatorSet();
        }
        if (!mEnlargeAnimatorSet.isStarted()) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.12f, 1.08f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.12f, 1.08f);
            view.setPivotX(view.getMeasuredWidth() / 2);
            view.setPivotY(view.getMeasuredHeight());
            mEnlargeAnimatorSet.setDuration(800);
            mEnlargeAnimatorSet.setInterpolator(new DecelerateInterpolator());
            mEnlargeAnimatorSet.play(scaleX).with(scaleY);
            mEnlargeAnimatorSet.start();
            mEnlargeAnimatorSet = null;
        }
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void shrink(View view) {
        if (mShrinkAnimatorSet == null) {
            mShrinkAnimatorSet = new AnimatorSet();
        }
        if (!mShrinkAnimatorSet.isStarted()) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.08f, 0.99f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.08f, 0.99f, 1f);
            mShrinkAnimatorSet.setDuration(800);
            mShrinkAnimatorSet.setInterpolator(new DecelerateInterpolator());
            mShrinkAnimatorSet.play(scaleX).with(scaleY);
            mShrinkAnimatorSet.start();
            mShrinkAnimatorSet = null;
        }

    }

    /**
     * 视差缩小(添加PivotX与PivotY，修改动画轴)
     *
     * @param view
     */
    public void shrinkParallax(View view) {
        if (mShrinkAnimatorSet == null) {
            mShrinkAnimatorSet = new AnimatorSet();
        }
        if (!mShrinkAnimatorSet.isStarted()) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.08f, 0.99f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.08f, 0.99f, 1f);
            mShrinkAnimatorSet.setDuration(800);
            mShrinkAnimatorSet.setInterpolator(new DecelerateInterpolator());
            mShrinkAnimatorSet.play(scaleX).with(scaleY);
            mShrinkAnimatorSet.start();
            mShrinkAnimatorSet = null;
        }

    }

    /**
     * 通过 Animator 执行 TranslationY
     *
     * @param view
     * @param values
     */
    public void translationY(View view, float... values) {
        ObjectAnimator mTranslationYAnimator = ObjectAnimator.ofFloat(view, "translationY", values);
        if (!mTranslationYAnimator.isStarted()) {
            mTranslationYAnimator.setDuration(500);
            mTranslationYAnimator.setInterpolator(new DecelerateInterpolator());
            mTranslationYAnimator.start();
            mTranslationYAnimator = null;
        }
    }

    public void translationY(View view, long duration, TimeInterpolator interpolator, float... values) {
        translationY(view, duration, interpolator, null, values);
    }

    public void translationY(View view, long duration, TimeInterpolator interpolator, Animator.AnimatorListener listener, float... values) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationY", values);

        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX); //设置动画
        animatorSet.setDuration(duration);  //设置动画时间
        animatorSet.setInterpolator(interpolator);
        if (listener != null) {
            animatorSet.addListener(listener);
        }
        animatorSet.start();
    }

    public void alpha(View view, float alpha, int duration, Interpolator interpolator) {
        alpha(view, alpha, duration, interpolator, null);
    }

    public void alpha(View view, float alpha, int duration, Interpolator interpolator, Animator.AnimatorListener listener) {
        if (view == null) {
            return;
        }
        view.animate()
                .alpha(alpha)
                .setDuration(duration)
                .setInterpolator(interpolator)
                .setListener(listener)
                .start();
    }

    public AnimatorSet createCategoryShowAnimSet(View category, View channel, View channelProgramme, View categoryBg, View channelBg, View channelProgrammeBg) {
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(category, "translationX", -820f, 0f),
                ObjectAnimator.ofFloat(categoryBg, "translationX", -820f, 0f),
                ObjectAnimator.ofFloat(channel, "translationX", -820f, 0f),
                ObjectAnimator.ofFloat(channelBg, "translationX", -820f, 0f),
                ObjectAnimator.ofFloat(channelProgramme, "translationX", -820f, 0f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "translationX", -820f, 0f));
        animatorSet.setDuration(180);

        return animatorSet;
    }

    public AnimatorSet createCategoryHideAnimSet(View category, View channel, View channelProgramme, View categoryBg, View channelBg, View channelProgrammeBg) {
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(category, "translationX", 0f, -820f),
                ObjectAnimator.ofFloat(categoryBg, "translationX", 0f, -820f),
                ObjectAnimator.ofFloat(channel, "translationX", 0f, -820f),
                ObjectAnimator.ofFloat(channelBg, "translationX", 0f, -820f),
                ObjectAnimator.ofFloat(channelProgramme, "translationX", 0f, -820f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "translationX", 0f, -820f));

        animatorSet.setDuration(200);

        return animatorSet;
    }

    public AnimatorSet createChannelProgrammeShowAnim(View category, View channel, View channelProgramme, View categoryBg, View channelBg, View channelProgrammeBg, View arrow, View focusBorder, ValueAnimator.AnimatorUpdateListener categoryValueUpdateListener, ValueAnimator.AnimatorUpdateListener channelValueUpdateListener) {
        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator categoryValueAnimator = ValueAnimator.ofInt(0xcc464b56, 0xcc474954, 0xcc434552, 0xcc3E424F, 0xcc3A3E4D, 0xcc363a4b);
        categoryValueAnimator.setEvaluator(new ArgbEvaluator());
        categoryValueAnimator.addUpdateListener(categoryValueUpdateListener);

        ValueAnimator channelValueAnimator = ValueAnimator.ofInt(0x7F808080, 0xcc757678, 0xcc6B6C6F, 0xcc606167, 0xcc56575E, 0xcc464b56);
        channelValueAnimator.setEvaluator(new ArgbEvaluator());
        channelValueAnimator.addUpdateListener(channelValueUpdateListener);

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(category, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(categoryBg, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(channel, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(channelBg, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(channelProgramme, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(channelProgramme, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(arrow, "translationX", 0f, -260f),
                ObjectAnimator.ofFloat(arrow, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(focusBorder, "translationX", 0f, 40f),
                categoryValueAnimator,
                channelValueAnimator);

        animatorSet.setDuration(200);

        return animatorSet;
    }

    public AnimatorSet createChannelProgrammeHideAnim(View category, View channel, View channelProgramme, View categoryBg, View channelBg, View channelProgrammeBg, View focusBorder, ValueAnimator.AnimatorUpdateListener categoryUpdateListener, ValueAnimator.AnimatorUpdateListener channelUpdateListener) {
        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator categoryValueAnimator = ValueAnimator.ofInt(0xcc363a4b, 0xcc3A3E4D, 0xcc3E424F, 0xcc434552, 0xcc474954, 0xcc464b56);
        categoryValueAnimator.setEvaluator(new ArgbEvaluator());
        categoryValueAnimator.addUpdateListener(categoryUpdateListener);

        ValueAnimator channelValueAnimator = ValueAnimator.ofInt(0xcc464b56, 0xcc56575E, 0xcc606167, 0xcc6B6C6F, 0xcc757678, 0x7F808080);
        channelValueAnimator.setEvaluator(new ArgbEvaluator());
        channelValueAnimator.addUpdateListener(channelUpdateListener);

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(category, "translationX", -260f, 0f),
                ObjectAnimator.ofFloat(categoryBg, "translationX", -260f, 0f),
                ObjectAnimator.ofFloat(channel, "translationX", -260f, 0f),
                ObjectAnimator.ofFloat(channelBg, "translationX", -260f, 0f),
                ObjectAnimator.ofFloat(channelProgramme, "translationX", -260f, 0f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "translationX", -260f, 0f),
                ObjectAnimator.ofFloat(channelProgramme, "alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(focusBorder, "translationX", 0f, 300f),
                categoryValueAnimator,
                channelValueAnimator);

        animatorSet.setDuration(200);

        return animatorSet;
    }

    public AnimatorSet createGroupHideAnim(View category, View channel, View channelProgramme, View categoryBg, View channelBg, View channelProgrammeBg, View arrow) {
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(category, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(categoryBg, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(channel, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(channelBg, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(channelProgramme, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(arrow, "translationX", -260f, -1160f),
                ObjectAnimator.ofFloat(channelProgramme, "alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(channelProgrammeBg, "alpha", 1.0f, 0.0f));

        animatorSet.setDuration(200);

        return animatorSet;
    }

//    public AnimatorSet createGroupAlphScale(View view, boolean inOrOut, int alphaFrom, int alphaTo, int durationAlpha, float durationScaleY, Animator.AnimatorListener listener) {
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", alphaFrom, alphaTo);
//        animatorAlpha.setDuration(durationAlpha);
//        animatorAlpha.setInterpolator(new DecelerateInterpolator());
//        ValueAnimator valueAnimator;
//        if (inOrOut) {
//            valueAnimator = ValueAnimator.ofInt(UiUtil.getHeight(view.getContext(), 85), 0);
//        } else {
//            valueAnimator = ValueAnimator.ofInt(0, UiUtil.getHeight(view.getContext(), 85));
//        }
//        valueAnimator.setDuration((long) durationScaleY);
//        valueAnimator.setInterpolator(new DecelerateInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
//                Timber.d("filter/animation_scaleY/view.getLayoutParams().height=" + view.getLayoutParams().height);
//                view.requestLayout();
//            }
//        });
//        if (inOrOut) {
//            animatorSet.playSequentially(animatorAlpha, valueAnimator);
//        } else {
//            animatorSet.playSequentially(valueAnimator, animatorAlpha);
//        }
//        animatorSet.addListener(listener);
//
//        return animatorSet;
//    }

//    public AnimatorSet createFocusBorderHideAnim(FocusBorderView view) {
//        AnimatorSet animatorSet = new AnimatorSet();
//
//        animatorSet.playTogether(
//                ObjectAnimator.ofFloat(view, "x", 0),
//                ObjectAnimator.ofFloat(view, "y", 0),
//                ObjectAnimator.ofInt(view, "width", 0),
//                ObjectAnimator.ofInt(view, "height", 0),
//                ObjectAnimator.ofInt(view, "visibility", View.GONE));
//
//        return animatorSet;
//    }

    public void playTogether(int duration, Animator... animator) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animator);
        animatorSet.start();
    }


}
