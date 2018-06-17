package com.example.kent.tv_view_focus.feature.grid_recyclerview.adapter;

import android.support.v7.widget.RecyclerView;


import com.example.kent.tv_view_focus.feature.grid_recyclerview.model.SimpleData;

import java.util.List;

/**
 * Created by KentSong on 2018/6/11.
 *
 * FocusableAdapter 适配器
 *
 * 使用 SimpleData 通用物件封装资料
 * @See {@link SimpleData}
 *
 */
public abstract class FocusableAdapter extends RecyclerView.Adapter {

    protected List<SimpleData> mData;

    protected int mLastFocusIndex;

    public FocusableAdapter(List<SimpleData> data) {
        this.mData = data;
    }

    public void setItemFocused(int index) {
        SimpleData ori = mData.get(mLastFocusIndex);
        ori.setFocused(false);
        ori.setSelected(false);
        notifyItemChanged(mLastFocusIndex);

        SimpleData target = mData.get(index);
        target.setFocused(true);
        target.setSelected(true);
        mLastFocusIndex = index;

        notifyItemChanged(index);
    }

    public void setItemUnFocus(int index) {
        SimpleData data = mData.get(index);
        data.setFocused(false);

        notifyItemChanged(index);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
