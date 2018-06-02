package com.example.kent.tv_view_focus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kent.tv_view_focus.feature1.ChannelAdapter;
import com.example.kent.tv_view_focus.feature1.OnItemFocusListener;
import com.example.kent.tv_view_focus.feature1.SelectionAdapter;
import com.example.kent.tv_view_focus.view.SelectionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.selectionView)
    SelectionView sView;

    @BindView(R.id.rv_channel)
    RecyclerView rvChannel;

    @BindView(R.id.rv_selection)
    RecyclerView rvSelection;

    private ChannelAdapter cAdpater;
    private SelectionAdapter sAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        cAdpater = new ChannelAdapter(generateChannel());
        cAdpater.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                int sPosition = (position + 1) / 5;
                sAdapter.setPosition(sPosition);
                rvSelection.scrollToPosition(sPosition);
                Timber.d(">> cAdpater onItemFocus position = %s", position);
            }
        });
        rvChannel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvChannel.setAdapter(cAdpater);

        sAdapter = new SelectionAdapter(generateSelection());
        sAdapter.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                Timber.d(">> sAdapter onItemFocus position = %s", position);
//                String str = ((TextView) view).getText().toString();

//                int sPosition = position/10;
//                sAdapter.notifyDataSetChanged();
//                rvSelection.scrollToPosition(sPosition;
            }
        });
        rvSelection.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSelection.setAdapter(sAdapter);

    }

    private List<String> generateChannel() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("" + i);
        }
        return list;
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
        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
