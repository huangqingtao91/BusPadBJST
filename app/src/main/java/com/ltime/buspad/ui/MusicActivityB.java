package com.ltime.buspad.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ltime.buspad.R;
import com.ltime.buspad.adapter.ListMusicAdapter;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.MusicResult;
import com.ltime.buspad.bean.VideoRequest;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;

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
 * Created by YF001 on 2018/5/22.
 */

public class MusicActivityB extends Activity{
    private Api api;
    private List<MusicResult.DataBean> mlist = new ArrayList<MusicResult.DataBean>();
    private String category;
    private ListMusicAdapter adapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicb);
        lv= (ListView) findViewById(R.id.lv_music);
        category = getIntent().getExtras().getString("category");
        getData();
    }


    private void getData() {

        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("category", category);
        paraMap.put("key", PUBLCIKEY);
        paraMap.put("time", "1524103390");

        String url = formatUrlMap(paraMap, true, true);

        String sign = getMD5(urlToJson(url));
        System.out.println("根据data生成的sign = " + sign);

        VideoRequest vr = new VideoRequest(category,"1524103390");
        Datapost dp=new Datapost("audio",vr,sign);

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
                }

                adapter = new ListMusicAdapter(MusicActivityB.this, mlist);
                lv.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<MusicResult> call, Throwable t) {

            }
        });
    }
}
