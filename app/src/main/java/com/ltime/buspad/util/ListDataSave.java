package com.ltime.buspad.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ltime.buspad.bean.AdResult2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YF001 on 2018/7/16.
 */

public class ListDataSave {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<AdResult2.DataBean> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public <T> List<AdResult2.DataBean> getDataList(String tag) {
        List<AdResult2.DataBean> datalist=new ArrayList<AdResult2.DataBean>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<AdResult2.DataBean>>() {
        }.getType());
        return datalist;

    }
}
