package com.example.kent.tv_view_focus.feature.keepfocus;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import timber.log.Timber;

public class CenterGridLayoutManger extends GridLayoutManager {


    public CenterGridLayoutManger(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        Timber.d(">> onInterceptFocusSearch() direction = %s, focused = %s", direction, focused);
        return super.onInterceptFocusSearch(focused, direction);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return CenterGridLayoutManger.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 0.2f;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }

}