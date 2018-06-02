package com.example.kent.tv_view_focus;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.kent.tv_view_focus.feature1.ChannelAdapter;
import com.example.kent.tv_view_focus.feature1.OnItemFocusListener;
import com.example.kent.tv_view_focus.feature1.OnKeyDownListener;
import com.example.kent.tv_view_focus.feature1.SelectionAdapter;
import com.example.kent.tv_view_focus.feature1.SelectionRecyclerView;
import com.example.kent.tv_view_focus.utils.TimerManager;
import com.example.kent.tv_view_focus.view.LabelView;
import com.example.kent.tv_view_focus.view.SelectionView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.selectionView)
    SelectionView sView;

    @BindView(R.id.rv_channel)
    RecyclerView rvChannel;

    @BindView(R.id.rv_selection)
    SelectionRecyclerView rvSelection;

    private ChannelAdapter cAdpater;
    private SelectionAdapter sAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mSelectionLayoutManager;
    private boolean mMove = false;
    private int mIndex = 0;

    TimerManager mTimerManager;

    private int mLastChannelPos = 80;
    private int mLastSlectionPos = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mTimerManager = new TimerManager();

        cAdpater = new ChannelAdapter(generateChannel());
        cAdpater.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                mLastChannelPos = position;
                String str = ((TextView) view).getText().toString();
                int pos = Integer.parseInt(str);
                int sPosition = (pos - 1) / 10;
                sAdapter.setPosition(sPosition);
                rvSelection.scrollToPosition(sPosition);
                Timber.d(">> cAdpater onItemFocus position = %s", position);
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
                        Timber.d(">> mLinearLayoutManager.findViewByPosition(pos) = null");
                    } else {
                        Timber.d(">> mLinearLayoutManager.findViewByPosition(pos) 有找到" );
                        view.requestFocus();
                    }

                }
            }
        });


        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mSelectionLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvChannel.setLayoutManager(mLinearLayoutManager);
        rvChannel.setAdapter(cAdpater);
        rvChannel.setOnScrollListener(new RecyclerViewListener() {
        });
        rvChannel.scrollToPosition(mLastChannelPos);

        sAdapter = new SelectionAdapter(generateSelection());
        sAdapter.setOnItemFocusListener(new OnItemFocusListener() {
            @Override
            public void onItemFocus(View view, int position) {
                mLastSlectionPos = position;
                Timber.d(">> sAdapter onItemFocus position = %s", position);
                String str = ((TextView) view).getText().toString();
                final int pos = Integer.parseInt(str.split("-")[0]) - 1;
                cAdpater.setPosition(pos);
                mTimerManager.addTask(new ChannelLoadTask(pos));

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

    private void test() {
        View focusedChild = mLinearLayoutManager.getFocusedChild();
        if (focusedChild == null) {
            int first = mLinearLayoutManager.findFirstVisibleItemPosition();
            View view = mLinearLayoutManager.findViewByPosition(first);
            view.requestFocus();
        } else {
            focusedChild.requestFocus();
        }
    }

    private void moveToPosition(final int n) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIndex = n;

                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                Timber.d(">> moveToPosition findFirstVisibleItemPosition = %s, findLastVisibleItemPosition = %s", firstItem, lastItem);
                if (n <= firstItem) {
                    Timber.d(">>  rvChannel.scrollToPosition(%s)", n);
                    rvChannel.smoothScrollToPosition(n);
//            rvChannel.smoothScrollToPosition(n);
                } else if (n <= lastItem) {
                    int left = rvChannel.getChildAt(n - firstItem).getLeft();
                    Timber.d(">>  rvChannel.scrollBy(%s, 0);", left);
                    rvChannel.scrollBy(left, 0);
//                    rvChannel.smoothScrollBy(left, 0);
                } else {
                    Timber.d(">>  else scrollToPosition(%s)", n);
                    rvChannel.scrollToPosition(n);
//                    rvChannel.smoothScrollToPosition(n);
                    mMove = true;
                }
            }
        });


    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Timber.d(">>  RecyclerViewListener onScrollStateChanged newState = %s", newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Timber.d(">>  RecyclerViewListener onScrolled dx = %s, dy = %s", dx, dy);
            if (mMove) {
                mMove = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rvChannel.getChildCount()) {
                    final int left = rvChannel.getChildAt(n).getLeft();
                    Timber.d(">>  left = %s", left);
                    rvChannel.smoothScrollBy(left, 0);
//                    rvChannel.scrollBy(left, 0);

                }
            }
        }
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

        public int getIndex() {
            return mIndex;
        }
    }
}
