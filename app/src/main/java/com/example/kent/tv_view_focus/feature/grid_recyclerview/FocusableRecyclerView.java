package com.example.kent.tv_view_focus.feature.grid_recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.example.kent.tv_view_focus.feature.grid_recyclerview.adapter.FocusableAdapter;


/**
 * Created by KentSong on 2018/6/11.
 * <p>
 * <p>
 * 通用型 RecyclerView
 * - 支持记忆焦点功能
 * - 焦点由 FocusableRecyclerView 控管
 * - 支持设置封闭水平、垂直移出 RecyclerView
 * <p>
 * <p>
 * 搭配 FocusableAdapter 使用
 *
 * @See {@link FocusableAdapter}
 */
public class FocusableRecyclerView extends RecyclerView {

    //布局方式
    @FocusableContract.LayoutMode
    private int mLayoutMode;

    //是否可以横向移出
    private boolean mCanFocusOutHorizontal;

    //是否可以纵向移出
    private boolean mCanFocusOutVertical;

    private FocusableAdapter mAdapter;
    private Integer mDefaultIndex;
    private int mCurrentIndex;


    public FocusableRecyclerView(Context context) {
        super(context);
        init();
    }

    public FocusableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FocusableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        this.mAdapter = (FocusableAdapter) getAdapter();

        if (mDefaultIndex != null) {
            this.mCurrentIndex = mDefaultIndex;
            onFocusItem(mCurrentIndex);
        }
    }

    public void setAdapter(Adapter adapter, @FocusableContract.LayoutMode int layoutMode) {
        this.mLayoutMode = layoutMode;
        setAdapter(adapter);
    }

    public void setDefaultIndex(Integer defaultIndex) {
        this.mDefaultIndex = defaultIndex;
    }

    private void onFocusItem(int index) {
        mAdapter.setItemFocused(index);
    }

    private void unFocusItem(int index) {
        mAdapter.setItemUnFocus(index);
    }

    public void setCanFocusOutHorizontal(boolean bln) {
        this.mCanFocusOutHorizontal = bln;
    }

    public void setCanFocusOutVertical(boolean bln) {
        this.mCanFocusOutVertical = bln;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        if (gainFocus) {
            onFocusItem(mCurrentIndex);
        } else {
            unFocusItem(mCurrentIndex);
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        boolean result;
        switch (mLayoutMode) {
            case FocusableContract.LINEARLAYOUT_HORIZONTAL:
                result = onKeyDownLinearLayoutHorizontal(keyCode);
                break;
            case FocusableContract.LINEARLAYOUT_VERTICAL:
                result = onKeyDownLinearLayoutVertical(keyCode);
                break;
            case FocusableContract.GRIDLAYOUT_HORIZONTAL:
                result = onKeyDownGirdLayoutHorizontal(keyCode);
                break;
            case FocusableContract.GRIDLAYOUT_VERTICAL:
                result = onKeyDownGirdLayoutVertical(keyCode);
                break;
            default:
                result = false;
                break;
        }

        return result || super.onKeyDown(keyCode, event);
    }

    private boolean onKeyDownLinearLayoutHorizontal(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutHorizontal) {
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
                    if (!mCanFocusOutHorizontal) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                return true;
        }

        return false;
    }

    private boolean onKeyDownLinearLayoutVertical(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutVertical) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutVertical) {
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    private boolean onKeyDownGirdLayoutHorizontal(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        int spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex < spanCount) {
                    return true;
                } else {
                    mCurrentIndex -= spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex + spanCount >= itemCount) {
                    return true;
                } else {
                    mCurrentIndex += spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
        }
        return false;
    }

    private boolean onKeyDownGirdLayoutVertical(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        int spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentIndex + spanCount >= itemCount) {
                    return true;
                } else {
                    mCurrentIndex += spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentIndex < spanCount) {
                    return true;
                } else {
                    mCurrentIndex -= spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
        }
        return false;
    }


    /**
     * 回调方法
     */
    public interface ItemFocusListener {

        void onItemFocused(int index);

    }


}
