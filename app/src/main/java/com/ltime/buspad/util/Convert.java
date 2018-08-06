package com.ltime.buspad.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import com.ltime.buspad.R;
import com.ltime.buspad.ui.MainActivity;
import com.ltime.buspad.ui.SettingwelActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class Convert {



	public static boolean isConnecting(Context context) {
		boolean isConnected = false;
		ConnectivityManager connectManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo connectionNetInfo = connectManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (connectionNetInfo.isConnected()) {
			isConnected = true;
		}
		return isConnected;
	}


	public static void showPasswordDialog2(final Context context,
			final Intent intent, final Activity app) {
		final EditText view = new EditText(context);
//		view.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		view.setTransformationMethod(PasswordTransformationMethod.getInstance());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		view.setLayoutParams(layoutParams);
		new AlertDialog.Builder(context)
				.setView(view)
				.setTitle(context.getResources().getString(R.string.password_vefify))
				.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface arg0) {
						if(app != null)
						app.finish();
					}
				})
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if (Constants.PASSWORD.equals(view.getText()
										.toString().trim())) {
									try {
										context.startActivity(intent);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									Toast.makeText(
											context,
											context.getResources().getString(
													R.string.password_error),
											Toast.LENGTH_SHORT).show();
								}
								if(app != null)
								app.finish();
							}

						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if(app != null)
								app.finish();
							}
						}).show();

	}

	public static void showPasswordDialog(final Context context,
										  final Intent intent, final Activity app) {
		final EditText view = new EditText(context);
		//view.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		view.setTransformationMethod(PasswordTransformationMethod.getInstance());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		view.setLayoutParams(layoutParams);
		new AlertDialog.Builder(context)
				.setView(view)
				.setTitle(context.getResources().getString(R.string.password_vefify))
				.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface arg0) {
						if(app != null)
							app.finish();
					}
				})
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if (Constants.PASSWORD2.equals(view.getText()
										.toString().trim())) {
									try {
										intent.setClass(context, SettingwelActivity.class);
										context.startActivity(intent);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}else if(Constants.PASSWORD.equals(view.getText()
										.toString().trim())){
									context.startActivity(intent);
								}

								else {
									Toast.makeText(
											context,
											context.getResources().getString(
													R.string.password_error),
											Toast.LENGTH_SHORT).show();
								}
								if(app != null)
									app.finish();
							}

						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if(app != null)
									app.finish();
							}
						}).show();

	}

	/**
	 * 用来判断服务是否运行.
	 *
	 * @param context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 将samba路径转换成http协议路径
	 *
	 * @param remotePath
	 * @return
	 */
	public static String convertRemotePathToHttpPath(String remotePath) {
		String result = "";
		String ipVal = FileUtil.ip;
		int portVal = FileUtil.port;
		String httpReq = "http://" + ipVal + ":" + portVal + "/smb=";
		remotePath = remotePath.substring(6);
		try {
			remotePath = URLEncoder.encode(remotePath, "UTF-8");
			result = httpReq + remotePath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static final String convertSmbPathToHttpPath(String smbPath) {
		String result = null;
		String ipVal = FileUtil.ip;
		int portVal = FileUtil.port;
		String httpReq = "http://" + ipVal + ":" + portVal + "/smb=";
		smbPath = smbPath.substring(6);
		try {
			smbPath = URLEncoder.encode(smbPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		result = httpReq + smbPath;
		return result;
	}

	public static String getRunningActivityName(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		return runningActivity;
	}

	public static void getMainImageViewLayoutWidth() {
		MySystemProperties.getLcdDensity();
	}

	@SuppressLint("NewApi")
	public static int getLcdWidth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		// int height = size.y;
		return width;
	}

	@SuppressLint("NewApi")
	public static int getLcdHeight(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		Point size = new Point();
		display.getSize(size);
		int height = size.y;
		// int height = size.y;
		return height;
	}


}
