package com.ltime.buspad.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ltime.buspad.ui.WelcomeActivity;

/**
 * Created by YF001 on 2018/1/25.
 */

public class ScreenService extends Service{
    private static boolean isFirst = true;
    Context context=this;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//        Toast.makeText(this,"ffffffffffff",Toast.LENGTH_SHORT).show();
        super.onCreate();

//        if (isFirst ) {
//
//            Intent in =new Intent();
//            in.setClass(context,WelcomeActivity.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(in);
//            isFirst=false;
//        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ScreenListener l = new ScreenListener(this);
        l.begin(new ScreenListener.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                Log.e("onUserPresent", "onUserPresent");
//                Toast.makeText(MainActivity.this,"onUserPresent",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScreenOn() {
                Log.e("onScreenOn", "onScreenOn");
//                Toast.makeText(MainActivity.this,"onScreenOn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScreenOff() {
                Log.e("onScreenOff", "onScreenOff");
//                Toast.makeText(MainActivity.this,"onScreenOff",Toast.LENGTH_SHORT).show();
                Intent in =new Intent();
                in.setClass(context,WelcomeActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
