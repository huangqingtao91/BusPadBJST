package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.ltime.buspad.R;
import com.ltime.buspad.mymusic.MyMusicActivity;
import com.ltime.buspad.util.Convert;
import com.ltime.buspad.util.WifiAdmin;

/**
 * Created by YF001 on 2018/5/15.
 */

public class MusicActivityX extends Activity implements View.OnClickListener {

    public static final String TAO="tao";

    private View music_lslamic_relativeLayout, music_oawali_relativeLayout,
            music_techno_relativeLayout, music_jazz_relativeLayout,
            music_relax_relativeLayout, music_easy_relativeLayout,
            music_rap_relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music);
        music_lslamic_relativeLayout = findViewById(R.id.music_lslamic_relativeLayout);
        music_lslamic_relativeLayout.setOnClickListener(this);
        music_oawali_relativeLayout = findViewById(R.id.music_oawali_relativeLayout);
        music_oawali_relativeLayout.setOnClickListener(this);
        music_techno_relativeLayout = findViewById(R.id.music_techno_relativeLayout);
        music_techno_relativeLayout.setOnClickListener(this);
        music_jazz_relativeLayout = findViewById(R.id.music_jazz_relativeLayout);
        music_jazz_relativeLayout.setOnClickListener(this);
        music_relax_relativeLayout = findViewById(R.id.music_relax_relativeLayout);
        music_relax_relativeLayout.setOnClickListener(this);
        music_easy_relativeLayout = findViewById(R.id.music_easy_relativeLayout);
        music_easy_relativeLayout.setOnClickListener(this);
        music_rap_relativeLayout = findViewById(R.id.music_rap_relativeLayout);
        music_rap_relativeLayout.setOnClickListener(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
//        if(!checkNetworkWifi()){
//            Log.i(TAO, "*******checkNetworkWifi******");
//            return;
//        }
        switch (view.getId()) {
            case R.id.music_lslamic_relativeLayout:
                b.putString("category", "1");
                break;
            case R.id.music_oawali_relativeLayout:
                b.putString("category", "2");
                break;
            case R.id.music_techno_relativeLayout:
                b.putString("category", "3");
                break;
            case R.id.music_jazz_relativeLayout:
                b.putString("category", "4");
                break;
            case R.id.music_relax_relativeLayout:
                b.putString("category", "5");
                break;
            case R.id.music_easy_relativeLayout:
                b.putString("category", "6");
                break;
            case R.id.music_rap_relativeLayout:
                b.putString("category", "0");
                break;
            default:
                break;
        }

        try {
            intent.setClass(MusicActivityX.this, MyMusicActivity.class);
            intent.putExtras(b);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkNetworkWifi() {
        boolean isConnect = false;
        if (Convert.isConnecting(this)) {
            WifiAdmin wifiAdmin = new WifiAdmin(this);
            String ipAddress = wifiAdmin.intToIpAddress(wifiAdmin
                    .getIPAddress());
            if (ipAddress.contains("192.168.180")) {
                isConnect = true;
            }
        }
        return isConnect;
    }

}