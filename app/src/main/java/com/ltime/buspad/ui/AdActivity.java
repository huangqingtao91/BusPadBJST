package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.VideoView;

import com.ltime.buspad.R;
import com.ltime.buspad.bean.AdResult2;
import com.ltime.buspad.bean.AdTotalRequest;
import com.ltime.buspad.bean.Datapost2;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ltime.buspad.ui.PlayMovieActivity.activity;

/**
 * Created by Administrator on 2016/2/23 22:07.
 */
public class AdActivity extends Activity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private VideoView mVideoview;
    private Api api;
    private Uri mUri;
    private String adPath;
    private int i;

    private String tag;

    private List<AdResult2.DataBean> mList = new ArrayList<>();
    //   ListDataSave dataSave;

    SharedPreferences sp = null;
    SharedPreferences.Editor ed = null;

    TextView tv_time;
    TimeCount timeCount;

//    private MediaController mMediaController;//媒体控制器

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //   dataSave = new ListDataSave(this, "buspad");
        sp = getSharedPreferences("buspad", MODE_PRIVATE); // 私有数据
        ed = sp.edit();// 获取编辑器
        mVideoview = findViewById(R.id.VideoView);
        tv_time = findViewById(R.id.tv_time);
    }

    @Override
    public void onResume() {
        super.onResume();
        tag = getIntent().getExtras().getString("tag");//判断是从哪个activity跳转过来的
        requestDataAd();
        initListener();
    }

    private void requestDataAdtotal(String id) {


        AdTotalRequest data = new AdTotalRequest(id);
        Datapost2 dp2 = new Datapost2(data);

        api = Net.net(Constants.URL);
        Call<String> call = api.postadtotal(dp2);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                System.out.println("***** 广告统计 +1 *****");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void requestDataAd() {

        api = Net.net(Constants.URL);
        Call<AdResult2> call = api.postad2();
        call.enqueue(new Callback<AdResult2>() {
            @Override
            public void onResponse(Call<AdResult2> call, Response<AdResult2> response) {
                //  AdResult2.DataBean modelad = response.body().getData().;
                mList.clear();
                for (int i = 0; i < response.body().getData().size(); i++) {
                    AdResult2.DataBean modelad = response.body().getData().get(i);
                    AdResult2.DataBean adResult2 = new AdResult2.DataBean(modelad.getAddtime(), modelad.getBasename(), modelad.getDirname(),
                            modelad.getExtension(), modelad.getId(), modelad.getName(), modelad.getStatus());
                    mList.add(adResult2);
                }
                //   dataSave.setDataList("javaBean", mList);

                startPlay();
            }

            @Override
            public void onFailure(Call<AdResult2> call, Throwable t) {

            }
        });
    }

    private void startPlay() {
        //广告按顺序播
        if (mList.size() != 0) {
            i = sp.getInt("index", 0);
            if (i + 1 > mList.size()) {
                i = 0;
            }
            ed.putInt("index", i + 1);
            ed.commit();
            adPath = mList.get(i).getDirname() + mList.get(i).getBasename();
            requestDataAdtotal(mList.get(i).getId());

            System.out.println("播放广告名字为： " + mList.get(i).getName() + ".mp4");
            System.out.println("播放广告id为： " + mList.get(i).getId());

            mUri = Uri.parse(adPath);
            mVideoview.setVideoURI(mUri);
            mVideoview.start();
        }

//        媒体控制器的初始化工作
//        mMediaController = new MediaController(this);
//        //videoview绑定媒体控制器
//        mVideoview.setMediaController(mMediaController);
    }

    private void initListener() {
        mVideoview.setOnCompletionListener(this);
        mVideoview.setOnErrorListener(this);
        mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                long ll = mVideoview.getDuration();
                timeCount = new TimeCount(ll, 1000);// 构造CountDownTimer对象
                timeCount.start();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }

    /**
     * Video播完的时候得到通知
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (tag.equals("0")) {
            Bundle b = new Bundle();
            b.putString("path", getIntent().getExtras().getString("videopath"));
            Intent intent = new Intent();
            intent.putExtras(b);
            intent.setClass(AdActivity.this, PlayMovieActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            this.finish();
        }

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        if (activity != null) {
            activity.finish();
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tv_time.setText(millisUntilFinished / 1000 + " s");
        }
    }
}
