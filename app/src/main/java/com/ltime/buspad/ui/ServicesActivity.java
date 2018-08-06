package com.ltime.buspad.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ltime.buspad.R;

/**
 * Created by YF001 on 2018/5/22.
 */

public class ServicesActivity extends Activity {
    LinearLayout ll_cart, ll_usb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        ll_cart = findViewById(R.id.ll_cart);
        ll_usb = findViewById(R.id.ll_usb);

        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServicesActivity.this,"功能完善中",Toast.LENGTH_LONG).show();
//                startActivity(new Intent(ServicesActivity.this,CartActivity.class));
            }
        });

        ll_usb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServicesActivity.this,"功能完善中",Toast.LENGTH_LONG).show();
            }
        });

    }
}
