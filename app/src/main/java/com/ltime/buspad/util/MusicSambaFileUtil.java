package com.ltime.buspad.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.MusicResult;
import com.ltime.buspad.bean.Song;
import com.ltime.buspad.bean.VideoRequest;
import com.ltime.buspad.net.Api;
import com.ltime.buspad.net.Net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ltime.buspad.util.Constants.PUBLCIKEY;
import static com.ltime.buspad.util.Md5.formatUrlMap;
import static com.ltime.buspad.util.Md5.getMD5;
import static com.ltime.buspad.util.Md5.urlToJson;

public class MusicSambaFileUtil {
    public static final int STATE_NO_NET = 0;
    public static final int STATE_SMB_EXCEPTION = 1;
    public static final int STATE_MALFORMED_RUL_EXCEPTION = 2;
    public static final int STATE_SUCCESS = 3;


    private static Api api;
    private static List<MusicResult.DataBean> mlist = new ArrayList<MusicResult.DataBean>();
    private String category;


    /**
     * ɨ�������ļ�
     *
     * @return
     */
    public static void scanServerFiles(final Context context, final Handler handler) {


//		private void getData() {
//
//			Map<String, String> paraMap = new HashMap<String, String>();
//			paraMap.put("category", "2");
//			paraMap.put("key", PUBLCIKEY);
//			paraMap.put("time", "1524103390");
//
//			String url = formatUrlMap(paraMap, true, true);
//
//			String sign = getMD5(urlToJson(url));
//			System.out.println("根据data生成的sign = " + sign);
//
//			VideoRequest vr = new VideoRequest("2","1524103390");
//			Datapost dp=new Datapost("audio",vr,sign);
//
//			final Gson gson = new Gson();
//			String datas = gson.toJson(dp);
////                String urlEncodee= URLEncoder.encode(datas);
//			System.out.println("提交的data参数 = " + datas);
////        final List<AdResult> mlistad = new ArrayList<>();
//			api = Net.net(Constants.URL);
//			Call<MusicResult> call = api.postmusic(dp);
//			call.enqueue(new Callback<MusicResult>() {
//				@Override
//				public void onResponse(Call<MusicResult> call, Response<MusicResult> response) {
//
//				}
//				@Override
//				public void onFailure(Call<MusicResult> call, Throwable t) {
//
//				}
//			});
//		}

        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Song> songs = new ArrayList<Song>();
                try {
                    Map<String, String> paraMap = new HashMap<String, String>();
                    paraMap.put("category", "2");
                    paraMap.put("key", PUBLCIKEY);
                    paraMap.put("time", "1524103390");

                    String url = formatUrlMap(paraMap, true, true);

                    String sign = getMD5(urlToJson(url));
                    System.out.println("根据data生成的sign = " + sign);

                    VideoRequest vr = new VideoRequest("2", "1524103390");
                    Datapost dp = new Datapost("audio", vr, sign);

                    final Gson gson = new Gson();
                    String datas = gson.toJson(dp);
//                String urlEncodee= URLEncoder.encode(datas);
                    System.out.println("提交的data参数 = " + datas);
//        final List<AdResult> mlistad = new ArrayList<>();
                    api = Net.net(Constants.URL);
                    Call<MusicResult> call = api.postmusic(dp);
                    call.enqueue(new Callback<MusicResult>() {
                        @Override
                        public void onResponse(Call<MusicResult> call, Response<MusicResult> response) {
                            MusicResult.DataBean model;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                model = response.body().getData().get(i);
//                                mlist.add(model);
//                                mlist.removeAll(Collections.singleton(null)); //移除所有的null元素

                                String file=model.getFile();
                                String thumb=model.getThumb();
                                String trackName=model.getTrackName();
                                String trackArtist=model.getTrackArtist();
                                String trackAlbum=model.getTrackAlbum();
                                Song s=new Song(file,thumb,trackName,trackArtist,trackAlbum);
                                songs.add(s);
                                songs.removeAll(Collections.singleton(null)); //移除所有的null元素
                            }

                            Message msg = handler.obtainMessage();
                            msg.what = STATE_SUCCESS;
                            msg.obj = songs;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(Call<MusicResult> call, Throwable t) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.what = STATE_SUCCESS;
                msg.obj = songs;
                handler.sendMessage(msg);
            }
        }).start();


    }

}
