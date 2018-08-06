package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltime.buspad.R;
import com.ltime.buspad.util.AppInfo;
import com.ltime.buspad.util.Convert;

import java.util.ArrayList;
import java.util.List;

public class AppManageActivity extends Activity {
    public static final String TAG = "tao";
    private GridView gv;

    private ArrayList<AppInfo> mInfosList;

    class AppManageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mInfosList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int postion, View convertView, ViewGroup group) {

            ViewHolder mHolder = null;
            final AppInfo mInfos = mInfosList.get(postion);
            LayoutInflater inflater = LayoutInflater
                    .from(getApplicationContext());
            convertView = inflater.inflate(R.layout.app_manage_item, null);
            // convertView.setLayoutParams(new GridView.LayoutParams(1024 / 6,
            // 1024 / 6));

            mHolder = new ViewHolder();
            mHolder.ImageView = (ImageView) convertView
                    .findViewById(R.id.app_manage_icon);
            mHolder.mText = (TextView) convertView
                    .findViewById(R.id.app_manage_text);

            mHolder.ImageView.setImageDrawable(mInfos.appIcon);
            mHolder.mText.setText(mInfos.appName);
            convertView.setTag(mHolder);

            return convertView;
        }

    }

    class ViewHolder {
        ImageView ImageView;
        TextView mText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.app_manage);
        mInfosList = getAllAppInfo();

        gv = (GridView) findViewById(R.id.app_manage_gv);
        gv.setAdapter(new AppManageAdapter());
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(Intent.ACTION_MAIN);

                String packageName = mInfosList.get(arg2).packageName;
                intent.setClassName(packageName, mInfosList.get(arg2).className);
                Log.i(TAG, "������������������������������������������������" + mInfosList.get(arg2).className);
                if ("com.android.settings".equals(packageName)) {
                    Convert.showPasswordDialog(AppManageActivity.this, intent,
                            AppManageActivity.this);
                } else {
                    try {

                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public ArrayList<AppInfo> getAllAppInfo() {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // �����洢��ȡ��Ӧ����Ϣ����
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(intent, 0);
        final List<ResolveInfo> apps = new ArrayList<ResolveInfo>();
        ResolveInfo finalRi = null;
        for (int i = 0; i < resolveInfo.size(); i++) {

            ResolveInfo ri = resolveInfo.get(i);
            if (!ri.activityInfo.packageName.equals("com.byl.testbaidumap")) {

                if ("com.android.settings".equals(ri.activityInfo.packageName)) {
                    finalRi = ri;
                    //apps.add(ri);
                } else if ((ri.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                        && !"com.ztzn.roadpadforcloud"
                        .equals(ri.activityInfo.packageName)) {
                    apps.add(ri);
                }
            }
        }
        if (finalRi != null) {
            apps.add(finalRi);
        }


        int size = apps.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo info = apps.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.appName = (String) info.activityInfo.loadLabel(pm);
            tmpInfo.packageName = info.activityInfo.packageName;
            tmpInfo.className = info.activityInfo.name;
            tmpInfo.appIcon = info.loadIcon(pm);
            appList.add(tmpInfo);

        }
        return appList;
    }

}
