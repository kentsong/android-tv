package com.example.kent.tv_view_focus.feature.keepfocus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.feature2.CenterLayoutManger;
import com.example.kent.tv_view_focus.feature2.OnItemFocusListener;
import com.example.kent.tv_view_focus.feature2.SelectionRecyclerView;
import com.example.kent.tv_view_focus.utils.TimerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Kent Song on 2018/6/22.
 */
public class FocusKeepActivity extends AppCompatActivity {


    @BindView(R.id.rv_selection)
    FocusKeepRecyclerView rvSelection;

    private SelectionAdapter sAdapter;
    private CenterLayoutManger mChannelLayoutManager;
    private LinearLayoutManager mSelectionLayoutManager;

    private int mLastSlectionPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_focus);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
//        initChaneel();
        initSelection();
    }

    private void initSelection() {

        mSelectionLayoutManager = new CenterGridLayoutManger(this, 6,CenterGridLayoutManger.VERTICAL, false);

        sAdapter = new SelectionAdapter(generateSelection());
        sAdapter.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                Timber.d(">> OnItemFocusListener position = %s ", position);
                mLastSlectionPos = position;
                rvSelection.smoothScrollToPosition(position);
//                Timber.d(">> sAdapter onItemFocus position = %s", position);
//                String str = ((TextView) view).getText().toString();
//                final int pos = Integer.parseInt(str.split("-")[0]) - 1;
//                cAdpater.setSelected(pos);
//                mTimerManager.addTask(new ChannelLoadTask(pos));

            }
        });
        rvSelection.setLayoutManager(mSelectionLayoutManager);
        rvSelection.setAdapter(sAdapter);
        rvSelection.scrollToPosition(mLastSlectionPos);
    }


    private List<String> generateSelection() {
        List<String> list = new ArrayList<>();
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        list.add("1-10");
        list.add("11-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61-70");
        list.add("71-80");
        list.add("81-90");
        list.add("91-100");
        list.add("101-110");
        return list;
    }

}
