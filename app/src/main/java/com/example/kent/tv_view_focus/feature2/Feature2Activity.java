package com.example.kent.tv_view_focus.feature2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.kent.tv_view_focus.MainActivity;
import com.example.kent.tv_view_focus.R;
import com.example.kent.tv_view_focus.utils.TimerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class Feature2Activity extends AppCompatActivity {

    @BindView(R.id.rv_channel)
    CustomRecyclerView rvChannel;

    @BindView(R.id.rv_selection)
    SelectionRecyclerView rvSelection;

    @BindView(R.id.edit_text1)
    EditText editText1;
    @BindView(R.id.edit_text2)
    EditText editText2;


    private ChannelAdapter cAdpater;
    private SelectionAdapter sAdapter;
    private CenterLayoutManger mChannelLayoutManager;
    private LinearLayoutManager mSelectionLayoutManager;
    private boolean mMove = false;
    private int mIndex = 0;

    TimerManager mTimerManager;

    private int mLastChannelPos = 0;
    private int mLastSlectionPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature2);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initChaneel();
        initSelection();
    }

    private void initChaneel() {
        cAdpater = new ChannelAdapter(this, generateChannel());
        cAdpater.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                mLastChannelPos = position;
            }
        });

        cAdpater.setmOnKeyDownListener(new OnKeyDownListener() {
            @Override
            public void onKeyDown(int keyCode) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    int pos = sAdapter.getLastPosition();
                    Timber.d(">> sAdapter.getLastPosition() = %s", pos);
                    View view = mSelectionLayoutManager.findViewByPosition(pos);
                    if (view == null) {
                        Timber.d(">> mChannelLayoutManager.findViewByPosition(pos) = null");
                    } else {
                        Timber.d(">> mChannelLayoutManager.findViewByPosition(pos) 有找到");
                        view.requestFocus();
                    }

                }
            }
        });

        mChannelLayoutManager = new CenterLayoutManger(this, LinearLayoutManager.HORIZONTAL, false);
        rvChannel.setLayoutManager(mChannelLayoutManager);
        rvChannel.setAdapter(cAdpater);
        rvChannel.scrollToPosition(mLastChannelPos);


        rvChannel.setFocusLostListener(new CustomRecyclerView.FocusLostListener() {
            @Override
            public void onFocusLost(View lastFocusChild, int direction) {
                Timber.d("onFocusLost lastFocusChild = %s, direction = %s", lastFocusChild, direction);
            }
        });

        rvChannel.setGainFocusListener(new CustomRecyclerView.FocusGainListener() {
            @Override
            public void onFocusGain(View child, View focuedView) {
                Timber.d("onFocusGain child = %s, focuedView = %s", child, focuedView);
            }
        });

    }

    private void initSelection() {
        mTimerManager = new TimerManager();

        mSelectionLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        sAdapter = new SelectionAdapter(generateSelection());
        sAdapter.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                mLastSlectionPos = position;
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


    private List<String> generateChannel() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 110; i++) {
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
        list.add("101-110");
        return list;
    }


    /**
     * 先用scrollToPosition，将要置左的项先移动显示出来，然后计算这一项离第一項的距离，用 scrollBy 完成！
     *
     * @param n
     */
    private void moveToPosition(final int n) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIndex = n;

                int firstItem = mChannelLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mChannelLayoutManager.findLastVisibleItemPosition();
                Timber.d(">> moveToPosition findFirstVisibleItemPosition = %s, findLastVisibleItemPosition = %s", firstItem, lastItem);
                if (n <= firstItem) {
                    //当要置左的项在当前显示的第一个项的前面时
                    Timber.d(">>  rvChannel.scrollToPosition(%s)", n);
                    rvChannel.smoothScrollToPosition(n);
                } else if (n <= lastItem) {
                    //当要置左的项已经在屏幕上显示时
                    int left = rvChannel.getChildAt(n - firstItem).getLeft();
                    Timber.d(">>  rvChannel.scrollBy(%s, 0);", left);
                    rvChannel.scrollBy(left, 0);
                } else {
                    //当要置左的项在当前显示的最后一项的后面时
                    Timber.d(">>  else scrollToPosition(%s)", n);
                    rvChannel.scrollToPosition(n);
                    //这里这个变量是用在RecyclerView滚动监听里面的
                    mMove = true;
                }
            }
        });
    }

    @OnClick(R.id.button1)
    public void onButton1Clicked() {
        int x = editText1.getText() == null ? 0 : Integer.parseInt(editText1.getText().toString());
        rvChannel.smoothScrollBy(x, 0);
        cAdpater.notifyDataSetChanged();

    }

    @OnClick(R.id.button2)
    public void onButton2Clicked() {
        int index = editText2.getText() == null ? 0 : Integer.parseInt(editText2.getText().toString()) - 1;
        rvChannel.smoothScrollToPosition(index);
        cAdpater.setItemFocused(index);
        cAdpater.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public class ChannelLoadTask extends TimerTask {

        private int mIndex;

        ChannelLoadTask(int index) {
            this.mIndex = index;
        }

        @Override
        public void run() {
            moveToPosition(mIndex);
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, Feature2Activity.class);
        context.startActivity(intent);
    }

}

