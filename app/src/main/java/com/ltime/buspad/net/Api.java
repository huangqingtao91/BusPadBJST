package com.ltime.buspad.net;


import com.ltime.buspad.bean.AboutUsResult;
import com.ltime.buspad.bean.AdResult;
import com.ltime.buspad.bean.AdResult2;
import com.ltime.buspad.bean.Datapost;
import com.ltime.buspad.bean.Datapost2;
import com.ltime.buspad.bean.EbookResult;
import com.ltime.buspad.bean.MusicResult;
import com.ltime.buspad.bean.PreCreateRequest;
import com.ltime.buspad.bean.PreCreateResult;
import com.ltime.buspad.bean.QueryRequest;
import com.ltime.buspad.bean.QueryResult;
import com.ltime.buspad.bean.Song;
import com.ltime.buspad.bean.VideoResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by tao on 2018/4/1.
 */

public interface Api {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=app&m=index")
    Call<VideoResult> postvideo(@Body Datapost data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=app&m=index")
    Call<AdResult> postad(@Body Datapost data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=api&m=getAdvertisingList")
    Call<AdResult2> postad2();

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=api&m=insert_click_record_file")
    Call<String> postadtotal(@Body Datapost2 data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=app&m=index")
    Call<MusicResult> postmusic(@Body Datapost data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=app&m=index")
    Call<EbookResult> postebook(@Body Datapost data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=app&m=index")
    Call<Song> postmusicnew(@Body Datapost data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/Api/request.html")
    Call<PreCreateResult> postprepay(@Body PreCreateRequest data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/Api/request.html")
    Call<QueryResult> postpaid(@Body QueryRequest data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("index.php?c=api&m=get_about")
    Call<AboutUsResult> postaboutus();

}
