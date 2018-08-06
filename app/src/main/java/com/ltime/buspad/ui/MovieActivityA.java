package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltime.buspad.R;
import com.ltime.buspad.adapter.ListVideoAdapter;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.VideoRequest;
import com.ltime.buspad.bean.VideoResult;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ltime.buspad.util.Constants.PUBLCIKEY;
import static com.ltime.buspad.util.Md5.formatUrlMap;
import static com.ltime.buspad.util.Md5.getMD5;
import static com.ltime.buspad.util.Md5.urlToJson;

/**
 * Created by YF001 on 2018/5/15.
 */

public class MovieActivityA extends Activity {

    private Context mContext;
    private Api api;
    private GridView gv;
    private TextView tv;
    private RelativeLayout rl;
    private ListVideoAdapter adapter;

    private Gson gson = new Gson();
    private List<VideoResult.DataBean> mlist = new ArrayList<VideoResult.DataBean>();
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviesa);
        mContext = this;
        category = getIntent().getExtras().getString("category");

        gv = (GridView) findViewById(R.id.gv_movies);
        tv = (TextView) findViewById(R.id.tv_title);
        rl =  findViewById(R.id.rl_title);
        getData();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MovieActivityA.this, "点击" + position, Toast.LENGTH_SHORT).show();
                String videopath=mlist.get(position).getDirname() + "/" + mlist.get(position).getBasename();
                try {
                    videopath= java.net.URLEncoder.encode(videopath,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Bundle b=new Bundle();
                b.putString("videopath",videopath);
                b.putString("tag","0");
                Intent intent=new Intent();
                intent.putExtras(b);
                intent.setClass(MovieActivityA.this,AdActivity.class);
                startActivity(intent);
            }
        });

        switch (Integer.valueOf(category)){
            case 1:
                tv.setText("ACTION");
                rl.setBackgroundColor(Color.parseColor("#B0332F"));
                break;
            case 2:
                tv.setText("PREMIERE");
                rl.setBackgroundColor(Color.parseColor("#436298"));
                break;
            case 3:
                tv.setText("COMEDY");
                rl.setBackgroundColor(Color.parseColor("#DCAF30"));
                break;
            case 4:
                tv.setText("ISLAMIC");
                rl.setBackgroundColor(Color.parseColor("#CE4782"));
                break;
            case 5:
                tv.setText("DRAMA");
                rl.setBackgroundColor(Color.parseColor("#9D5427"));
                break;
            case 6:
                tv.setText("CHILDREN");
                rl.setBackgroundColor(Color.parseColor("#239B77"));
                break;
            case 7:
                tv.setText("SONGS");
                rl.setBackgroundColor(Color.parseColor("#92A927"));
                break;

            default:
                break;
        }

    }


    private void getData() {

        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("category", category);
        paraMap.put("key", PUBLCIKEY);
        paraMap.put("time", "1524103390");
        String s = formatUrlMap(paraMap, true, true);

        String sign = getMD5(urlToJson(s));
        System.out.println("根据data生成的sign = " + sign);

        VideoRequest data = new VideoRequest(category, "1524103390");
        Datapost dp = new Datapost("video", data, sign);

        final Gson gson = new Gson();
        String datas = gson.toJson(dp);
//                String urlEncodee= URLEncoder.encode(datas);
        System.out.println("提交的data参数 = " + datas);

        api = Net.net(Constants.URL);
        Call<VideoResult> call = api.postvideo(dp);
        call.enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                VideoResult.DataBean model;
                for (int i = 0; i < response.body().getData().size(); i++) {
                    model = response.body().getData().get(i);
                    mlist.add(model);
                    mlist.removeAll(Collections.singleton(null)); //移除所有的null元素
                }
                adapter = new ListVideoAdapter(mContext, mlist);
                gv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {

            }
        });

    }
}
