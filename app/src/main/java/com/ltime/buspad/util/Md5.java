package com.ltime.buspad.util;

import com.google.gson.Gson;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by YF001 on 2018/5/15.
 */

public class Md5 {
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    public static String getMD5(String url) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(url.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String str = Integer.toHexString(b & 0xFF);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String urlToJson(String arry) {
        int sun = 0;
        int count = 0;
        String json = "{";
        String data = "";
        String end = "}";

        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
//        String arry = "result=0&description=发送短信成功&taskid=202390811&faillist=1&flag=0";
//        String arry = "category=0&key=5771eea23ac248e34adf4314b2a79757&page=1&pageshow=6&time=1524103390";
        // 计算出字符串里有多少个&符号
        for (int i = 0; i < arry.length(); i++) {
            if ("&".equals(arry.substring(i, i + 1))) {
                sun++;
            }
        }
        String a[] = arry.split("[&]");//以&符号分隔
        //将分隔出来的字符串加上，
        for (int i = 0; i < sun + 1; i++) {
            sb.append(a[i] + ",");
        }
        //以=分隔
        String[] c = sb.toString().split("[=]");
        //将分隔出来的字符串加上，
        for (int j = 0; j < c.length; j++) {
            sb2.append(c[j] + ",");
        }
        //去掉最后面两个,
        String result = sb2.toString().substring(0, sb2.length() - 2);
        // 计算出字符串里有多少个,符号
        for (int i = 0; i < result.length(); i++) {
            if (",".equals(result.substring(i, i + 1))) {
                count++;
            }

        }
        //以，分隔
        String[] d = result.toString().split("[,]");
        for (int i = 0; i < count + 1; i++) {
            if (i % 2 != 0) {//字符下标为奇数时加上，
                data += "\"" + d[i] + "\",";
            } else {
                data += "\"" + d[i] + "\":";
            }

        }
        //拼接json格式
        String jsonArray = (json + data).substring(0,
                (json + data).length() - 1)
                + end;
        System.out.println("转换后："+jsonArray);
        return jsonArray;
    }
}
