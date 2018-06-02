package com.example.kent.tv_view_focus;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class Test {

    public static void main(String[] args) throws Exception {

        TimerTask task = new RunMeTask("1");
        TimerTask task2 = new RunMeTask("2");

        Timer timer = new Timer();
        timer.schedule(task, 1000);
        timer.schedule(task2, 500, 60000);
        task.cancel();


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 1000);


    }


    public static class RunMeTask extends TimerTask {

        private String mId;

        public RunMeTask(String id) {
            this.mId = id;
        }

        @Override
        public void run() {
            System.out.println("id = " + mId + " Run  ~");
        }
    }


}
