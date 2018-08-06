package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.ltime.buspad.R;
import com.ltime.buspad.adapter.ListEbookAdapter;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.EbookResult;
import com.ltime.buspad.bean.VideoRequest;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;
import com.ltime.buspad.util.Constants;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ltime.buspad.util.Constants.PUBLCIKEY;
import static com.ltime.buspad.util.Md5.formatUrlMap;
import static com.ltime.buspad.util.Md5.getMD5;
import static com.ltime.buspad.util.Md5.urlToJson;

/**
 * Created by YF001 on 2018/5/15.
 */

public class EbookActivity extends Activity {
    private Api api;
    private GridView gv;
    private ListEbookAdapter adapter;
    private List<EbookResult.DataBean> mlist = new ArrayList<EbookResult.DataBean>();

    String pdfUrl = "http://192.168.180.1/Uploads/ebook/TheShawshankRedemption.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        gv = (GridView) findViewById(R.id.gv_ebook);
        getData();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mlist.get(position).getType().equals("txt")) {
                    downTxt(mlist.get(position).getPath());
                } else {
                    downPdf(mlist.get(position).getPath());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    public void downTxt(String url) {
        OkHttpUtils.get(url)//
                .tag(this)//
                .execute(new DownloadTxtCallBack(Environment.getExternalStorageDirectory() +
                        "/temp", "txt.txt"));//保存到sd卡
    }

    public void downPdf(String url) {
        OkHttpUtils.get(url)//
                .tag(this)//
                .execute(new DownloadPdfCallBack(Environment.getExternalStorageDirectory() +
                        "/temp", "pdf.pdf"));//保存到sd卡
    }

    private class DownloadTxtCallBack extends FileCallback {

        public DownloadTxtCallBack(String destFileDir, String destFileName) {
            super(destFileDir, destFileName);
        }

        @Override
        public void onBefore(BaseRequest request) {
//            btnFileDownload.setText("正在下载中");
        }

        @Override
        public void onResponse(boolean isFromCache, File file, Request request, okhttp3.Response response) {
//            btnFileDownload.setText("下载完成");
//            show_pdf.setVisibility(View.VISIBLE);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +
                    "/temp", "txt.txt"));
            intent.setDataAndType(uri, "text/plain");
            EbookActivity.this.startActivity(intent);
        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);

            String downloadLength = Formatter.formatFileSize(getApplicationContext(),
                    currentSize);
            String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
//            tvDownloadSize.setText(downloadLength + "/" + totalLength);
            String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
//            tvNetSpeed.setText(netSpeed + "/S");
//            tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
        }

        @Override
        public void onError(boolean isFromCache, okhttp3.Call call, @Nullable okhttp3.Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
//            btnFileDownload.setText("下载出错");
        }
    }

    private class DownloadPdfCallBack extends FileCallback {

        public DownloadPdfCallBack(String destFileDir, String destFileName) {
            super(destFileDir, destFileName);
        }

        @Override
        public void onBefore(BaseRequest request) {
//            btnFileDownload.setText("正在下载中");
        }

        @Override
        public void onResponse(boolean isFromCache, File file, Request request, okhttp3.Response response) {
//            btnFileDownload.setText("下载完成");
//            show_pdf.setVisibility(View.VISIBLE);
            startActivity(new Intent(EbookActivity.this, PdfActivity.class));
        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);

            String downloadLength = Formatter.formatFileSize(getApplicationContext(),
                    currentSize);
            String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
//            tvDownloadSize.setText(downloadLength + "/" + totalLength);
            String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
//            tvNetSpeed.setText(netSpeed + "/S");
//            tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
        }

        @Override
        public void onError(boolean isFromCache, okhttp3.Call call, @Nullable okhttp3.Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
//            btnFileDownload.setText("下载出错");
        }
    }

    private void getData() {

        Map<String, String> paraMap = new HashMap<String, String>();
//        paraMap.put("category", category);
        paraMap.put("key", PUBLCIKEY);
        paraMap.put("time", "1524103390");
        String s = formatUrlMap(paraMap, true, true);

        String sign = getMD5(urlToJson(s));
        System.out.println("根据data生成的sign = " + sign);

        VideoRequest data = new VideoRequest("1524103390");
//        Data2 data = new Data2("0", "1", "2", "1524103390");
        Datapost dp = new Datapost("ebook", data, sign);

        final Gson gson = new Gson();
        String datas = gson.toJson(dp);
//                String urlEncodee= URLEncoder.encode(datas);
        System.out.println("提交的data参数 = " + datas);

        api = Net.net(Constants.URL);
        Call<EbookResult> call = api.postebook(dp);
        call.enqueue(new Callback<EbookResult>() {
            @Override
            public void onResponse(Call<EbookResult> call, Response<EbookResult> response) {
                EbookResult.DataBean model;
                for (int i = 0; i < response.body().getData().size(); i++) {
                    model = response.body().getData().get(i);
                    mlist.add(model);
                    mlist.removeAll(Collections.singleton(null)); //移除所有的null元素
                }
                adapter = new ListEbookAdapter(EbookActivity.this, mlist);
                gv.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<EbookResult> call, Throwable t) {

            }
        });

    }
}
