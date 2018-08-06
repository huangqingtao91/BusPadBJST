package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ltime.buspad.R;

import java.util.Formatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/23 22:07.
 */
public class PlayMovieActivity extends Activity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    SharedPreferences sp = null;
    private VideoView mVideoview;
    private int currentPosition = 0;

    private Timer timer;

    private Uri mUri;
    private String moviepath;
    private MediaController mMediaController;//媒体控制器

    String result = "null";

    //将长度转换为时间
    StringBuilder mFormatBuilder;
    Formatter mFormatter;

    private long adtime;

    public static Activity activity;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
              //  Toast.makeText(PlayMovieActivity.this, "广告时间", Toast.LENGTH_SHORT).show();
                currentPosition = mVideoview.getCurrentPosition();
                Bundle b = new Bundle();
                b.putString("tag", "1");
                Intent intent = new Intent();
                intent.putExtras(b);
                intent.setClass(PlayMovieActivity.this, AdActivity.class);
                startActivity(intent);
                removeCallbacksAndMessages(null);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoview = (VideoView) findViewById(R.id.VideoView);
        sp = getSharedPreferences("buspad", MODE_PRIVATE); //私有数据
        adtime=Long.parseLong(sp.getString("adtime","0"))*60*1000;
        timer = new Timer();
        activity=this;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        playad();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        initListener();
        startPlay();
        super.onResume();
    }


    private void startPlay() {
        if (currentPosition == 0) {
            moviepath = getIntent().getExtras().getString("path");
            mUri = Uri.parse(moviepath);
            mVideoview.setVideoURI(mUri);
            mVideoview.start();
        } else {
            mVideoview.start();
            mVideoview.seekTo(currentPosition);
        }

//        媒体控制器的初始化工作
        mMediaController = new MediaController(this);
        //videoview绑定媒体控制器
        mVideoview.setMediaController(mMediaController);
    }

    private void initListener() {
        mVideoview.setOnCompletionListener(this);
        mVideoview.setOnErrorListener(this);
        mVideoview.setOnPreparedListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    /**
     * Video播完的时候得到通知
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
//        mVideoview.setVideoURI(mUri);
//        mVideoview.start();
        this.finish();
    }

    /**
     * 监听MediaPlayer上报的错误信息
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    protected void onDestroy() {
        currentPosition = 0;
        //       mHandler.removeMessages(0);
        mHandler.removeCallbacksAndMessages(null);
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 时间转time格式
     *
     * @param timeMs
     * @return
     */
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private void playad() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                Log.e("TAG","没隔5秒执行一次操作");
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessage(message);
            }
        }, adtime, adtime);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data != null) {
//            result = data.getExtras().getString("result");//得到新Activity关闭后返回的数据
//        }
//    }
}
