package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Created by YF001 on 2018/5/16.
 */

public class TestAct extends Activity {

    //    http://ltimecloud.com/api/Api/request.html
    private TextView tv;
    private ImageView iv;
    private EditText ed;
    private Button btn;
    private Context mContext;
    private Api api;
    private boolean ispay;
    private String qs;
    private String client_sn;
    private String sn;
    private String terminal_sn;
    private String order_status;
    private String spwd;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {  //这个是发送过来的消息
            // 处理从子线程发送过来的消息

            switch (msg.what) {
                case 1:
                    if (order_status.equals("PAID")) {
                        ispay = true;
                        iv.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        tv = (TextView) findViewById(R.id.tv_test);
        iv = (ImageView) findViewById(R.id.iv);
        ed = (EditText) findViewById(R.id.ed);
        btn = (Button) findViewById(R.id.btn);

        ispay = false;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(TestAct.this).load(qs).into(iv);
                new Thread(new MyThread()).start();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ed.getText().toString();
                if (s.equals(spwd)) {
                    Toast.makeText(TestAct.this, "支付成功", Toast.LENGTH_LONG).show();
                    iv.setVisibility(View.GONE);
                }
            }
        });
        preCreate();
    }

    private void preCreate() {

        ChoosePayWay cpy = new ChoosePayWay("1", "1", "1234567890");
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
                System.out.println("返回的数据 ：" + spwd);
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
                message.what = 1;
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
}