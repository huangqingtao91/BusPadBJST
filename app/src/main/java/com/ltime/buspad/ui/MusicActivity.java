package com.ltime.buspad.ui;

import android.R.interpolator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ltime.buspad.R;
import com.ltime.buspad.bean.Song;
import com.ltime.buspad.util.Convert;
import com.ltime.buspad.util.CustomCallBack;
import com.ltime.buspad.util.MusicSambaFileUtil;
import com.ltime.buspad.util.SoundService;

import java.util.ArrayList;
import java.util.Map;

public class MusicActivity extends FragmentActivity implements OnClickListener,OnSeekBarChangeListener,OnTouchListener {

	private Context context = this;
	private Resources res;
	private SoundService soundService;
	private ServiceConnection conn;
	
	private ProgressDialog dialog;
	private RelativeLayout rlLoadingDialog;
	private int currentPlayIndex = -1;
	private ArrayList<Song> musicItems = new ArrayList<Song>();
	private boolean isPlaying = false;
	private int volumeOperLeft;
	private int volumeOperRight;
	private AudioManager audioiManager;
	private CustomCallBack callback;
	private int maxVolume;
	private int currentTimeSec;
	private int currentMusicTimeSec;
	private boolean isRunThreadCodeBlock;
	private Handler handler;
	private int currentProgress;
	private Thread progressThread;
	private Animation cdAnim;
	private boolean isRecall = false;
	private boolean isBinded;
	private int currentRepeatMode = MODE_ORDER;
	
	private static final int MODE_ORDER = 1;
	private static final int MODE_REPEAT = 2;
	private static final int MODE_RANDOM = 3;
	
	private ListView lvMusicList;
	private TextView tvCurrentPlay;
	private TextView tvCurrentTime;
	private TextView tvTotalTime;
	private ImageView ivModeOrder;
	private ImageView ivSingleOrder;
	private ImageView ivModeRandom;
	private ImageView ivPlayingAnim;
	private SeekBar sbProgress;
	private ImageView ivVolumeOper;
	private ImageView ivPrevious;
	private ImageView ivPlayPause;
	private ImageView ivNext;
	private ImageView volumeIcon;
	private final Handler mHandler = new Handler();
	private final Handler mHandlerShowRunnable = new Handler();
	private MusicItemsAdapter adapter;
	private LinearLayout llNoFile,musicLeft,musicRight;
	private TextView musicLeftTextview,musicLeftTextviewTop;
//	private Intent intentService;
	
	private int subscript=0;
	/**
	 * ������ͼ
	 */
	private void loadViews(){
		llNoFile = (LinearLayout) findViewById(R.id.llNoFile);
		lvMusicList = (ListView) findViewById(R.id.lvMusicList);
		lvMusicList.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				subscript=lvMusicList.getFirstVisiblePosition();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		tvCurrentPlay = (TextView) findViewById(R.id.tvCurrentPlay);
		rlLoadingDialog = (RelativeLayout) findViewById(R.id.rlLoadingDialog);
		ivPlayingAnim = (ImageView) findViewById(R.id.ivCdIcon);
		ivVolumeOper = (ImageView) findViewById(R.id.ivVolumeOper);
		tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
		sbProgress = (SeekBar) findViewById(R.id.sbProgress);
		ivPrevious = (ImageView) findViewById(R.id.ivPrevious);
		ivPlayPause = (ImageView) findViewById(R.id.ivPlayPause);		
		ivNext = (ImageView) findViewById(R.id.ivNext);
		tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
		ivModeOrder = (ImageView) findViewById(R.id.ivModeOrder);
		ivSingleOrder = (ImageView) findViewById(R.id.ivModeSingle);
		ivModeRandom = (ImageView) findViewById(R.id.ivModeRandom);
		dialog = new ProgressDialog(this);
//		dialog.setMessage(res.getString(R.string.scanning));
		dialog.setMessage("Scanning please wait ...");
		dialog.setCanceledOnTouchOutside(false);
		
		moveLayout();

	}
	
	public void moveLayout(){
		musicLeft = (LinearLayout) findViewById(R.id.music_left);
		musicLeftTextview = (TextView) findViewById(R.id.music_left_textview);
		musicLeftTextviewTop = (TextView) findViewById(R.id.music_left_textview_top);
		
		musicRight = (LinearLayout) findViewById(R.id.music_right);
		
		volumeIcon = (ImageView) findViewById(R.id.volume_icon);
		
		//��̬����
		LayoutParams layoutParams= musicLeft.getLayoutParams();
		layoutParams.width = (int) (Convert.getLcdWidth(this)*0.385);
		layoutParams.height = (int) (Convert.getLcdHeight(this)*0.933);
		//System.out.println("getLcdHeight="+Convert.getLcdHeight(this));
		
		LayoutParams layoutParamsMusicLeftTextviewTop= musicLeftTextviewTop.getLayoutParams();
		layoutParamsMusicLeftTextviewTop.width = (int) (Convert.getLcdWidth(this)*0.385);
		layoutParamsMusicLeftTextviewTop.height = (int) (Convert.getLcdHeight(this)*0.933*0.018);
		
		LayoutParams layoutParamsMusicLeftTextview= musicLeftTextview.getLayoutParams();
		layoutParamsMusicLeftTextview.width = (int) (Convert.getLcdWidth(this)*0.385);
		layoutParamsMusicLeftTextview.height = (int) (Convert.getLcdHeight(this)*0.933*0.1146);
		
		LayoutParams layoutParamsLvMusicList= lvMusicList.getLayoutParams();
		layoutParamsLvMusicList.width = (int) (Convert.getLcdWidth(this)*0.385);
		layoutParamsLvMusicList.height = (int) (Convert.getLcdHeight(this)*0.933*0.8214);
		
		musicLeftTextviewTop.setLayoutParams(layoutParamsMusicLeftTextviewTop);
		musicLeftTextview.setLayoutParams(layoutParamsMusicLeftTextview);
		lvMusicList.setLayoutParams(layoutParamsLvMusicList);
		
		musicRight.setLayoutParams(layoutParams);
		
		
		
		LayoutParams layoutIvCdIcon= ivPlayingAnim.getLayoutParams();
		layoutIvCdIcon.width = (int) (Convert.getLcdWidth(this)*0.385*0.5487);
		layoutIvCdIcon.height = (int) (Convert.getLcdWidth(this)*0.385*0.5487);
		
		LayoutParams layoutParamsMode= ivModeOrder.getLayoutParams();
		layoutParamsMode.width = (int) (Convert.getLcdWidth(this)*0.385*0.078);
		layoutParamsMode.height = (int) (Convert.getLcdHeight(this)*0.933*0.041);
		
		ivSingleOrder.setLayoutParams(layoutParamsMode);
		ivModeRandom.setLayoutParams(layoutParamsMode);
		volumeIcon.setLayoutParams(layoutParamsMode);
		
		LayoutParams layoutParamsModeVolume= ivVolumeOper.getLayoutParams();
		layoutParamsModeVolume.width = (int) (Convert.getLcdWidth(this)*0.385*0.2792);
		layoutParamsModeVolume.height = (int) (Convert.getLcdHeight(this)*0.933*0.041);
		ivVolumeOper.setLayoutParams(layoutParamsModeVolume);
		
		
		LayoutParams layoutParamsSbProgress= sbProgress.getLayoutParams();
		layoutParamsSbProgress.width = (int) (Convert.getLcdWidth(this)*0.385*0.5);
		//layoutParamsSbProgress.height = (int) (Convert.getLcdHeight(this)*0.933*0.1);
		sbProgress.setLayoutParams(layoutParamsSbProgress);
		
		
		LayoutParams layoutIvPrevious= ivPrevious.getLayoutParams();
		layoutIvPrevious.width = (int) (Convert.getLcdWidth(this)*0.385*0.1461);
		layoutIvPrevious.height = (int) (Convert.getLcdWidth(this)*0.385*0.1461);
		ivPrevious.setLayoutParams(layoutIvPrevious);
		ivNext.setLayoutParams(layoutIvPrevious);
		
		LayoutParams layoutPlayPause= ivPlayPause.getLayoutParams();
		layoutIvPrevious.width = (int) (Convert.getLcdWidth(this)*0.385*0.1883);
		layoutIvPrevious.height = (int) (Convert.getLcdWidth(this)*0.385*0.1883);
		ivPlayPause.setLayoutParams(layoutPlayPause);

		//musicLeft.setLayoutParams(layoutParams);
		//musicLeft.setBackgroundResource(R.drawable.list_bg);
//		ivVideo.setLayoutParams(layoutParams); 
//		ivBrowser.setLayoutParams(layoutParams);		
//		LayoutParams layoutParams2= ivMusic.getLayoutParams();
//		layoutParams2.width = Convert.getLcdWidth(this)/8;
//		layoutParams2.height = Convert.getLcdWidth(this)/8;		
//        ivMusic.setLayoutParams(layoutParams2); 
//        ivBackToAndroid.setLayoutParams(layoutParams2); 
//        ivPicBrowser.setLayoutParams(layoutParams2); 
//        ivEBook.setLayoutParams(layoutParams2); 
	}
	
	/**
	 * ע���¼�
	 */
	private void registerEvent(){
		llNoFile.setOnClickListener(this);
		ivNext.setOnClickListener(this);
		ivPlayPause.setOnClickListener(this);
		ivPrevious.setOnClickListener(this);
		sbProgress.setOnSeekBarChangeListener(this);
		ivVolumeOper.setOnTouchListener(this);
		rlLoadingDialog.setOnClickListener(this);
		ivModeOrder.setOnClickListener(this);
		ivSingleOrder.setOnClickListener(this);
		ivModeRandom.setOnClickListener(this);
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData(){
		loadVolumePosition();
		handler = new Handler(new Callback() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean handleMessage(Message msg) {
				mHandlerShowRunnable.removeCallbacks(dialogShowRunnable);
				ArrayList<Song> songs = new ArrayList<Song>();
				if(dialog != null && dialog.isShowing()){
				    try{
			            dialog.cancel();
			        }catch (Exception e) {
			           e.printStackTrace();
			        }
				}
				switch (msg.what) {

					case MusicSambaFileUtil.STATE_SUCCESS:{
						songs = (ArrayList<Song>) msg.obj;
						for(int i = 0; i< songs.size(); i++){
							musicItems.add(songs.get(i));
						}
						if(musicItems != null && musicItems.size() > 0){
							//TODO ��ʼ����ý������
							if(soundService != null || !isBinded){ //if(soundService == null || !isBinded){
								handler.postDelayed(new Runnable(){
									@Override
									public void run() {
										if(soundService != null){
											soundService.initData(musicItems, callback);
										}
									}
								}, 500);
							}else{
								if(soundService != null){
									soundService.initData(musicItems, callback);
								}
							}
							if (llNoFile.getVisibility() == View.VISIBLE) {
								llNoFile.setVisibility(View.INVISIBLE);
							}
						}else{
							llNoFile.setVisibility(View.VISIBLE);
						}
					}break;
				}
				refresh();
				return true;
			}
		});
		Intent soundIntent = new Intent(context,SoundService.class);
		context.startService(soundIntent);
		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				isBinded = true;
				SoundService.SoundBind binderObject = (SoundService.SoundBind)service;
				soundService = binderObject.getService();
			}
		};
		callback = new CustomCallBack(){
			@Override
			public Object handleMessage(Object argment) {
				if("error".equals(argment.toString())){
					if(rlLoadingDialog.getVisibility() == View.VISIBLE){
						rlLoadingDialog.setVisibility(View.INVISIBLE);
					}
//					Toast.makeText(context, res.getString(R.string.play_exception), Toast.LENGTH_SHORT).show();
					playPause();
					currentPlayIndex = -1;
					initSystemData();
					return null;
				}else if("file_error".equals(argment.toString())){
					if(rlLoadingDialog.getVisibility() == View.VISIBLE){
						rlLoadingDialog.setVisibility(View.INVISIBLE);
					}
//					Toast.makeText(context, res.getString(R.string.play_exception), Toast.LENGTH_SHORT).show();
					playPause();
					currentPlayIndex = -1;
					initSystemData();
					return null;
				}else{
					@SuppressWarnings("unchecked")
					Map<SoundService.MusicInfoKey,String> info = (Map<SoundService.MusicInfoKey,String>)argment;
					int duration = Integer.parseInt(info.get(SoundService.MusicInfoKey.DURATION));
					currentPlayIndex = Integer.parseInt(info.get(SoundService.MusicInfoKey.CURRENT_INDEX));
					tvCurrentPlay.requestFocus();
					currentMusicTimeSec = duration/1000;
					ivPlayingAnim.startAnimation(cdAnim);
					
					if(rlLoadingDialog.getVisibility() == View.VISIBLE){
						rlLoadingDialog.setVisibility(View.INVISIBLE);
						ivNext.setEnabled(true);
						ivPlayPause.setEnabled(true);
						ivPrevious.setEnabled(true);
						sbProgress.setEnabled(true);
					}
					
					ivPlayPause.setImageResource(R.drawable.pause_selector);
					//System.out.println("------------------musicItems.size="+musicItems.size());
					//System.out.println("-----------------currentPlayIndex=="+currentPlayIndex);
					tvCurrentPlay.setText(musicItems.get(currentPlayIndex).getTrackName());//outindex
					currentProgress = 0;
					sbProgress.setProgress(0);
					currentTimeSec = 0;
					
					isPlaying = true;
					if(progressThread != null && progressThread.getState().equals(Thread.State.BLOCKED)){
						progressThread.notify();
					}
					//lvMusicList.setAdapter(new MusicItemsAdapter());
					adapter.notifyDataSetChanged();
					lvMusicList.setSelection(currentPlayIndex);
					//lvMusicList.postInvalidate();
					
					final int totalSecond = duration/1000;
					int minutes = totalSecond/60;
					int second = totalSecond % 60;
					String minutesStr = null;
					String secondStr = null;
					if(minutes < 10){
						minutesStr = "0" + minutes;
					}else{
						minutesStr = "" + minutes;
					}
					
					if(second < 10){
						secondStr = "0" + second;
					}else{
						secondStr = "" + second;
					}
					tvTotalTime.setText(minutesStr + ":" + secondStr);
					
					if(progressThread != null){
						isRecall = true;
						if(!progressThread.getState().equals(Thread.State.TIMED_WAITING)){
							progressThread.interrupt();
						}
						while(isRunThreadCodeBlock){
							synchronized(this){
								try {
									this.wait(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						progressThread = null;
					}
					
					progressThread = new Thread(new Runnable() {
						@Override
						public void run() {
							isRecall = false;
							while(true){
								try {
									if(!isPlaying){
										synchronized(progressThread){
											try {
												progressThread.wait();
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
									}
									
									Thread.sleep(1000);
									isRunThreadCodeBlock = false;
									if(isRecall){
										return;
									}
									isRunThreadCodeBlock = true;
									currentTimeSec = soundService.getPosition()/1000;
									double progress_step = 1.0/totalSecond * 100;
									int realProgress = (int)(currentTimeSec * progress_step);
									currentProgress = realProgress;
									Message msgInner = new Message();
									int minutes = currentTimeSec / 60;
									int seconds = currentTimeSec % 60;
									String minutesStr = minutes >= 10 ? "" + minutes : "0" + minutes;
									String secondsStr = seconds >= 10 ? "" + seconds : "0" + seconds;
									String currentPlayTimeStr = minutesStr + ":" + secondsStr;
									
									msgInner.obj = currentPlayTimeStr;
									msgInner.arg1 = currentProgress;
									progressChanger.sendMessage(msgInner);
								}catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});
					progressThread.start();
					return null;
				}
			}
		};
		cdAnim = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
		cdAnim.setRepeatCount(Animation.INFINITE);
		cdAnim.setDuration(5000);
		cdAnim.setInterpolator(context, interpolator.linear);
	}
	
	/**
	 * ��ȡ������С
	 */
	private void loadVolumePosition(){
		audioiManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioiManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int current = audioiManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		int currentVolumeLevel = (int)(current/(float)maxVolume * 7);
		setVolumeLevelRes(currentVolumeLevel);
	}
	
	/**
	 * ����ϵͳ�ļ�
	 */
	private void initSystemData(){
		ivNext.setEnabled(false);
		ivPlayPause.setEnabled(false);
		ivPrevious.setEnabled(false);
		sbProgress.setEnabled(false);
		musicItems.clear();
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}		
		mHandlerShowRunnable.postDelayed(dialogShowRunnable, 2000);
		MusicSambaFileUtil.scanServerFiles(context,handler);
		if(soundService != null){
			soundService.changeMode(currentRepeatMode);
		}
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if(dialog.isShowing()){
//					if(dialog != null && dialog.isShowing()){
//					    try{
//				            dialog.dismiss();
//				        }catch (Exception e) {
//				           e.printStackTrace();
//				        }
//					}
//				}
//			}
//		}, 10000);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		res = context.getResources();
		//System.out.println("------------------onCreate--------------------------");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_musicnew);
//		intentService = new Intent(this, PlayFileService.class);
//		startService(intentService);
		loadViews();
		registerEvent();
		initData();
		Intent soundIntent = new Intent(context,SoundService.class);
		this.bindService(soundIntent, conn, Service.BIND_AUTO_CREATE);
		IntentFilter filter = new IntentFilter("tpd.music.pause");
		registerReceiver(pauseBroadcastReceiver, filter);
		
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(Intent.ACTION_MEDIA_UNMOUNTED);  
		filter2.addAction(Intent.ACTION_MEDIA_MOUNTED);
		filter2.addDataScheme("file");
		registerReceiver(mediaMountedReceiver, filter2);
		
		//setOnSystemUiVisibilityChangeListener();
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//		
	}
	
private final Handler mHandlerMedia = new Handler();
	
	private BroadcastReceiver mediaMountedReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)){
				mHandlerMedia.postDelayed(runnable, 10000);
		    }else{
		    	initSystemData();
		    }
		}
	};
	
	private final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			initSystemData();
		}
	};
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setOnSystemUiVisibilityChangeListener() {
		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                //if (visibility  != View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {
                    mHandler.postDelayed(mHideStatusBar, 3000);
               //}
            }
        });
    }
	
    private final Runnable mHideStatusBar = new Runnable() {
        @Override
        public void run() {
        	getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
	
	BroadcastReceiver pauseBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if(isPlaying){
				playPause();
			}		
		}
		
	};
	
	@Override
	protected void onStart() {
		super.onStart();
//		Intent soundIntent = new Intent(context,SoundService.class);
//		this.bindService(soundIntent, conn, Service.BIND_AUTO_CREATE);
//		initSystemData();
	}
	
	@Override
	protected void onResume() {
		initSystemData();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    try{
            dialog.dismiss();
        }catch (Exception e) {
           e.printStackTrace();
        }
//	    stopService(intentService);
		this.unbindService(conn);
		Intent soundIntent = new Intent(context,SoundService.class);
		context.stopService(soundIntent);
	}
	
	/**
	 * ˢ��ҳ��
	 */
	public void refresh(){
//		if(musicItems != null){
//			MusicItemsAdapter adapter = new MusicItemsAdapter();
//			lvMusicList.setAdapter(adapter);
//		}
		if (musicItems != null && musicItems.size() > 0) {
			if(adapter == null){
				adapter = new MusicItemsAdapter();
				lvMusicList.setAdapter(adapter);
				//gvPictures.invalidate();
			}else{
				adapter.notifyDataSetChanged();
			}
			if(subscript != 0 && subscript <= musicItems.size()){
				lvMusicList.setSelection(subscript);
			}
			if(currentPlayIndex != -1){
				lvMusicList.setSelection(currentPlayIndex);
			}
			ivNext.setEnabled(true);
			ivPlayPause.setEnabled(true);
			ivPrevious.setEnabled(true);
			sbProgress.setEnabled(true);
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		int[] location = new int[2];
		ivVolumeOper.getLocationOnScreen(location);
		volumeOperLeft = location[0];
		volumeOperRight = volumeOperLeft + ivVolumeOper.getWidth();
		
		super.onWindowFocusChanged(hasFocus);
	}
	
	/**
	 * ��һ�׸���
	 */
	private void previousSong(){
		if(soundService == null || !isBinded || musicItems == null || musicItems.size() == 0){
			return;
		}
		sbProgress.setProgress(0);
		currentPlayIndex --;
		if(currentPlayIndex == -1){
			currentPlayIndex = musicItems.size() - 1;
		}
		//lvMusicList.setAdapter(new MusicItemsAdapter());
		adapter.notifyDataSetChanged();
		lvMusicList.setSelection(currentPlayIndex);
		//lvMusicList.postInvalidate();
		
		showLoadingDialog();
		soundService.previousSong();
		//SoundService.MusicOperation.previousMusic(context, callback);
	}
	
	/**
	 * ��һ�׸���
	 */
	private void nextSong(){
		if(soundService == null || !isBinded || musicItems == null || musicItems.size() == 0){
			return;
		}
		sbProgress.setProgress(0);
		currentPlayIndex ++ ;
		if(currentPlayIndex == musicItems.size()){
			currentPlayIndex = 0;
		}
		//lvMusicList.setAdapter(new MusicItemsAdapter());
		adapter.notifyDataSetChanged();
		lvMusicList.setSelection(currentPlayIndex);
		//lvMusicList.postInvalidate();
		
		showLoadingDialog();
		soundService.nextSong();
		//SoundService.MusicOperation.nextMusic(context, callback);
	}
	
	@Override
	public void onBackPressed() {
		isPlaying = false;
		//finish();
		//System.exit(0);
		super.onBackPressed();
	}
	
	private void showLoadingDialog(){
		rlLoadingDialog.setVisibility(View.VISIBLE);
		ivNext.setEnabled(false);
		ivPlayPause.setEnabled(false);
		ivPrevious.setEnabled(false);
		sbProgress.setEnabled(false);
	}
	
	/**
	 * ���Ż�����ͣ����
	 */
	public void playPause(){
		if(soundService == null || !isBinded){
			return;
		}
		if(musicItems == null || musicItems.size() == 0){
			return;
		}
		if(isPlaying){
			isPlaying = false;
			ivPlayPause.setImageResource(R.drawable.play_selector);
			soundService.pause();
			if(ivPlayingAnim.getAnimation() != null){
				ivPlayingAnim.getAnimation().cancel();
			}
			//SoundService.MusicOperation.stopMusic(context);
		}else{
			isPlaying = true;
			if(progressThread != null && progressThread.getState().equals(Thread.State.WAITING)){
				synchronized (progressThread) {
					progressThread.notifyAll();
				}
			}
			/*sbProgress.setProgress(0);*/
			
			if(ivPlayingAnim.getAnimation() != null){
				ivPlayingAnim.startAnimation(cdAnim);
			}
			ivPlayPause.setImageResource(R.drawable.pause_selector);
//			if (Convert.isServiceRunning(context,
//					"com.carlec.carlecmusic.service.PlayFileService")) {
//				stopService(intentService);
//			}
//			startService(intentService);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					waitServicehandle.sendEmptyMessage(0);

				}
			}).start();

			
		}
	}
	
	Handler waitServicehandle = new Handler() {
		public void handleMessage(Message msg) {
			if(currentPlayIndex == -1){
				if(musicItems != null && musicItems.size() > 0){
					showLoadingDialog();
					soundService.play(musicItems.get(0),musicItems);
					//SoundService.MusicOperation.playMusic(context, callback,musicItems.get(0));
				}
			}else{
				soundService.play(null);
				//SoundService.MusicOperation.playMusic(context, callback);
			}
			
		}
	};
	
	private class MusicItemsAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return musicItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.CENTER_VERTICAL);
			layout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			final TextView tvIndex = new TextView(context);
			tvIndex.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tvIndex.setTextSize(20);
			tvIndex.setTextColor(Color.WHITE);
			tvIndex.setGravity(Gravity.CENTER_VERTICAL);
			tvIndex.setShadowLayer(1.5F, 1.0F, 1.0F, Color.BLACK);
			String indexNo = position < 9 ? "0" + (position + 1) + " ":"" + (position + 1) + " ";
			tvIndex.setText(indexNo);
			layout.addView(tvIndex);
			
			final TextView item = new TextView(context);
			item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			item.setTextSize(20);
			item.setGravity(Gravity.CENTER_VERTICAL);
			item.setTextColor(Color.WHITE);
			item.setSingleLine();
			item.setEllipsize(TruncateAt.END);
			item.setMaxWidth(350);
			item.setShadowLayer(1.5F, 1.0F, 1.0F, Color.BLACK);
			//System.out.println("------------------musicItems.size="+musicItems.size());
			//System.out.println("----------------------------------position="+position);
			item.setText(musicItems.get(position).getTrackName());//outindex
			layout.addView(item);
			
			if(currentPlayIndex == -1 && position == 0){
				tvCurrentPlay.setText(musicItems.get(0).getTrackName());
			}
			
			if(position == currentPlayIndex){
				tvIndex.setTextColor(Color.parseColor("#00FFFF"));
				item.setTextColor(Color.parseColor("#00FFFF"));
			}else{
				layout.setBackgroundResource(android.R.color.transparent);
			}
			final int index = position;
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(soundService == null || !isBinded){
						return;
					}
					currentPlayIndex = index;
					showLoadingDialog();
					soundService.play(musicItems.get(index),musicItems);
					//SoundService.MusicOperation.playMusic(context, callback, musicItems.get(index));
					
					//lvMusicList.setAdapter(new MusicItemsAdapter());
					adapter.notifyDataSetChanged();
					lvMusicList.setSelection(currentPlayIndex);
					//lvMusicList.postInvalidate();
				}
			});
			return layout;
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ivNext:
				nextSong();
				break;
			case R.id.ivPrevious:
				previousSong();
				break;
			case R.id.ivPlayPause:{
				playPause();
				ivPlayPause.setEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									ivPlayPause.setEnabled(true);
								}
							});
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				}
				break;
			case R.id.llNoFile:{
				initSystemData();
			}break;
			case R.id.rlLoadingDialog:{
				return;
			}
			case R.id.ivModeOrder:{
				if(currentRepeatMode != MODE_ORDER){
					currentRepeatMode = MODE_ORDER;
					ivModeOrder.setImageResource(R.mipmap.mode_order_sel);
					ivSingleOrder.setImageResource(R.mipmap.mode_single_nor);
					ivModeRandom.setImageResource(R.mipmap.mode_random_nor);
					soundService.changeMode(SoundService.MODE_ORDER);
					Toast.makeText(context, res.getString(R.string.mode_order), Toast.LENGTH_SHORT).show();
				}
			}break;
			case R.id.ivModeSingle:{
				if(currentRepeatMode != MODE_REPEAT){
					currentRepeatMode = MODE_REPEAT;
					ivModeOrder.setImageResource(R.mipmap.mode_order_nor);
					ivSingleOrder.setImageResource(R.mipmap.mode_single_sel);
					ivModeRandom.setImageResource(R.mipmap.mode_random_nor);
					soundService.changeMode(SoundService.MODE_REPEAT);
					Toast.makeText(context, res.getString(R.string.mode_repeat), Toast.LENGTH_SHORT).show();
				}
			}break;
			case R.id.ivModeRandom:{
				if(currentRepeatMode != MODE_RANDOM){
					currentRepeatMode = MODE_RANDOM;
					ivModeOrder.setImageResource(R.mipmap.mode_order_nor);
					ivSingleOrder.setImageResource(R.mipmap.mode_single_nor);
					ivModeRandom.setImageResource(R.mipmap.mode_random_sel);
					soundService.changeMode(SoundService.MODE_RANDOM);
					Toast.makeText(context, res.getString(R.string.mode_random), Toast.LENGTH_SHORT).show();
				}
			}break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.ivVolumeOper:{
				if(event.getAction() == MotionEvent.ACTION_UP){
					
				}else if(event.getAction() == MotionEvent.ACTION_MOVE){
					float motionPosition = event.getRawX();
					float onceLength = (volumeOperRight - volumeOperLeft)/7;
					int volumeLevel = 0;
					for(int i = 0;i < 8;i ++){
						if(motionPosition < volumeOperLeft + onceLength * (i + 1) && motionPosition >= volumeOperLeft + onceLength * i){
							volumeLevel = i;
							break;
						}else if(motionPosition>volumeOperLeft + onceLength * (8)){
							volumeLevel = 7;
							break;
						}
					}
					changeVolume(volumeLevel);
				}
			}break;
		}
		return true;
	}
	

	private void changeVolume(int volume){
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(volume/7F * maxVolume), 0);
		
		setVolumeLevelRes(volume);
	}
	
	private void setVolumeLevelRes(int level){
		int volumeIconRes = R.mipmap.volume_level_04;
		switch (level) {
			case 0:{
				volumeIconRes = R.mipmap.volume_level_00;
			}break;
			case 1:{
				volumeIconRes = R.mipmap.volume_level_01;
			}break;
			case 2:{
				volumeIconRes = R.mipmap.volume_level_02;
			}break;
			case 3:{
				volumeIconRes = R.mipmap.volume_level_03;
			}break;
			case 4:{
				volumeIconRes = R.mipmap.volume_level_04;
			}break;
			case 5:{
				volumeIconRes = R.mipmap.volume_level_05;
			}break;
			case 6:{
				volumeIconRes = R.mipmap.volume_level_06;
			}break;
			case 7:{
				volumeIconRes = R.mipmap.volume_level_07;
			}break;
		}
		ivVolumeOper.setImageResource(volumeIconRes);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if(isPlaying){		
			int progress = seekBar.getProgress();
			if(progress >= seekBar.getMax()){
				//soundService.nextSong();
				nextSong();
				return;
			}
			switch (seekBar.getId()) {
				case R.id.sbProgress:{
					currentProgress = progress;
					int currentPlayTimeSeekTo = (int)(currentMusicTimeSec * (progress / (float)seekBar.getMax()));
					currentTimeSec = currentPlayTimeSeekTo;
					int minutes = currentPlayTimeSeekTo / 60;
					int seconds = currentPlayTimeSeekTo % 60;
					String minutesStr = minutes >= 10 ? "" + minutes : "0" + minutes;
					String secondsStr = seconds >= 10 ? "" + seconds : "0" + seconds;
					tvCurrentTime.setText(minutesStr + ":" + secondsStr);
					SoundService.MusicOperation.setProgress(context, currentProgress);
				}break;
			}
		}
	}
	
	private Handler progressChanger = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			String currentTimeStr = msg.obj.toString();
			int progress = msg.arg1;
			tvCurrentTime.setText(currentTimeStr);
			sbProgress.setProgress(progress);
			return true;
		}
	});
	
	private final Runnable dialogShowRunnable = new Runnable() {
		@Override
		public void run() {
			if (!isFinishing()) dialog.show();
		}
	};
}
