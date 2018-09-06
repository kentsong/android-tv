package com.example.kent.tv_view_focus.feature.keepfocus;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.view.LabelView;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by Kent Song on 2018/6/22.
 */
public class FocusKeepRecyclerView extends RecyclerView {

    private static final String TAG = FocusKeepRecyclerView.class.getSimpleName();
    //是否可以纵向移出
    private boolean mCanFocusOutVertical = true;
    //是否可以横向移出
    private boolean mCanFocusOutHorizontal = true;
    //焦点移出recyclerview的事件监听
    private FocusLostListener mFocusLostListener;
    //焦点移入recyclerview的事件监听
    private FocusGainListener mFocusGainListener;
    //默认第一次选中第一个位置
    private int mCurrentFocusPosition = 0;

    public FocusKeepRecyclerView(Context context) {
        this(context, null);
    }

    public FocusKeepRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusKeepRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        setChildrenDrawingOrderEnabled(true);
        setItemAnimator(null);
        this.setFocusable(true);
    }


    public boolean isCanFocusOutVertical() {
        return mCanFocusOutVertical;
    }

    public void setCanFocusOutVertical(boolean canFocusOutVertical) {
        mCanFocusOutVertical = canFocusOutVertical;
    }

    public boolean isCanFocusOutHorizontal() {
        return mCanFocusOutHorizontal;
    }

    public void setCanFocusOutHorizontal(boolean canFocusOutHorizontal) {
        mCanFocusOutHorizontal = canFocusOutHorizontal;
    }

    @Override
    public View focusSearch(int direction) {
        return super.focusSearch(direction);
    }

    //覆写focusSearch寻焦策略
    @Override
    public View focusSearch(View focused, int direction) {
        Timber.d(">> focusSearch() direction= %s, focused =%s ", direction, focused);
        View view = super.focusSearch(focused, direction);
        Timber.d(">> super.focusSearch() focused =%s ", view);
        if (focused == null) {
            return view;
        }

        if (view != null) {
            //该方法返回焦点view所在的父view,如果是在recyclerview之外，就会是null.所以根据是否是null,来判断是否是移出了recyclerview
            View nextFocusItemView = findContainingItemView(view);
            if (nextFocusItemView == null) {
                //如果下一个 View 没找到
                if (!mCanFocusOutVertical && (direction == View.FOCUS_DOWN || direction == View.FOCUS_UP)) {
                    //屏蔽焦点纵向移出recyclerview
                    return focused;
                }

                if (direction == View.FOCUS_LEFT && mCurrentFocusPosition > 0) {
                    return getLabelViewByPosition(mCurrentFocusPosition - 1);
                } else if (!mCanFocusOutHorizontal && direction == View.FOCUS_LEFT) {
                    //屏蔽焦点横向移出recyclerview
                    return focused;
                }

                if (direction == View.FOCUS_RIGHT && mCurrentFocusPosition < getAdapter().getItemCount()) {
                    return getLabelViewByPosition(mCurrentFocusPosition + 1);
                } else if (!mCanFocusOutHorizontal && direction == View.FOCUS_RIGHT) {
                    //屏蔽焦点横向移出recyclerview
                    return focused;
                }

                //调用移出的监听
                if (mFocusLostListener != null) {
                    mFocusLostListener.onFocusLost(focused, direction);
                }
                return view;
            } else {
                //如果有找到下一个子焦点，策略写在这
                if (mCurrentFocusPosition == (getAdapter().getItemCount() - 1) && direction == View.FOCUS_RIGHT) {
                    Timber.d(">> 拦截最后一个 Item 往右");
                    return focused;
                }
            }
        }
        return view;
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

    @Override
    public void requestChildFocus(View child, View focused) {
        Timber.d(">> requestChildFocus() nextchild= " + child + ",focused = " + focused);
        if (!hasFocus()) {
            //recyclerview 子view 重新获取焦点，调用移入焦点的事件监听
            if (mFocusGainListener != null) {
                mFocusGainListener.onFocusGain(child, focused);
            }
        }
        super.requestChildFocus(child, focused);//执行过super.requestChildFocus之后hasFocus会变成true
        mCurrentFocusPosition = getChildViewHolder(child).getAdapterPosition();
        Timber.d(">> focusPos = " + mCurrentFocusPosition);
    }

    //实现焦点记忆的关键代码
    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        View view = null;
        LabelView labelView = null;

        view = getLayoutManager().findViewByPosition(mCurrentFocusPosition);
        if (view != null) {
            labelView = view.findViewById(R.id.tv_selection);
        }


        if (this.hasFocus() || mCurrentFocusPosition < 0 || view == null) {
            super.addFocusables(views, direction, focusableMode);
        } else if (labelView.isFocusable()) {
//将当前的view放到Focusable views列表中，再次移入焦点时会取到该view,实现焦点记忆功能
            views.add(view);
        } else {
            super.addFocusables(views, direction, focusableMode);
        }
    }

    private View getLabelViewByPosition(int position) {
        View view = getLayoutManager().findViewByPosition(position);
        return view;
//        return view.findViewById(R.id.tv_selection);
    }


    /**
     * 控制当前焦点最后绘制，防止焦点放大后被遮挡
     * 原顺序123456789，当4是focus时，绘制顺序变为123567894
     *
     * @param childCount
     * @param i
     * @return
     */
//    @Override
//    protected int getChildDrawingOrder(int childCount, int i) {
//        View focusedChild = getFocusedChild();
//        Log.i(TAG,"focusedChild ="+focusedChild);
//        if(focusedChild== null){
//            return super.getChildDrawingOrder(childCount, i);
//        }else{
//            int index = indexOfChild(focusedChild);
//            Log.i(TAG, " index = " + index + ",i=" + i + ",count=" + childCount);
//            if(i == childCount-1){
//                return index;
//            }
//            if(i<index){
//                return i;
//            }
//            return i+1;
//        }
//    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return super.dispatchKeyEvent(event);
        }

        int next;
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            next = mCurrentFocusPosition - 6 < 0 ? 0 : mCurrentFocusPosition - 6;
            View view = getLayoutManager().findViewByPosition(next);
            if (view == null) {
                Timber.d(">> #### KeyEvent.KEYCODE_DPAD_UP");
                return true;
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            next = mCurrentFocusPosition + 6;
            View view = getLayoutManager().findViewByPosition(next);
            if (view == null) {
                Timber.d(">> #### KeyEvent.KEYCODE_DPAD_UP");
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }
}
