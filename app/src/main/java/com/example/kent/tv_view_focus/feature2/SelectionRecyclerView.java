package com.example.kent.tv_view_focus.feature2;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import timber.log.Timber;


public class SelectionRecyclerView extends RecyclerView {
    public SelectionRecyclerView(Context context) {
        super(context);
    }

    public SelectionRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectionRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setSelected(int pos) {
        ViewHolder vh = findViewHolderForAdapterPosition(pos);
        requestFocusFromTouch();
        if (vh != null)
            vh.itemView.requestFocus();
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        Timber.d(">> onRequestFocusInDescendants direction = %s ", direction);
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Timber.d(">> onFocusChanged gainFocus = %s ", gainFocus);
//        ViewHolder vh = findViewHolderForAdapterPosition(0);
//        requestFocusFromTouch();
//        if (vh != null)
//            vh.itemView.requestFocus();
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }
}
