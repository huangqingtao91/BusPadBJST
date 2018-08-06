package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.ltime.buspad.R;
import com.ltime.buspad.adapter.ListMusicAdapter;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.MusicResult;
import com.ltime.buspad.bean.VideoRequest;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.service.MusicService;
import com.ltime.buspad.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ltime.buspad.service.MusicService.mp;
import static com.ltime.buspad.util.Constants.PUBLCIKEY;
import static com.ltime.buspad.util.Md5.formatUrlMap;
import static com.ltime.buspad.util.Md5.getMD5;
import static com.ltime.buspad.util.Md5.urlToJson;

/**
 * Created by YF001 on 2018/5/23.
 */

public class TestMusicAct extends Activity implements View.OnClickListener {
    public static ImageView bt_sp, bt_next, bt_last, icon;
    public static TextView tv_crt, tv_tol, tv_name, tv_art;
    private static SeekBar seekBar;
    public static ArrayList<String> list = new ArrayList<String>();
    public static int isplay = 0;
    public static int pos = 0;
    private Api api;
    public static List<MusicResult.DataBean> mlist = new ArrayList<MusicResult.DataBean>();
    private String category;
    public static ListMusicAdapter adapter;
    private ListView lv;

    Animation operatingAnim;

    public static Context content;
    private boolean isfirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicb);
        content = this;
        isfirst = true;
        initView();
        bt_sp.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_last.setOnClickListener(this);
        category = getIntent().getExtras().getString("category");
        getData();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {  //当seekBar被持续拖动时回调
                Log.e("changed", progress + "");
                int current = (int) (progress * mp.getDuration() / 1000.0);
                tv_crt.setText(new SimpleDateFormat("mm:ss").format(current));  //更新拖动进度
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {               //当seekBar被拖动时回调
                if (isplay == 0) {                //当音乐在播放时拖动
//                    count--;
                    Intent in = new Intent(TestMusicAct.this, MusicService.class)
                            .putExtra("switch", 0xffffffff);
                    startService(in);
                } else {                                                    //暂停时拖动
                    Intent in = new Intent(TestMusicAct.this, MusicService.class)
                            .putExtra("switch", 0xffffffff);
                    startService(in);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {                 //当seekBar拖动停止时回调
//                count++;
                int progress = seekBar.getProgress();
                int current = (int) (progress * mp.getDuration() / 1000.0);
                mp.seekTo(current);                                  //在拖动位置处播放
                Intent in = new Intent(TestMusicAct.this, MusicService.class)
                        .putExtra("switch", 0xffffffff);
                startService(in);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                bt_sp.setImageResource(R.mipmap.ic_play);
                isplay = 1;

                Intent in = new Intent(TestMusicAct.this, MusicService.class)
                        .putExtra("switch", 0xfffffffe);
                startService(in);
                changeui(position);
                bt_sp.setImageResource(R.mipmap.ic_pause);
                if (operatingAnim != null) {
                    icon.startAnimation(operatingAnim);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        pos = 0;
        super.onResume();
    }

    public void initView() {
        //获取所有控件实例
        lv = (ListView) findViewById(R.id.lv_music);
        bt_sp = (ImageView) findViewById(R.id.sp);
        icon = (ImageView) findViewById(R.id.musicicon);
        bt_next = (ImageView) findViewById(R.id.next);
        bt_last = (ImageView) findViewById(R.id.quiet);
        tv_crt = (TextView) findViewById(R.id.current);
        tv_tol = (TextView) findViewById(R.id.total);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_art = (TextView) findViewById(R.id.tv_art);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);


    }

    private void getData() {
        mlist.clear();
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("category", category);
        paraMap.put("key", PUBLCIKEY);
        paraMap.put("time", "1524103390");

        String url = formatUrlMap(paraMap, true, true);

        String sign = getMD5(urlToJson(url));
        System.out.println("根据data生成的sign = " + sign);

        VideoRequest vr = new VideoRequest(category, "1524103390");
        Datapost dp = new Datapost("audio", vr, sign);

        final Gson gson = new Gson();
        String datas = gson.toJson(dp);
//                String urlEncodee= URLEncoder.encode(datas);
        System.out.println("提交的data参数 = " + datas);
//        final List<AdResult> mlistad = new ArrayList<>();
        api = Net.net(Constants.URL);
        Call<MusicResult> call = api.postmusic(dp);
        call.enqueue(new Callback<MusicResult>() {
            @Override
            public void onResponse(Call<MusicResult> call, Response<MusicResult> response) {
                MusicResult.DataBean model;

                for (int i = 0; i < response.body().getData().size(); i++) {
                    model = response.body().getData().get(i);
                    mlist.add(model);
                    mlist.removeAll(Collections.singleton(null)); //移除所有的null元素
                    list.add("http://192.168.180.1/" + model.getFile());
                }

                adapter = new ListMusicAdapter(TestMusicAct.this, mlist);
                lv.setAdapter(adapter);
                changeui(pos);
            }

            @Override
            public void onFailure(Call<MusicResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        icon.clearAnimation();
        stopService(new Intent(this, MusicService.class));
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp: {
                isplay = 1;
                if (mlist.size() != 0) {

                    if (!isfirst) {

                        if (MusicService.mp != null) {
                            if (MusicService.mp.isPlaying()) {
                                bt_sp.setImageResource(R.mipmap.ic_play);

                                if (operatingAnim != null) {
                                    icon.clearAnimation();
                                }
                            } else {
                                bt_sp.setImageResource(R.mipmap.ic_pause);
                                isplay = 0;
                                if (operatingAnim != null) {
                                    icon.startAnimation(operatingAnim);
                                }
                            }
                        } else {
                            changeui(pos);
                        }
                        isfirst = false;
                    }

                    Intent in = new Intent(this, MusicService.class)
                            .putExtra("switch", 0xffffffff);
                    startService(in);
                }
            }
            break;
            case R.id.next: {
//                count = 0;
                pos++;
                if (pos > mlist.size() - 1) {
                    pos = 0;
                }
                changeui(pos);
                bt_sp.setImageResource(R.mipmap.ic_pause);
                isplay = 1;
                Intent in = new Intent(this, MusicService.class)
                        .putExtra("switch", 0xfffffffe);
                startService(in);
            }
            break;
            case R.id.quiet:

                pos--;
                if (pos < 0) {
                    pos = mlist.size() - 1;
                }
                changeui(pos);
                bt_sp.setImageResource(R.mipmap.ic_pause);
                isplay = 1;
                Intent in = new Intent(this, MusicService.class)
                        .putExtra("switch", 0xfffffffe);
                startService(in);
        }
    }

    public static void changeui(int pos) {

//        ListMusicAdapter.mCurrentItem=pos;
        adapter.setCurrentItem(pos);
        adapter.notifyDataSetChanged();

        tv_name.setText(mlist.get(pos).getTrackName());
        tv_art.setText(mlist.get(pos).getTrackArtist());

        Glide.with(content)//圆角
                .load("http://192.168.180.1/" + mlist.get(pos).getThumb())
                .asBitmap()  //这句不能少，否则下面的方法会报错
                .centerCrop()
                .into(new BitmapImageViewTarget(icon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(content.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        icon.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }


    public static Handler handler = new Handler() {  //更新进度条和音乐播放时间
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                String total = (String) msg.obj;
                tv_tol.setText(total);
            }
            int progress = msg.what;
            int current = (int) (progress * mp.getDuration() / 1000.0);
            seekBar.setProgress(progress);
            tv_crt.setText(new SimpleDateFormat("mm:ss").format(current));
        }
    };
}