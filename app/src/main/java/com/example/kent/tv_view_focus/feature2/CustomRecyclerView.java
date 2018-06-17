package com.example.kent.tv_view_focus.feature2;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import timber.log.Timber;

public class CustomRecyclerView extends RecyclerView {

    private int mCurrentIndex = 0;
    private int mItemCount;
    private ChannelAdapter mAdapter;


    //是否可以横向移出
    private boolean mCanFocusOutHorizontal;
    //焦点移出recyclerview的事件监听
    private FocusLostListener mFocusLostListener;
    //焦点移入recyclerview的事件监听
    private FocusGainListener mFocusGainListener;
    //默认第一次选中第一个位置
    private int mCurrentFocusPosition = 0;


    public CustomRecyclerView(Context context) {
        super(context);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setItemAnimator(null);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.mAdapter = (ChannelAdapter) getAdapter();
        this.mItemCount = mAdapter.getItemCount();
    }

    private void onFocusItem(int index) {
        mAdapter.setItemFocused(index);
    }

    private void unFocusItem(int index) {
        mAdapter.setItemUnFocus(index);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Timber.d(">> onKeyDown keyCode = %s", keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex < mItemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                } else {
                    if(!mCanFocusOutHorizontal){
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                } else {
                    if(!mCanFocusOutHorizontal){
                        return true;
                    }
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Timber.d(">> onFocusChanged gainFocus = %s, direction = %s", gainFocus, direction);
        if (gainFocus) {
            onFocusItem(mCurrentIndex);
        } else {
            unFocusItem(mCurrentIndex);
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }





    public void setFocusLostListener(FocusLostListener focusLostListener) {
        this.mFocusLostListener = focusLostListener;
    }

    public interface FocusLostListener {
        void onFocusLost(View lastFocusChild, int direction);
    }

    public void setGainFocusListener(FocusGainListener focusListener) {
        this.mFocusGainListener = focusListener;
    }


    public interface FocusGainListener {
        void onFocusGain(View child, View focued);
    }







}
