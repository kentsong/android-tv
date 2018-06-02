package com.example.kent.tv_view_focus.feature1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kent.tv_view_focus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private List<ChannelVO> mList;

    private OnItemFocusListener mOnItemFocusListener;

    private int mLastPosition;

    public int getLastPosition() {
        return mLastPosition;
    }

    public void setOnItemFocusListener(OnItemFocusListener onItemFocusListener) {
        this.mOnItemFocusListener = onItemFocusListener;
    }

    public ChannelAdapter(List<String> sList) {
        this.mList = convert(sList);
    }

    private List<ChannelVO> convert(List<String> sList) {
        List<ChannelVO> list = new ArrayList<>();
        for (String s : sList) {
            list.add(new ChannelVO(s));
        }
        return list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_channel, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_channel)
        TextView tvChannel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ChannelVO vo) {
            tvChannel.setText(vo.name);
            tvChannel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Timber.d(">> onFocusChange hasFocus = %s", hasFocus);
                    if (hasFocus) {
                        if(mOnItemFocusListener != null){
                            mLastPosition = getAdapterPosition();
                            mOnItemFocusListener.onItemFocus(v, getAdapterPosition());
                        }
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
