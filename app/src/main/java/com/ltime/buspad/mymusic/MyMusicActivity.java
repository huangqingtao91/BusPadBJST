package com.ltime.buspad.mymusic;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltime.buspad.R;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.MusicResult;
import com.ltime.buspad.bean.VideoRequest;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ltime.buspad.util.Constants.PUBLCIKEY;
import static com.ltime.buspad.util.Md5.formatUrlMap;
import static com.ltime.buspad.util.Md5.getMD5;
import static com.ltime.buspad.util.Md5.urlToJson;

/**
 * Created by YF001 on 2018/7/28.
 */

public class MyMusicActivity extends Activity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private Api api;
    ListView mListView;
    MyMusicAdapter adapter;
    private List<MusicResult.DataBean> mlist = new ArrayList<MusicResult.DataBean>();
    private MediaPlayer mediaPlayer;

    TextView tv_time, tv_totaltime,tv_title;
    RelativeLayout rl,rl_menu;
    SeekBar seekb;
    ImageView iv_back, iv_play, iv_next, iv_model;

    private int playmodel = 0;

    private int pos = -1;
    private int duration = 0;

    private String category;

    private boolean isStop = false;

    //接受多线程信息，安卓中不允许主线程实现UI更新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            seekb.setProgress(msg.what);
            tv_time.setText(formatTime(msg.what));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymusic);
        mListView = findViewById(R.id.lv_music);
        tv_time = findViewById(R.id.tv_time);
        tv_totaltime = findViewById(R.id.tv_totaltime);
        tv_title = findViewById(R.id.tv_title);
        rl = findViewById(R.id.rl_title);
        rl_menu = findViewById(R.id.rl_menu);

        iv_play = findViewById(R.id.iv_play);
        iv_next = findViewById(R.id.iv_next);
        iv_back = findViewById(R.id.iv_back);
        iv_model = findViewById(R.id.iv_model);
        seekb = findViewById(R.id.listen_jindutiao_sb);

        mediaPlayer = new MediaPlayer();
        category = getIntent().getExtras().getString("category");
        getData(category);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                adapter.setCurrentItem(position);
                pos = position;
                play(mlist, position);
                iv_play.setImageResource(R.mipmap.ic_play);
                adapter.setCurrentItem(position);
                adapter.notifyDataSetChanged();
            }
        });

        seekb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        iv_back.setOnClickListener(this);
        iv_next.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_model.setOnClickListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);


        switch (Integer.valueOf(category)){
            case 1:
                tv_title.setText("ISLAMIC");
                rl.setBackgroundColor(Color.parseColor("#DCAF30"));
                break;
            case 2:
                tv_title.setText("QAWALI");
                rl.setBackgroundColor(Color.parseColor("#B0332F"));
                break;
            case 3:
                tv_title.setText("TECHNO");
                rl.setBackgroundColor(Color.parseColor("#CE4782"));
                break;
            case 4:
                tv_title.setText("JAZZ");
                rl.setBackgroundColor(Color.parseColor("#436298"));
                break;
            case 5:
                tv_title.setText("RELAX");
                rl.setBackgroundColor(Color.parseColor("#92A927"));
                break;
            case 6:
                tv_title.setText("EASY TO LISTEN");
                rl.setBackgroundColor(Color.parseColor("#B0332F"));
                break;
            case 7:
                tv_title.setText("RAP");
                rl.setBackgroundColor(Color.parseColor("#B0332F"));
                break;

            default:
                break;
        }

    }

    private void play(List<MusicResult.DataBean> mlist, int position) {

        mediaPlayer.reset();
        MusicResult.DataBean music = mlist.get(position);//获取传来的值

        try {
            mediaPlayer.setDataSource(Constants.URL + music.getFile());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        tv_totaltime.setText(formatTime(duration));
        new Thread(new SeekBarThread()).start();
        seekb.setMax(duration);
    }


    private void getData(String category) {
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
                    //  list.add("http://192.168.180.1/" + model.getFile());
                }

                adapter = new MyMusicAdapter(MyMusicActivity.this, mlist);
                mListView.setAdapter(adapter);
                if (mlist.size() != 0) {
                    rl_menu.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<MusicResult> call, Throwable t) {

            }
        });
    }

    private String formatTime(int length) {
        Date date = new Date(length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String TotalTime = simpleDateFormat.format(date);

        return TotalTime;

    }

    @Override
    protected void onDestroy() {
        mediaPlayer.reset();
        mediaPlayer.release();
        isStop = true;
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (mlist.size() == 0) {
            return;
        }
        switch (v.getId()) {
            case R.id.iv_model:
                playmodel++;
                if (playmodel == 1) {
                    iv_model.setImageResource(R.mipmap.ic_random);
                }
                if (playmodel == 2) {
                    iv_model.setImageResource(R.mipmap.ic_danqu);
                }
                if (playmodel == 3) {
                    playmodel = 0;
                    iv_model.setImageResource(R.mipmap.ic_shunxu);
                }
                break;
            case R.id.iv_back:

                if (playmodel == 0) {
                    pos--;
                    if (pos <0) {
                        pos = mlist.size() - 1;
                    }
                    play(mlist, pos);
                }
                if (playmodel == 1) {
                    Random random = new Random();
                    pos = random.nextInt(mlist.size());
                    play(mlist, pos);
                }
                if (playmodel == 2) {
                    play(mlist, pos);
                }
                iv_play.setImageResource(R.mipmap.ic_play);
                adapter.setCurrentItem(pos);
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_next:

                if (playmodel == 0) {
                    pos++;
                    if (pos == mlist.size()) {
                        pos = 0;
                    }
                    play(mlist, pos);
                }
                if (playmodel == 1) {
                    Random random = new Random();
                    pos = random.nextInt(mlist.size());
                    play(mlist, pos);
                }
                if (playmodel == 2) {
                    play(mlist, pos);
                }
                iv_play.setImageResource(R.mipmap.ic_play);
                adapter.setCurrentItem(pos);
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_play:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    iv_play.setImageResource(R.mipmap.ic_pause);
                } else {
                    if (pos == -1) {
                        pos = 0;
                        play(mlist, pos);
                        adapter.setCurrentItem(pos);
                        adapter.notifyDataSetChanged();
                        iv_play.setImageResource(R.mipmap.ic_play);
                    } else {
                        mediaPlayer.start();
                        iv_play.setImageResource(R.mipmap.ic_play);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = mediaPlayer.getDuration();
        tv_totaltime.setText(formatTime(mediaPlayer.getDuration()));
        seekb.setMax(mediaPlayer.getDuration());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        if (playmodel == 0) {
            pos++;
            if (pos == mlist.size()) {
                pos = 0;
            }
            play(mlist, pos);
        }
        if (playmodel == 1) {
            Random random = new Random();
            pos = random.nextInt(mlist.size());
            play(mlist, pos);
        }
        if (playmodel == 2) {
            play(mlist, pos);
        }
        adapter.setCurrentItem(pos);
        adapter.notifyDataSetChanged();
    }

    class SeekBarThread implements Runnable {

        @Override
        public void run() {
            while (mediaPlayer != null && isStop == false) {
                // 将SeekBar位置设置到当前播放位置
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
