package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ltime.buspad.R;
import com.ltime.buspad.util.Constants;

public class FontsActivity extends Activity{
	private ListView gv;
	
	SharedPreferences sp;
	
//	int[] imgRes = {R.drawable.carlogo1,R.drawable.carlogo2,R.drawable.carlogo3,R.drawable.carlogo4,R.drawable.carlogo5,R.drawable.carlogo6,R.drawable.carlogo7,R.drawable.carlogo8,R.drawable.carlogo9,R.drawable.carlogo10,R.drawable.carlogo11,R.drawable.carlogo12,R.drawable.carlogo13,R.drawable.carlogo14,R.drawable.carlogo15,R.drawable.carlogo16,R.drawable.carlogo17};
	
	class AppManageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Constants.Fonts.length;
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
			LayoutInflater inflater = LayoutInflater
					.from(getApplicationContext());
			convertView = inflater.inflate(R.layout.font_item, null);
			//convertView.setLayoutParams(new GridView.LayoutParams(1024 / 6, 1024 / 6));
			
			mHolder = new ViewHolder();
			mHolder.mText = (TextView) convertView
					.findViewById(R.id.tvfont);
			mHolder.mText.setText(Constants.Fonts[postion]);
//			mHolder.ImageView.setImageResource(Convert.imgRes[postion]);
			convertView.setTag(mHolder);

			return convertView;
		}

	}

	class ViewHolder {
//		ImageView ImageView;
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
		setContentView(R.layout.fontslist);
		
		sp = getSharedPreferences("buspad", Context.MODE_PRIVATE);
		gv = (ListView) findViewById(R.id.fontlist);
		gv.setAdapter(new AppManageAdapter());
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Editor editor = sp.edit();
				editor.putInt("fonts", arg2);
				editor.commit();
				finish();
			}
		});

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
}
