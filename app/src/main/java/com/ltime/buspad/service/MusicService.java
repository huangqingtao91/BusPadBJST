package com.ltime.buspad.service;

/**
 * Created by YF001 on 2018/5/23.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.ltime.buspad.R;
import com.ltime.buspad.bean.MusicResult;
import com.ltime.buspad.ui.TestMusicAct;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.ltime.buspad.ui.TestMusicAct.bt_sp;
import static com.ltime.buspad.ui.TestMusicAct.changeui;
import static com.ltime.buspad.ui.TestMusicAct.isplay;
import static com.ltime.buspad.ui.TestMusicAct.pos;


public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    public static MediaPlayer mp;
    private List<MusicResult.DataBean> mlist;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service", "服务被启动");
        mp = new MediaPlayer();
        mlist = TestMusicAct.mlist;          //获取所有音乐名
        try {
            mp.setDataSource("http://192.168.180.1/"+mlist.get(0).getFile());         //设置第一首歌
            mp.prepare();             //装载音乐文件资源
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            int num = intent.getIntExtra("switch", 0);
            if (num == 0xffffffff) {

                if (mp.isPlaying()) {
                    mp.pause();
                    bt_sp.setImageResource(R.mipmap.ic_play);
                } else {
                    mp.start();
                    bt_sp.setImageResource(R.mipmap.ic_pause);
//                    updateProgress();
                }

            } else if (num == 0xfffffffe) {                        //按下下一首按钮
                    if (mp != null) {
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        mp.reset();                                     //重置
                        mp.setDataSource("http://192.168.180.1/"+mlist.get(pos).getFile());
                        mp.prepare();                              //装载音乐文件资源
                        mp.start();
//                        updateProgress();
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

//    public void updateProgress() {                                   //更新进度条
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Message msg = Message.obtain();
//                msg.obj = getTime();
//                TestMusicAct.handler.sendMessage(msg);
//                while (TestMusicAct.count % 2 != 0) {
//                    Message msg_ = Message.obtain();
//                    int progress = (int) (mp.getCurrentPosition() * 1000.0 / mp.getDuration());
//                    msg_.what = progress;                 //获取当前播放位置
//                    TestMusicAct.handler.sendMessage(msg_);
//                    SystemClock.sleep(1000);    //延时一秒钟
//                }
//            }
//        }).start();
//        Log.e("thread", "更新线程结束");
//    }

    public String getTime() {                        //将毫秒转换成时间
        int total = mp.getDuration();
        String format = "mm:ss";                     //自定义时间模式
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(total);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();          //释放音乐文件资源
            } else
                mp.release();
        }

        Log.e("service", "服务被销毁");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
//        bt_next.performClick();

        pos++;
        if (pos>mlist.size()-1) {
            pos=0;
        }
        changeui(pos);
        bt_sp.setImageResource(R.mipmap.ic_pause);
        isplay = 1;
        Intent in = new Intent(MusicService.this, MusicService.class)
                .putExtra("switch", 0xfffffffe);
        startService(in);
    }
}
