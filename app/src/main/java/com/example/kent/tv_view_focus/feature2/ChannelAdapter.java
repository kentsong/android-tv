package com.example.kent.tv_view_focus.feature2;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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
    private OnKeyDownListener mOnKeyDownListener;

    private int mLastPosition;

    public int getLastPosition() {
        return mLastPosition;
    }

    public void setPosition(int pos){
        Timber.d(">> setPosition = %s", pos);
        ChannelVO oriChannel= mList.get(mLastPosition);
        if(oriChannel.isSelected){
            oriChannel.isSelected = false;
            notifyItemChanged(mLastPosition);
        }

        ChannelVO newChannel = mList.get(pos);
        newChannel.isSelected = true;
        mLastPosition = pos;

        notifyItemChanged(pos);
    }

    public void setOnItemFocusListener(OnItemFocusListener onItemFocusListener) {
        this.mOnItemFocusListener = onItemFocusListener;
    }

    public void setmOnKeyDownListener(OnKeyDownListener mOnKeyDownListener) {
        this.mOnKeyDownListener = mOnKeyDownListener;
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
            tvChannel.setText(vo.name);
            tvChannel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Timber.d(">> onFocusChange hasFocus = %s", hasFocus);
                    if (hasFocus) {
                        if(mOnItemFocusListener != null){
                            int position = getAdapterPosition();
                            mLastPosition = position;
                            mOnItemFocusListener.onItemFocus(v, position);
                        }
                    }
                }
            });

            tvChannel.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(event.getAction() == KeyEvent.ACTION_DOWN){
                        if(mOnKeyDownListener != null){
                            mOnKeyDownListener.onKeyDown(keyCode);
                            if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
