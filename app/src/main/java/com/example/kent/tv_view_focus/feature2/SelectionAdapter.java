package com.example.kent.tv_view_focus.feature2;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.view.LabelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {

    private List<SelectionVO> mList;

    private OnItemFocusListener mOnItemFocusListener;

    private int mLastPosition;

    private long mLastTime;

    public SelectionAdapter(List<String> sList) {
        this.mList = convert(sList);
    }

    public void setOnItemFocusListener(OnItemFocusListener onItemFocusListener) {
        this.mOnItemFocusListener = onItemFocusListener;
    }

    public void setPosition(int pos) {
        Timber.d(">> setSelected = %s", pos);
        SelectionVO oriSelection = mList.get(mLastPosition);
        if(oriSelection.isSelected){
            oriSelection.isSelected = false;
            notifyItemChanged(mLastPosition);
        }


        SelectionVO newSelection = mList.get(pos);
        newSelection.isSelected = true;
        mLastPosition = pos;

        notifyItemChanged(pos);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_selection, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    public int getLastPosition() {
        return mLastPosition;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_selection)
        LabelView tvSelection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(SelectionVO vo) {
            tvSelection.setText(vo.name);
            tvSelection.setSelected(vo.isSelected);

            tvSelection.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Timber.d(">> onFocusChange hasFocus = %s ", hasFocus);
                        if (mOnItemFocusListener != null) {
                            mOnItemFocusListener.onItemFocus(v, getAdapterPosition());
                        }
                    }
                }
            });

            tvSelection.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(event.getAction() == KeyEvent.ACTION_DOWN){
                        long currTime = System.currentTimeMillis();
                        switch (keyCode){
                            case KeyEvent.KEYCODE_DPAD_LEFT:
                            case KeyEvent.KEYCODE_DPAD_RIGHT:
                                mLastTime = currTime;
                                break;
                            case KeyEvent.KEYCODE_DPAD_UP:
                                break;
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

    private List<SelectionVO> convert(List<String> sList) {
        List<SelectionVO> list = new ArrayList<>();
        for (int i = 0; i < sList.size(); i++) {
            SelectionVO vo = new SelectionVO(sList.get(i));
            if (i == 0) {
                vo.isSelected = true;
            }
            list.add(vo);
        }
        return list;
    }

}
