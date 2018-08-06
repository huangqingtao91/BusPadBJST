package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ltime.buspad.R;
import com.ltime.buspad.bean.ChoosePayWay;
import com.ltime.buspad.bean.PreCreateRequest;
import com.ltime.buspad.bean.PreCreateResult;
import com.ltime.buspad.bean.QueryRequest;
import com.ltime.buspad.bean.QueryResult;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

/**
 * Created by YF001 on 2018/5/21.
 */

public class QRActivity extends Activity {
    private Api api;
    private boolean ispay;
    private String qs;
    private String client_sn;
    private String sn;
    private String terminal_sn;
    private String order_status;
    private String spwd;
    private String total_amount = "1";
    private String payway;
    private String operator = "1234567890";
    private String moviepath;
    private ImageView ivqr;
    private LinearLayout ll_gkm;
    private TextView tv_yes,tv_tip;
    private EditText ed_gkm;
    private String myid;

    SharedPreferences sp = null;
    SharedPreferences.Editor ed = null;
    SharedPreferences sp2 = null;
    SharedPreferences.Editor ed2 = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {  //这个是发送过来的消息
            // 处理从子线程发送过来的消息
            switch (msg.what) {
                case 2:
                    if (order_status.equals("PAID")) {
                        ispay = true;
                        ivqr.setVisibility(View.GONE);
                        ed.putString("ispay" + myid, "1");
                        ed.commit();
                        finish();
                    }
                    break;
                case 1:
                    Glide.with(QRActivity.this).load(qs).into(ivqr);
                    MyThread thread=new MyThread();
                    new Thread(thread).start();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        initView();
        ispay = false;

        String str = getIntent().getExtras().getString("payway");
        moviepath = getIntent().getExtras().getString("moviepath");
        myid = getIntent().getExtras().getString("myid3");

        sp = getSharedPreferences("buspad", MODE_PRIVATE); //私有数据
        ed = sp.edit();
        sp2 = getSharedPreferences("buspad2", MODE_PRIVATE); //私有数据
        ed2 = sp2.edit();

        if (str.equals("支付宝")) {
            payway = "1";
            preCreate();
        }
        if (str.equals("微信")) {
            payway = "3";
            preCreate();
        }
        if (str.equals("观看码(购买过该电影)")) {
            tv_tip.setVisibility(View.GONE);
            ivqr.setVisibility(View.GONE);
            ll_gkm.setVisibility(View.VISIBLE);
        }

//        preCreate();
    }

    private void initView() {
        ivqr = (ImageView) findViewById(R.id.iv_qr);
        ll_gkm = (LinearLayout) findViewById(R.id.ll_gkm);
        tv_yes = (TextView) findViewById(R.id.tv_yes);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        ed_gkm = (EditText) findViewById(R.id.ed_gkm);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ed_gkm.getText().toString();
                String ss = sp2.getString("pwd" + myid, "");
                if (s.equals(ss)) {
                    Intent intent = new Intent();
                    //把返回数据存入Intent
                    intent.putExtra("result", "pwdpay");
                    //设置返回数据
                    QRActivity.this.setResult(RESULT_OK, intent);
                    //关闭Activity
                    QRActivity.this.finish();
                  //  Toast.makeText(QRActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(QRActivity.this, "观看码已经失效！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void rePlay() {
//        Bundle b = new Bundle();
//        Intent in = new Intent();
//        in.setClass(QRActivity.this, PlayMovieActivity.class);
//        b.putString("path", moviepath);
////        b.putString("temp","1");
//        startActivity(in);
//
//    }

    private void preCreate() {

        ChoosePayWay cpy = new ChoosePayWay(total_amount, payway, operator);
        PreCreateRequest data2 = new PreCreateRequest("precreate", cpy);

        //    DataTest dataTest = new DataTest("precreate");
        api = Net.net(Constants.PAYURL);
        Call<PreCreateResult> call = api.postprepay(data2);
        call.enqueue(new Callback<PreCreateResult>() {
            @Override
            public void onResponse(Call<PreCreateResult> call, Response<PreCreateResult> response) {

                qs = response.body().getData().getQr_code_image_url();
                terminal_sn = response.body().getData().getTerminal_sn();
                sn = response.body().getData().getSn();
                client_sn = response.body().getData().getClient_sn();
                //     spwd=client_sn.substring(14);
                spwd = response.body().getData().getSubject();
                ed2.putString("pwd" + myid, spwd);
                ed2.commit();
                System.out.println("返回的数据 ：" + spwd);
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<PreCreateResult> call, Throwable t) {

            }
        });
    }

    private void queryPay() {

        PreCreateResult.DataBean data = new PreCreateResult.DataBean(terminal_sn, sn, client_sn);
        QueryRequest queryRequest = new QueryRequest("query", data);

        api = Net.net(Constants.PAYURL);
        Call<QueryResult> call = api.postpaid(queryRequest);
        call.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
//
                order_status = response.body().getData().getOrder_status();
                System.out.println("返回的数据 ：" + order_status);

                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {

            }
        });


    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            while (!ispay) {

                try {
                    queryPay();
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        ispay = true;
        super.onDestroy();
    }
}
