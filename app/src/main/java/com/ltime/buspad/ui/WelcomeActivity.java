package com.ltime.buspad.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltime.buspad.R;
import com.ltime.buspad.util.Constants;

public class WelcomeActivity extends Activity {

	RelativeLayout rl;
	TextView num, company, welcome;
	SharedPreferences sp = null;
	SharedPreferences.Editor ed = null;	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.welcome);
		sp = getSharedPreferences("buspad", MODE_PRIVATE); // ˽������
		ed = sp.edit();// ��ȡ�༭��
		num = (TextView) findViewById(R.id.num);
		company = (TextView) findViewById(R.id.logo);
		welcome = (TextView) findViewById(R.id.welcome);
		
		rl = (RelativeLayout) findViewById(R.id.wel);
		rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in =new Intent();
				in.setClass(WelcomeActivity.this,MainActivity.class);
				startActivity(in);
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		num.setText(sp.getString("num", ""));
		company.setText(sp.getString("company", ""));
		num.setTextSize(2, (float)Integer.parseInt(sp.getString("textsize", "")));
		
				
		int imgIndex = sp.getInt("car_logo", -1);
		if(imgIndex != -1){
			rl.setBackgroundResource(Constants.imgRes[imgIndex]);
		}
		
		int imgIndex2 = sp.getInt("TColor", -1);
		if(imgIndex2 != -1){
			num.setTextColor(Color.parseColor(Constants.imgColor[imgIndex2]));
			company.setTextColor(Color.parseColor(Constants.imgColor[imgIndex2]));
			welcome.setTextColor(Color.parseColor(Constants.imgColor[imgIndex2]));
		}
		
		int imgIndex3 = sp.getInt("fonts", -1);
		if(imgIndex3 != -1){
			Typeface typeFace =Typeface.createFromAsset(getAssets(),Constants.Fonts[imgIndex3]);
			num.setTypeface(typeFace);						
			company.setTypeface(typeFace);						
			welcome.setTypeface(typeFace);						
		}
		
		

		super.onResume();
	}

}
