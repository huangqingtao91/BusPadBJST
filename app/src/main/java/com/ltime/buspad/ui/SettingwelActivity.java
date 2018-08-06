package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltime.buspad.R;
import com.ltime.buspad.util.Constants;

public class SettingwelActivity extends Activity {
	SharedPreferences sp = null;
	SharedPreferences.Editor ed = null;
	EditText etnum, etnumsize, etcompany,etadtime;
	TextView tvfonts;
	LinearLayout linearLayout;
	ImageView ivbgcolor, ivtextcolor;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welsetting);

		sp = getSharedPreferences("buspad", MODE_PRIVATE); // ˽������
		ed = sp.edit();// ��ȡ�༭��

		etadtime = (EditText) findViewById(R.id.etadtime);
		etadtime.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		etnum = (EditText) findViewById(R.id.etnum);
		etnum.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		etnumsize = (EditText) findViewById(R.id.etnumsize);
		etnumsize.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		etcompany = (EditText) findViewById(R.id.etcompany);
		ivbgcolor = (ImageView) findViewById(R.id.ivbgcolor);
		ivtextcolor = (ImageView) findViewById(R.id.ivtextcolor);
		linearLayout=(LinearLayout) findViewById(R.id.llfont);
		tvfonts=(TextView) findViewById(R.id.etfonts);
		
		linearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingwelActivity.this, FontsActivity.class);
				startActivity(intent);
			}
		});

		ivbgcolor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingwelActivity.this, CarlogoActivity.class);
				startActivity(intent);
			}
		});

		ivtextcolor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingwelActivity.this, TextColorActivity.class);
				startActivity(intent);
			}
		});

		etcompany.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String ss = etcompany.getText().toString();
				ed.putString("company", ss);
				ed.commit();

			}
		});

		etnum.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String ss = etnum.getText().toString();
				ed.putString("num", ss);
				ed.commit();
			}
		});

		etadtime.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String ss = etadtime.getText().toString();
				ed.putString("adtime", ss);
				ed.commit();
			}
		});

		etnumsize.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String ss = etnumsize.getText().toString();
				ed.putString("textsize", ss);
				ed.commit();
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		etnum.setText(sp.getString("num", ""));
		etadtime.setText(sp.getString("adtime", ""));
		etcompany.setText(sp.getString("company", ""));
		etnumsize.setText(sp.getString("textsize", ""));				

		int imgIndex = sp.getInt("car_logo", -1);
		if (imgIndex != -1) {
			ivbgcolor.setBackgroundResource(Constants.imgRes[imgIndex]);
		}
		
		int imgIndex2 = sp.getInt("TColor", -1);
		if (imgIndex2 != -1) {
			ivtextcolor.setBackgroundColor(Color.parseColor(Constants.imgColor[imgIndex2]));
		}
		
		int imgIndex3 = sp.getInt("fonts", -1);
		if(imgIndex3 != -1){
				tvfonts.setText(Constants.Fonts[imgIndex3]);
		}
	}

}
