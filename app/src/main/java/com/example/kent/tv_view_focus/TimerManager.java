package com.example.kent.tv_view_focus;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {

    private Timer mTimer ;
    private TimerTask mLastTask;
    private Long mLastTime;

    public TimerManager() {
        this.mTimer = new Timer();

    }

    public void addTask(TimerTask newTask){
        long currTime = System.currentTimeMillis();
        if (Math.abs(mLastTime - currTime) > 500) {
            mLastTime = currTime;

            mLastTask.cancel();
            mLastTask = newTask;

            mTimer.schedule(newTask, 500);
        }
    }







}
