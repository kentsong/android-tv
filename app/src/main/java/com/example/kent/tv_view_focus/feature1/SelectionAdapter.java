package com.example.kent.tv_view_focus.feature1;

import android.support.v7.widget.RecyclerView;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public SelectionAdapter(List<String> sList) {
        this.mList = convert(sList);
    }

    public void setOnItemFocusListener(OnItemFocusListener onItemFocusListener) {
        this.mOnItemFocusListener = onItemFocusListener;
    }

    public void setPosition(int pos) {
        Timber.d(">> setPosition = %s", pos);
        SelectionVO oriSelection = mList.get(mLastPosition);
        oriSelection.isSelected = false;

        notifyItemChanged(mLastPosition);

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
                        if (mOnItemFocusListener != null) {
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
