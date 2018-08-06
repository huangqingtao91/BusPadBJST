package com.ltime.buspad.ui;

//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                        佛祖保佑 永无Bug !
//

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.ltime.buspad.R;
import com.ltime.buspad.service.ScreenService;
import com.ltime.buspad.util.Convert;
import com.ltime.buspad.util.WifiAdmin;

import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener {

    private static boolean isFirst = true;


    private View musics_relativeLayout, movies_relativeLayout, games_relativeLayout, server_relativeLayout,
            ebooks_relativeLayout, internet_relativeLayout, quran_relativeLayout, aboutus_relativeLayout;
    private ImageView ivar, iven, ivru, ivzh, ives, ivfr;
    // private boolean changeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        musics_relativeLayout = findViewById(R.id.musics_relativeLayout);
        musics_relativeLayout.setOnClickListener(this);
        movies_relativeLayout = findViewById(R.id.movies_relativeLayout);
        movies_relativeLayout.setOnClickListener(this);
        games_relativeLayout = findViewById(R.id.games_relativeLayout);
        games_relativeLayout.setOnClickListener(this);
        server_relativeLayout = findViewById(R.id.server_relativeLayout);
        server_relativeLayout.setOnClickListener(this);
        ebooks_relativeLayout = findViewById(R.id.ebooks_relativeLayout);
        ebooks_relativeLayout.setOnClickListener(this);
        aboutus_relativeLayout = findViewById(R.id.aboutus_relativeLayout);
        aboutus_relativeLayout.setOnClickListener(this);
        internet_relativeLayout = findViewById(R.id.internet_relativeLayout);
        internet_relativeLayout.setOnClickListener(this);
        quran_relativeLayout = findViewById(R.id.quran_relativeLayout);
        quran_relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetworkWifi()) {
                    Intent intent = new Intent();
//                    ComponentName component = new ComponentName("com.byl.testbaidumap",
//                            "com.byl.testbaidumap.MainActivity");
                    ComponentName component = new ComponentName("com.iq.irfanulquran", "com.iq.irfanulquran.SplashScreenActivity");
                    intent.setComponent(component);
                    startActivity(intent);
                }
            }
        });

        ivar = findViewById(R.id.ivLanguageAr);
        iven = findViewById(R.id.ivLanguageEn);
        ivzh = findViewById(R.id.ivLanguageZh);
        ivfr = findViewById(R.id.ivLanguageFr);
        ivru = findViewById(R.id.ivLanguageRu);
        ives = findViewById(R.id.ivLanguageEs);

        ivar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // changeLanguage = true;
                changeLanguage("DE");
            }
        });

        iven.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // changeLanguage = true;
                changeLanguage("EN");
            }
        });

        ivzh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // changeLanguage = true;
                changeLanguage("CN");
            }
        });

        ivru.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // changeLanguage = true;
                changeLanguage("RU");
            }
        });

        ives.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // changeLanguage = true;
                changeLanguage("ES");
            }
        });

        ivfr.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // changeLanguage = true;
                changeLanguage("FR");
            }
        });

//        sp = getSharedPreferences("buspad", MODE_PRIVATE); //私有数据
//        editor = sp.edit();
//        editor.clear();
//


        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ScreenService.class);
        startService(intent);


//
        // IntentFilter filter = new IntentFilter();
        // filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        // filter.addDataScheme("file");
        // filter.setPriority(2147483647);
        // registerReceiver(new BroadcastReceiver() {
        //
        // @Override
        // public void onReceive(Context arg0, Intent intent) {
        // System.out.println("------------------------onReceive-----------------------");
        // try {
        // if (intent.getData().toString() != null
        // && intent.getData().toString().contains("usbhost")) {
        // Intent intentStartPlayer = new Intent();
        // intentStartPlayer.setClassName(
        // "com.softwinner.explore",
        // "com.softwinner.explore.Main");
        // // intentStartPlayer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity(intentStartPlayer);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // }
        // }, filter);
    }

    /**
     * 英语
     */
    public void switchEnglish() {
        switchLanguage("en", "US");
    }

    /**
     * 阿拉伯
     */
    public void switchAre() {
        switchLanguage("ar", "");
    }

    /**
     * 切换语言 lan 语言代号 con 国家代号
     */
    String currentlan = Locale.getDefault().getLanguage();// 获取系统当前语言

    private void switchLanguage(String lan, String con) {
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        DisplayMetrics dm = res.getDisplayMetrics();
        config.locale = new Locale(lan, con);
        res.updateConfiguration(config, dm);

        // 如果切换了语言
        if (!currentlan.equals(config.locale)) {
            // 这里需要重新刷新当前页面中使用到的资源
            onCreate(null);
        }
    }

    @SuppressWarnings({"unused", "unchecked", "rawtypes"})
    private void changeLanguage(String country) {
        Intent intent = new Intent("tpd_change_language");
        intent.putExtra("change_language", country);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
//        if (isFirst) {
//            try {
//                Intent intentStartPlayer = new Intent();
//                intentStartPlayer.setClassName("com.ltime.buspad", "com.ltime.buspad.ui.TestActivity");
//                // intentStartPlayer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intentStartPlayer);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
////            iven.performClick();
//            isFirst = false;
//        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        if (!checkNetworkWifi() && view.getId() != R.id.games_relativeLayout
                && view.getId() != R.id.internet_relativeLayout) {
            return;
        }
        switch (view.getId()) {
            case R.id.musics_relativeLayout:
                intent.setClass(MainActivity.this, MusicActivityX.class);
                break;
            case R.id.movies_relativeLayout:

                intent.setClass(MainActivity.this, MoviesActivity.class);
                break;
            case R.id.aboutus_relativeLayout:
                intent.setClass(MainActivity.this, AboutUsActivity.class);
                break;
            case R.id.ebooks_relativeLayout:
                intent.setClass(MainActivity.this, EbookActivity.class);
                break;
            case R.id.server_relativeLayout:
                intent.setClass(MainActivity.this, TestWeb.class);
                break;
            case R.id.games_relativeLayout:
                intent.setClass(MainActivity.this, AppManageActivity.class);
                break;
            case R.id.internet_relativeLayout:
                Uri content_url = null;
                intent.setPackage("com.android.chrome");
                intent.setAction(Intent.ACTION_VIEW);
                content_url = Uri.parse(getResources().getString(R.string.defaulturl));
                if (content_url != null) {
                    intent.setData(content_url);
                }
                break;
            default:
                break;
        }

        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkNetworkWifi() {
        boolean isConnect = false;
        if (Convert.isConnecting(MainActivity.this)) {
            WifiAdmin wifiAdmin = new WifiAdmin(MainActivity.this);
            String ipAddress = wifiAdmin.intToIpAddress(wifiAdmin.getIPAddress());
            if (ipAddress.contains("192.168.180")) {
                isConnect = true;
            }
        }
        return isConnect;
    }

    private void checkNetwork() {

        new Thread() {
            @Override
            public void run() {

                if (Convert.isConnecting(MainActivity.this)) {
                    WifiAdmin wifiAdmin = new WifiAdmin(MainActivity.this);
                    String ipAddress = wifiAdmin.intToIpAddress(wifiAdmin.getIPAddress());
                    if (ipAddress.contains("192.168.180")) {
                        // 你要执行的方法
//                        HttpUtil.verifyNetwork();
                        // String verify = HttpUtil.verifyNetwork();
                        // System.out.println("vefify==============================="+verify);
//                        String request = HttpUtil.checkNetwork();
                        Message message = new Message();
//                        message.obj = request;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.obj = "2";
                        handler.sendMessage(message);
                    }
                } else {
                    Message message = new Message();
                    message.obj = "-1";
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    // 定义Handler对象
    private Handler handler = new Handler() {
        @Override
        // 当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString().trim();
            System.out.println("message================" + message);
            if ("1".equals(message)) {
                Toast.makeText(MainActivity.this, "No Vefify！", Toast.LENGTH_LONG).show();
                // showPasswordDialog(AppManageActivity.this);
            } else if ("2".equals(message)) {
                //com.android.chrome/com.google.android.apps.chrome.Main
                ComponentName component = null;
                Intent intent = new Intent();
                component = new ComponentName("com.android.chrome", "com.google.android.apps.chrome.Main");
//				Uri content_url = null;
//				intent.setPackage("com.android.chrome");
//				intent.setAction(Intent.ACTION_VIEW);
//				content_url = Uri.parse(getResources().getString(R.string.defaulturl));
//				if (content_url != null) {
//					intent.setData(content_url);
//				}
                try {
                    intent.setComponent(component);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ("3".equals(message)) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.no_interent), Toast.LENGTH_LONG)
                        .show();
                // showPasswordDialog(AppManageActivity.this);
            } else if ("-1".equals(message)) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.no_interent), Toast.LENGTH_LONG)
                        .show();
            }
        }
    };

    @Override
    protected void onPause() {
        // changeLanguage = false;
        super.onPause();
    }
}
