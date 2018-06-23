package com.example.kent.tv_view_focus.feature.grid_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.feature.grid_recyclerview.adapter.ChannelAdapter;
import com.example.kent.tv_view_focus.feature.grid_recyclerview.model.SimpleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2018/6/16.
 */
public class GirdRvActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    FocusableRecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recyclerview);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        setupCategoryRv();//分类
    }

    private void setupCategoryRv() {
        ChannelAdapter adapter = new ChannelAdapter(generateData());
        recyclerview.setLayoutManager(new CenterGridLayoutManger(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerview.setCanFocusOutHorizontal(true);
        recyclerview.setDefaultIndex(0);
        recyclerview.setAdapter(adapter);
    }

    private List<SimpleData> generateData() {
        List<SimpleData> list = new ArrayList<>();
        for(int i =0 ;i<100;i++){
            list.add(new SimpleData(""+i));
        }
        return list;
    }
}
