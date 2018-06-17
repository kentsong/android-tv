package com.example.kent.tv_view_focus.feature.grid_recyclerview.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.feature.grid_recyclerview.model.SimpleData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KentSong on 2018/6/11.
 */
public class ChannelAdapter extends FocusableAdapter {

    public ChannelAdapter(List<SimpleData> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_replay_channel, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return super.mData.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.channel_tv)
        TextView mChannelTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            SimpleData data = mData.get(position);
            mChannelTv.setText(data.getStr_1());
            mChannelTv.setSelected(data.isSelected());

            Context context = mChannelTv.getContext();
            if (data.isFocused()) {
                mChannelTv.setBackgroundColor(context.getResources().getColor(R.color.focus_blue));
                mChannelTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                if (data.isSelected()) {
//                    mChannelTv.setBackground(context.getResources().getDrawable(R.drawable.frame_line_shrink_blue_bg));
                    mChannelTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                } else {
                    mChannelTv.setBackgroundColor(0);
                    mChannelTv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }

            }
        }

    }


}
