package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltime.buspad.R;
import com.ltime.buspad.bean.AdResult2;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;
import com.ltime.buspad.util.ListDataSave;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YF001 on 2018/7/16.
 */

public class Mytest extends Activity {
    TextView tv;
    private Api api;
    private List<AdResult2.DataBean> mList = new ArrayList<>();
    Context mContext;
    ListDataSave dataSave;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.testmy);
        dataSave = new ListDataSave(mContext, "mytest");
        tv = findViewById(R.id.mytest);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(dataSave.getDataList("javaBean").get(0).getName());
            }
        });

        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                requestDataAd2();
                return false;
            }
        });
    }


    private void requestDataAd2() {

        api = Net.net(Constants.URL);
        Call<AdResult2> call = api.postad2();
        call.enqueue(new Callback<AdResult2>() {
            @Override
            public void onResponse(Call<AdResult2> call, Response<AdResult2> response) {
                //  AdResult2.DataBean modelad = response.body().getData().;
                tv.setText(gson.toJson(response.body()));
                for (int i = 0; i < response.body().getData().size(); i++) {
                    AdResult2.DataBean modelad = response.body().getData().get(i);
                    AdResult2.DataBean adResult2 = new AdResult2.DataBean(modelad.getAddtime(), modelad.getBasename(), modelad.getDirname(),
                            modelad.getExtension(), modelad.getId(), modelad.getName(), modelad.getStatus());
                    mList.add(adResult2);
                }
                dataSave.setDataList("javaBean", mList);
            }
            @Override
            public void onFailure(Call<AdResult2> call, Throwable t) {

            }
        });
    }

}
