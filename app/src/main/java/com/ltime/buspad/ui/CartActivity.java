package com.ltime.buspad.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ltime.buspad.R;

/**
 * Created by Administrator on 2016/2/23 22:07.
 */
public class CartActivity extends Activity {
    TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //让Activity显示在锁屏界面上
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        tv1.setTextColor(Color.parseColor("#f77d0a"));
        tv1.setBackgroundColor(Color.parseColor("#FFFFFF"));

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setTextColor(Color.parseColor("#f77d0a"));
                tv1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tv2.setTextColor(Color.parseColor("#FFFFFF"));
                tv2.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv3.setTextColor(Color.parseColor("#FFFFFF"));
                tv3.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv4.setTextColor(Color.parseColor("#FFFFFF"));
                tv4.setBackgroundColor(Color.parseColor("#f77d0a"));
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv2.setTextColor(Color.parseColor("#f77d0a"));
                tv2.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tv1.setTextColor(Color.parseColor("#FFFFFF"));
                tv1.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv3.setTextColor(Color.parseColor("#FFFFFF"));
                tv3.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv4.setTextColor(Color.parseColor("#FFFFFF"));
                tv4.setBackgroundColor(Color.parseColor("#f77d0a"));
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv3.setTextColor(Color.parseColor("#f77d0a"));
                tv3.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tv2.setTextColor(Color.parseColor("#FFFFFF"));
                tv2.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv1.setTextColor(Color.parseColor("#FFFFFF"));
                tv1.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv4.setTextColor(Color.parseColor("#FFFFFF"));
                tv4.setBackgroundColor(Color.parseColor("#f77d0a"));
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv4.setTextColor(Color.parseColor("#f77d0a"));
                tv4.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tv2.setTextColor(Color.parseColor("#FFFFFF"));
                tv2.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv3.setTextColor(Color.parseColor("#FFFFFF"));
                tv3.setBackgroundColor(Color.parseColor("#f77d0a"));

                tv1.setTextColor(Color.parseColor("#FFFFFF"));
                tv1.setBackgroundColor(Color.parseColor("#f77d0a"));
            }
        });

    }

}
