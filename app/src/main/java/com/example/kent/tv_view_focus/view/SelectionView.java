package com.example.kent.tv_view_focus.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

public class SelectionView extends LinearLayout {

    private final String TAG = SelectionView.class.getSimpleName();


    private static final int ITEM_SIZE = 10;
    private LabelView[] mItems;
    private int mCurrentIndex = -1;
    private int mCurrentLen = 10;



    public SelectionView(Context context) {
        super(context);
        init();
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {
        setOrientation(HORIZONTAL);
        setFocusable(true);
        setFocusableInTouchMode(true);

        mCurrentIndex = 0;

        mItems = new LabelView[ITEM_SIZE];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
        LinearLayout.LayoutParams layoutParamsGap = new LinearLayout.LayoutParams(50, 50);
        for (int i = 0; i < ITEM_SIZE; i++) {
            mItems[i] = new LabelView(getContext());
            mItems[i].setFocusable(false);
            mItems[i].setFocusableInTouchMode(false);
            mItems[i].setText(""+i);
//            mItems[i].setDefaultBackgroundColor(Color.argb(102, 129, 129, 129));
            if (i > 0) {
                mItems[i].setLayoutParams(layoutParamsGap);
            } else {
                mItems[i].setLayoutParams(layoutParams);
            }
            addView(mItems[i]);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, ">> SelectionView, onKeyDown KeyEvent = "+event.getAction()+", keyCode = "+keyCode );
        Log.d(TAG, ">> SelectionView, mCurrentIndex before = "+mCurrentIndex);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                if (mCurrentIndex < mCurrentLen - 1) {
                    mItems[mCurrentIndex].onFocusChanged(false);
                    mCurrentIndex++;
                    mItems[mCurrentIndex].onFocusChanged(true);

                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex > 0) {
                    mItems[mCurrentIndex].onFocusChanged(false);
                    mCurrentIndex--;
                    mItems[mCurrentIndex].onFocusChanged(true);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                for(LabelView v : mItems){
                    v.unSelected();
                }
                mItems[mCurrentIndex].onSelected();
                break;
        }
        Log.d(TAG, ">> SelectionView, mCurrentIndex after = "+mCurrentIndex);

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Log.d(TAG, ">> SelectionView, onFocusChanged focused = "+gainFocus+", direction = "+direction );
        if (gainFocus) {
            mItems[mCurrentIndex].onFocusChanged(true);
//            if (mOnItemFocusedListener != null) {
//                mOnItemFocusedListener.onItemFocused(mItems[mCurrentIndex], mCurrentIndex);
//            }
        } else {
            mItems[mCurrentIndex].onFocusChanged(false);
        }

        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

}
