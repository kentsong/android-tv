package com.example.kent.tv_view_focus.utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {

    private final int DELAY_TIME = 300; // 加载延迟时间

    private Timer mTimer;
    private TimerTask mLastTask;
    private long mLastTime;

    public TimerManager() {
        this.mTimer = new Timer();
    }

    public synchronized void addTask(TimerTask newTask) {
        long currTime = System.currentTimeMillis();

        if (Math.abs(mLastTime - currTime) > 300) {
//            Timber.d(">> newTask = %s , index = %s", mLastTask, ((HomeFragment.PageTask)newTask).getIndex());
            mLastTask = newTask;
            mTimer.schedule(newTask, DELAY_TIME);
        } else {
            mLastTask.cancel();
            mLastTask = newTask;
            mTimer.schedule(newTask, DELAY_TIME);
//            Timber.d(">> oldTask = %s , index = %s, canceled = %s", mLastTask, ((HomeFragment.PageTask)mLastTask).getIndex(), isCanceled);
        }

        mLastTime = currTime;
    }


}
