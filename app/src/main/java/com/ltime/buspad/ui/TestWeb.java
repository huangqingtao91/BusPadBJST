package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.ltime.buspad.R;
import com.ltime.buspad.util.Constants;
import com.ltime.buspad.widget.ProgressWebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YF001 on 2018/7/17.
 */

public class TestWeb extends Activity {
    ProgressWebView webView;

    SharedPreferences sp = null;
    SharedPreferences.Editor ed = null;
    String zid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testweb);
        webView = findViewById(R.id.web);

        sp = getSharedPreferences("buspad", MODE_PRIVATE);
        ed = sp.edit();
        zid=sp.getString("num","1");

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

        webView.setDownloadListener(new DownloadListener(){
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Log.e("HEHE","onDownloadStart被调用：下载链接：" + url);
                new Thread(new DownLoadThread(url)).start();
            }
        });
        WebSettings webSettings = webView.getSettings();
//
        webSettings.setUseWideViewPort(true);
////        webView.setInitialScale(100);
////        //支持缩放，默认为true。
//        webSettings.setSupportZoom(false);
//        //调整图片至适合webview的大小
//        webSettings.setUseWideViewPort(true);
//        // 缩放至屏幕的大小
//        webSettings.setLoadWithOverviewMode(true);
//        //设置默认编码
//        webSettings.setDefaultTextEncodingName("utf-8");
//        //设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
//        //多窗口
//        webSettings.supportMultipleWindows();
//        //获取触摸焦点
        webView.requestFocusFromTouch();
//        //允许访问文件
        webSettings.setAllowFileAccess(true);
//        //开启javascript
//        webSettings.setJavaScriptEnabled(true);
//        //支持通过JS打开新窗口
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        //关闭webview中缓存
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        webView.loadUrl(Constants.CARTURL+zid);
//        webView.loadUrl("http://ltimeadmin.com:8888/index");

    }

    public class DownLoadThread implements Runnable {

        private String dlUrl;

        public DownLoadThread(String dlUrl) {
            this.dlUrl = dlUrl;
        }

        @Override
        public void run() {
            Log.e("HEHE", "开始下载~~~~~");
            InputStream in = null;
            FileOutputStream fout = null;
            try {
                URL httpUrl = new URL(dlUrl);
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                in = conn.getInputStream();
                File downloadFile, sdFile;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Log.e("HEHE","SD卡可写");
                    downloadFile = Environment.getExternalStorageDirectory();
                    sdFile = new File(downloadFile, "ad.xlsx");
                    fout = new FileOutputStream(sdFile);
                }else{
                    Log.e("HEHE","SD卡不存在或者不可读写");
                }
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    fout.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fout != null) {
                    try {
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.e("HEHE", "下载完毕~~~~");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    Log.d(TAG, "requestCode=" + requestCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == ProgressWebView.TYPE_CAMERA) { // 相册选择
                webView.onActivityCallBack(true, null);
            } else if (requestCode == ProgressWebView.TYPE_GALLERY) {// 相册选择
                if (data != null) {
                    Uri uri = data.getData();
                //    Log.d(TAG, "uri=" + uri);
                    if (uri != null) {
                        webView.onActivityCallBack(false, uri);
                    } else {
                        Toast.makeText(TestWeb.this, "获取数据为空", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }

    // 权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ProgressWebView.TYPE_REQUEST_PERMISSION) {
            webView.toCamera();// 到相机
        }
    }

}
