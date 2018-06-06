package com.example.kent.tv_view_focus.feature2;

import android.content.Context;
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

    private Context mContext;
    private List<ChannelVO> mList;

    private OnItemFocusListener mOnItemFocusListener;
    private OnKeyDownListener mOnKeyDownListener;

    private int mLastSelectedPos;
    private int mLastFocusPos;

    public int getLastSelectedPos() {
        return mLastSelectedPos;
    }

    public void setSelected(int pos){
        Timber.d(">> setSelected = %s", pos);
        ChannelVO oriChannel= mList.get(mLastSelectedPos);
        if(oriChannel.isSelected){
            oriChannel.isSelected = false;
            notifyItemChanged(mLastSelectedPos);
        }

        ChannelVO newChannel = mList.get(pos);
        newChannel.isSelected = true;
        mLastSelectedPos = pos;

        notifyItemChanged(pos);
    }

    public void setItemFocused(int pos){
        Timber.d(">> setItemFocused = %s", pos);
        ChannelVO oriChannel= mList.get(mLastFocusPos);
        if(oriChannel.isFocused){
            oriChannel.isFocused = false;
            notifyItemChanged(mLastFocusPos);
        }

        ChannelVO newChannel = mList.get(pos);
        newChannel.isFocused = true;
        mLastFocusPos = pos;

        notifyItemChanged(pos);
    }

    public void setItemUnFocus(int pos){
        ChannelVO newChannel = mList.get(pos);
        newChannel.isFocused = false;
        mLastFocusPos = pos;

        notifyItemChanged(pos);
    }

    public void setOnItemFocusListener(OnItemFocusListener onItemFocusListener) {
        this.mOnItemFocusListener = onItemFocusListener;
    }

    public void setmOnKeyDownListener(OnKeyDownListener mOnKeyDownListener) {
        this.mOnKeyDownListener = mOnKeyDownListener;
    }

    public ChannelAdapter(Context context , List<String> sList) {
        this.mContext = context;
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
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
            tvChannel.setFocusable(false);
            tvChannel.setFocusableInTouchMode(false);

            tvChannel.setText(vo.name);
            if(vo.isFocused){
                tvChannel.setBackgroundColor(mContext.getResources().getColor(R.color.focus_bg));
                mOnItemFocusListener.onItemFocus(tvChannel, getAdapterPosition());
            } else{
                tvChannel.setBackgroundColor(mContext.getResources().getColor(R.color.unfocus_bg));
            }


        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
