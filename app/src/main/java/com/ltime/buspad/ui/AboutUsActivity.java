package com.ltime.buspad.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ltime.buspad.R;
import com.ltime.buspad.bean.AboutUsResult;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YF001 on 2018/5/22.
 */

public class AboutUsActivity extends Activity {
    TextView tv;
    ImageView iv;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        tv = (TextView) findViewById(R.id.tv_about);
        iv = (ImageView) findViewById(R.id.iv_about);

        requestAboutus();

    }

    private void requestAboutus() {

        api = Net.net(Constants.URL);
        Call<AboutUsResult> call = api.postaboutus();
        call.enqueue(new Callback<AboutUsResult>() {
            @Override
            public void onResponse(Call<AboutUsResult> call, Response<AboutUsResult> response) {
                System.out.println(response.body().toString());
                String str=response.body().getData().getAbout_info();
                str=str.replace("[","");
                str=str.replace("]","");
                tv.setText(str);
                Glide.with(AboutUsActivity.this).load(response.body().getData().getAbout_img()).into(iv);
            }

            @Override
            public void onFailure(Call<AboutUsResult> call, Throwable t) {

            }
        });
    }
}
