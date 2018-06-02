package com.example.kent.tv_view_focus.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.kent.tv_view_focus.R;

import timber.log.Timber;


@SuppressLint("AppCompatCustomView")
public class LabelView extends TextView{

    /**
     * 是否被选中
     */
    private boolean isSelected;
    /**
     * 是否获得焦点
     */
    private boolean isFocused;

    public LabelView(Context context) {
        super(context);
        init();
    }

    public LabelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        setTextColor(getResources().getColor(R.color.colorPrimary));
        setBackgroundColor(getResources().getColor(R.color.unfocus_bg));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LabelView", ">> Item "+getText()+", onClick hit");
                isSelected = true;
            }
        });
    }

    @Override
    public boolean isSelected() {
        return super.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        isSelected = selected;
        onStateChange();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        Log.d("LabelView", ">> Item "+getText()+", onFocusChanged focused = "+focused+", direction = "+direction );
        if(focused){
            setTextColor(getResources().getColor(R.color.focus));
            setBackgroundColor(getResources().getColor(R.color.focus_bg));
        } else{

            if(isSelected){
                setTextColor(getResources().getColor(R.color.colorAccent));
                setBackgroundColor(getResources().getColor(R.color.unfocus_bg));
            }else{
                setTextColor(getResources().getColor(R.color.colorPrimary));
                setBackgroundColor(getResources().getColor(R.color.unfocus_bg));
            }

        }


    }


    public void onFocusChanged(boolean focused) {
        isFocused = focused;
        onStateChange();

    }

    private void onStateChange(){
//        Timber.d(">> Item "+getText()+", onStateChange isFocused = %s, isSelected = %s",isFocused,  isSelected);
        if (isFocused) {
            setTextColor(getResources().getColor(R.color.focus));
            setBackgroundColor(getResources().getColor(R.color.focus_bg));
        } else {
            if (isSelected) {
                setTextColor(getResources().getColor(R.color.colorAccent));
                setBackgroundColor(getResources().getColor(R.color.unfocus_bg));
            } else {
                setTextColor(getResources().getColor(R.color.colorPrimary));
                setBackgroundColor(getResources().getColor(R.color.unfocus_bg));
            }
        }
    }

    public void onSelected(){
        Log.d("LabelView", ">> Item "+getText()+", onClick hit");
        isSelected = true;
        setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void unSelected(){
        isSelected = false;
        setTextColor(getResources().getColor(R.color.colorPrimary));
    }


}
