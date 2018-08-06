package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.ltime.buspad.R;


public class TestActivity extends Activity{
	
	private static boolean isFirst = true;
	private final Handler mHandler = new Handler();

	SharedPreferences spfirst;
	SharedPreferences sp = null;
	SharedPreferences.Editor ed = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("---------------test-Activity--------------------");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mHandler.postDelayed(mHideStatusBar, 2000);


		sp = getSharedPreferences("buspad", MODE_PRIVATE); // 私有数据
		ed = sp.edit();// 获取编辑器
		spfirst = getSharedPreferences("first", MODE_PRIVATE);

		final SharedPreferences.Editor editorfirst = spfirst.edit();
		if (spfirst.getString("first", "").equals("")) {
			// 判断是否第一次安装
			ed.putInt("index", 0);
			ed.commit();

			ed.putString("num", "8");
			ed.putString("adtime", "15");
			ed.putString("textsize", "320");
			ed.putString("company", "FAISAL MOVERS");
			ed.putInt("car_logo", 0);
			ed.putInt("TColor", 1);
			ed.putInt("fonts", 0);
			// ed.putString("fonts", "fonts/spyagencyexpand.ttf");
			ed.commit();

			editorfirst.putString("first", "yes");
			editorfirst.commit();

		}
	}
	
	private final Runnable mHideStatusBar = new Runnable() {
		@Override
		public void run() {			
			Intent in=new Intent();
			in.setClass(TestActivity.this, WelcomeActivity.class);
			startActivity(in);
			finish();
		}
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}


}
