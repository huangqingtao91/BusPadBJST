package com.ltime.buspad.util;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ltime.buspad.bean.Song;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SoundService extends Service implements OnCompletionListener,OnPreparedListener,OnErrorListener{
	private Context context = this;
	private CustomCallBack callback;
	private static final String TAG = "MusicServicexx";
	public static MediaPlayer player;
	private MusicReceiver receiver;
	private MusicOperation.MusicInfoReceiver infoReceiver;
	private AssetManager assetManager;
	private List<Song> musicItems;
	
	private int currentIndex = -1;
	private int currentMode = MODE_ORDER;
	private boolean isPause;
	
	
	public static final String PATH_TYPE_ASSETS = "assets";
	public static final String PATH_TYPE_SDCARD = "sdcard";
	
	public static final int MODE_ORDER = 1;			//�б�ѭ��
	public static final int MODE_RANDOM = 2;		//�������
	public static final int MODE_REPEAT = 3;		//����ѭ��
	
	public static final int RESULT_MUSIC_INFO = 520;		//���ص�ǰ������Ϣ
	public static final int RESULT_REAL_TIME_INFO = 521; 	//����ʵʱ������Ϣ
	
	
	//================================�ⲿ����===============================//
	public static final String ACTION_RECEIVER = "com.carlec.music_action";
	
	//--------------------------------��Ϊ---------------------------------//
	public static final String WANNADO = "wannado";
	
	public static final String WANNADO_INITIAL_DATA = "initial_data";
	public static final String WANNADO_PLAY = "play";
	public static final String WANNADO_STOP = "stop";
	public static final String WANNADO_PREVIOUS = "previous";
	public static final String WANNADO_NEXT = "next";
	public static final String WANNADO_CHANGE_VOLUME = "change_volume";
	public static final String WANNADO_CHANGE_PROGRESS = "change_progress";
	public static final String WANNADO_CHANGE_MODE = "change_mode";
	//--------------------------------��Ϊ----------------------------------//
	
	//--------------------------------����---------------------------------//
	public static final String DATA_KEY_VOLUME_VALUE = "volume_value";
	public static final String DATA_KEY_PROGRESS_VALUE = "progress_value";
	public static final String DATA_KEY_ITEMS = "com.carlec.DataPacker";
	public static final String DATA_KEY_MUSIC_ITEM = "com.carlec.Song";
	public static final String DATA_KEY_PLAY_MODE = "play_mode";
	//--------------------------------����---------------------------------//
	
	//================================�ⲿ����==============================//
	
	//private WakeLock mWakeLock;
	
	private AudioManager mAudioManager;
	@Override
	public void onCreate() {
		super.onCreate();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getName());
//        mWakeLock.setReferenceCounted(false);
//        mWakeLock.acquire(30000);
		if(player == null){
			player = new MediaPlayer();
			player.setOnPreparedListener(this);
			player.setOnCompletionListener(this);
			player.setOnErrorListener(this);
			
			receiver = new MusicReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(ACTION_RECEIVER);
			context.registerReceiver(receiver, filter);
			
			infoReceiver = new MusicOperation().new MusicInfoReceiver();
			IntentFilter filter2 = new IntentFilter();
			filter2.addAction(MusicOperation.ACTION_RECEIVER_MUSIC_INFO);
			context.registerReceiver(infoReceiver, filter2);
		}
	}
	
    private OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
        	
            if(focusChange ==  AudioManager.AUDIOFOCUS_LOSS ) {
                sendBroadcast(new Intent("tpd.music.pause"));
            }
        }
    };
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(player != null){
			player.release();
			player = null;
		}
//		if(mWakeLock != null){
//			mWakeLock.release();
//			mWakeLock = null;
//		}
		context.unregisterReceiver(receiver);
		context.unregisterReceiver(infoReceiver);
		mAudioManager.abandonAudioFocus(mAudioFocusListener);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return new SoundBind();
	}
	
	public class SoundBind extends Binder{
		public SoundService getService(){
			return SoundService.this;
		}
	}
	
	/**
	 * ���ڽ��ղ�������Ĺ㲥������
	 * @author Administrator
	 *
	 */
	private class MusicReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String wannado = intent.getStringExtra(SoundService.WANNADO);
			if(WANNADO_PLAY.equals(wannado)){
				Song item = intent.getParcelableExtra(DATA_KEY_MUSIC_ITEM);
				play(item);
			}else if(WANNADO_CHANGE_PROGRESS.equals(wannado)){
				int progress = intent.getIntExtra(DATA_KEY_PROGRESS_VALUE, 0);
				changeProgress(progress);
			}else if(WANNADO_CHANGE_VOLUME.equals(wannado)){
				int volume = intent.getIntExtra(DATA_KEY_VOLUME_VALUE, 10);
				changeVolume(volume);
			}else if(WANNADO_INITIAL_DATA.equals(wannado)){
				musicItems = ((DataPacker)intent.getParcelableExtra(DATA_KEY_ITEMS)).getData();
				if(currentIndex == -1){
					currentIndex = 0;
				}	
			}else if(WANNADO_NEXT.equals(wannado)){
				nextSong();
			}else if(WANNADO_PREVIOUS.equals(wannado)){
				previousSong();
			}else if(WANNADO_STOP.equals(wannado)){
				pause();
			}else if(WANNADO_CHANGE_MODE.equals(wannado)){
				int mode = intent.getIntExtra(DATA_KEY_PLAY_MODE, MODE_ORDER);
				currentMode = mode;
			}
		}
	}
	
	public enum MusicInfoKey{
		SINGER,ALBUM,YEAR,DURATION,DISPLAY_NAME,DATE_ADDED,CURRENT_INDEX
	}
	
	/**
	 * ��ʼ��Ƶ������
	 * @param songs
	 */
	public void initData(ArrayList<Song> songs,CustomCallBack callback){
		//TODO ��ʼ����Ƶ����
		this.callback = callback;
		this.musicItems = songs;
		if(currentIndex == -1){
			currentIndex = 0;
		}		
	}
	
	/**
	 * ��ȡ��ǰ���Ž���λ��[���������������Ϊ100,��˷���ֵ��0~100֮�����]
	 * @return
	 */
	public int getPosition(){
		//TODO ��ȡ��ǰ���Ž���
		if(player == null){
			return 0;
		}
		return player.getCurrentPosition();
	}
	
	/**
	 * �ı䲥��ģʽ
	 * @param mode
	 */
	public void changeMode(int mode){
		//TODO �ı䲥��ģʽ
		currentMode = mode;
	}
	
	/**
	 * ��ͣ����
	 */
	public void pause(){
		//TODO ��ͣ����
		if(!isPause){
			player.pause();
			isPause = true;
		}
	}
	
	/**
	 * ��������
	 * @param item
	 */
	public void play(Song item){
        mAudioManager.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
		//TODO ��������
		if(item == null){
			if(isPause){
				player.start();
				isPause = false;
			}
		}else{
			String path = item.getFile();
			if(path != null){
				playSdCardMusic(path);
				isPause = false;
			}
		}
	}
	
	public void play(Song item,ArrayList<Song> al){
		if(musicItems == null){
			musicItems = al;
		}
        mAudioManager.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
		//TODO ��������
		if(item == null){
			if(isPause){
				player.start();
				isPause = false;
			}
		}else{
			String path = item.getFile();
			if(path != null){
				playSdCardMusic(path);
			}
		}
	}
	
	/**
	 * �ı䲥�Ž���
	 * @param progress
	 */
	public void changeProgress(int progress){
		//TODO �ı䲥�Ž���
		int msec = player.getDuration() * progress / 100;
		player.seekTo(msec);
	}
	
	/**
	 * ��һ�׸���
	 */
	public void nextSong(){
		//TODO ��һ�׸���
		if(musicItems == null || musicItems.size() == 0){
			return;
		}
		switch (currentMode) {
			case MODE_ORDER:{
				currentIndex ++;
				if(currentIndex == musicItems.size()){
					currentIndex = 0;
				}
				Song item = musicItems.get(currentIndex);
				play(item);
			}break;
			case MODE_REPEAT:{
				Song item = musicItems.get(currentIndex);
				play(item);
			}break;
			case MODE_RANDOM:{
				Random random = new Random();
				currentIndex = random.nextInt(musicItems.size() - 1);
				Song item = musicItems.get(currentIndex);
				play(item);
			}break;
		}
	}
	
	/**
	 * ��һ�׸���
	 */
	public void previousSong(){
		//TODO ��һ�׸���
		if(musicItems == null || musicItems.size() == 0){
			return;
		}
		switch (currentMode) {
			case MODE_ORDER:{
				currentIndex --;
				if(currentIndex == -1){
					currentIndex = musicItems.size() - 1;
				}
				Song item = musicItems.get(currentIndex);
				play(item);
			}break;
			case MODE_REPEAT:{
				Song item = musicItems.get(currentIndex);
				play(item);
			}break;
			case MODE_RANDOM:{
				Random random = new Random();
				currentIndex = random.nextInt(musicItems.size() - 1);
				Song item = musicItems.get(currentIndex);
				play(item);
			}break;
		}
	}


	public void changeVolume(int volume){
		//TODO �ı�����
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(volume/100F * maxVolume), 0);
	}
	
	@Override
	public void onPrepared(MediaPlayer arg0) {
		player.start();
		Map<MusicInfoKey,String> infos = new HashMap<MusicInfoKey,String>();
		infos.put(MusicInfoKey.DURATION, player.getDuration() + "");
		infos.put(MusicInfoKey.CURRENT_INDEX,currentIndex + "");
		callback.handleMessage(infos);
		
//		Intent intent = new Intent();
//		intent.setAction(MusicOperation.ACTION_RECEIVER_MUSIC_INFO);
//		int msec = player.getDuration();
//		intent.putExtra(MusicOperation.DATA_KEY_CURRENT_PLAY_INDEX, currentIndex);
//		intent.putExtra(MusicOperation.DATA_KEY_TOTAL_TIME, msec);
//		context.sendBroadcast(intent);
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		nextSong();
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		if(callback != null && what ==1 &&  extra == -1004){
			callback.handleMessage("error");
		}
		return true;
	}
	
	/**
	 * ����AssetsĿ¼�����ļ�
	 * @param fileName
	 */
	@SuppressWarnings("unused")
	private void playAssetsMusic(String fileName){
		AssetFileDescriptor assetFd;
		try {
			assetFd = assetManager.openFd(fileName);
			FileDescriptor fd = assetFd.getFileDescriptor();
			player.reset();
			player.setDataSource(fd, assetFd.getStartOffset(), assetFd.getLength());
			player.prepare();
		} catch (IOException e) {
			Log.e(TAG,e.getStackTrace().toString());
		}
	}
	

	private void playSdCardMusic(String filePath){
		try {
			player.reset();
			player.setDataSource(filePath);
			player.prepare();
			for(int i = 0;i < musicItems.size();i ++){
				if(filePath.equals(musicItems.get(i).getFile())){
					currentIndex = i;
				}
			}
		} catch (IOException e) {
			if(callback != null){
				callback.handleMessage("file_error");
			}
			Log.e(TAG,e.getStackTrace().toString());
		}
	}

	/**
	 * �Զ������ͷ�װList<String>����(����Intent����List��������)
	 * @author Administrator
	 *
	 */
	public static class DataPacker implements Parcelable{
		private List<Song> data = null;
		
		public List<Song> getData() {
			return data;
		}

		public void setData(List<Song> data) {
			this.data = data;
		}
		
		public DataPacker(List<Song> data) {
			this.data = data;
		}
		
		public DataPacker() {}
		
		@Override
		public int describeContents() {
			return 0;
		}
		
		public static Creator<DataPacker> CREATOR = new Creator<DataPacker>(){
			
			@Override
			public DataPacker createFromParcel(Parcel source) {
				List<Song> data = new ArrayList<Song>();
				source.readList(data, Song.class.getClassLoader());
				return new DataPacker(data);
			}

			@Override
			public DataPacker[] newArray(int size) {
				return new DataPacker[size];
			}
			
		};
		
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeList(data);
		}
	}
	
	/**
	 * ������������
	 * @author Administrator
	 *
	 */
	public static class MusicOperation{
		private static Handler handler;
		public static final String DATA_KEY_TOTAL_TIME = "total_time";
		public static final String DATA_KEY_CURRENT_PLAY_INDEX = "current_index";
		
		
		public static final String ACTION_RECEIVER_MUSIC_INFO = "com.carlec.receiver_music_info";
		/**
		 * ��ʼ���赥
		 * @param context
		 * @param data
		 */
		public static void initialData(Context context,List<Song> data){
			Intent intent = new Intent();
			intent.setAction(ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_INITIAL_DATA);
			intent.putExtra(SoundService.DATA_KEY_ITEMS, new DataPacker(data));
			context.sendBroadcast(intent);
		}
		
		/**
		 * ���ŵ�ǰ����
		 * @param context
		 */
		public static void playMusic(Context context,final Handler callback){
			handler = callback;
			Intent intent = new Intent();
			intent.setAction(ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_PLAY);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ����ָ������
		 * @param context	�����Ķ���
		 * @param callback	ͨ����Ϣ���ƻص���msg.arg1��Ӧ�����ܹ�ʱ����msg.arg2��Ӧ��ǰ���������±꣩
		 * @param item		���ֶ���
		 */
		public static void playMusic(Context context,final Handler callback,Song item){
			handler = callback;
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_PLAY);
			intent.putExtra(SoundService.DATA_KEY_MUSIC_ITEM, item);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ��ͣ����
		 * @param context
		 */
		public static void stopMusic(Context context){
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_STOP);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ��һ�׸���
		 * @param context  �����Ķ���
		 * @param callback ͨ����Ϣ���ƻص���msg.arg1��Ӧ�����ܹ�ʱ����msg.arg2��Ӧ��ǰ���������±꣩
		 */
		public static void previousMusic(Context context,final Handler callback){
			handler = callback;
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_PREVIOUS);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ��һ�׸���
		 * @param context	�����Ķ���
		 * @param callback	ͨ����Ϣ���ƻص���msg.arg1��Ӧ�����ܹ�ʱ����msg.arg2��Ӧ��ǰ���������±꣩
		 */
		public static void nextMusic(Context context,final Handler callback){
			handler = callback;
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_NEXT);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ���ò���ģʽ
		 * @param context
		 * @param Mode
		 */
		public static void setPlayMode(Context context,int mode){
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_CHANGE_MODE);
			intent.putExtra(SoundService.DATA_KEY_PLAY_MODE, mode);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ��������(volumeȡֵ��Χ��0~17)
		 * @param context
		 * @param volume
		 */
		public static void setVolume(Context context,int volume){
			if(volume > 17){
				volume = 17;
			}else if(volume < 0){
				volume = 0;
			}
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_CHANGE_VOLUME);
			intent.putExtra(SoundService.DATA_KEY_VOLUME_VALUE, volume);
			context.sendBroadcast(intent);
		}
		
		/**
		 * ���ò��Ž���(progressȡֵ��Χ��0~100)
		 * @param context
		 * @param progress
		 */
		public static void setProgress(Context context,int progress){
			if(progress < 0){
				progress = 0;
			}else if(progress > 100){
				progress = 100;
			}
			Intent intent = new Intent();
			intent.setAction(SoundService.ACTION_RECEIVER);
			intent.putExtra(SoundService.WANNADO, SoundService.WANNADO_CHANGE_PROGRESS);
			intent.putExtra(SoundService.DATA_KEY_PROGRESS_VALUE, progress);
			context.sendBroadcast(intent);
		}
		
		public class MusicInfoReceiver extends BroadcastReceiver{
			
			@Override
			public void onReceive(Context context, Intent intent) {
				if(handler == null){
					return;
				}
				int totalTime = intent.getIntExtra(DATA_KEY_TOTAL_TIME,0);
				int currentIndex = intent.getIntExtra(DATA_KEY_CURRENT_PLAY_INDEX, 0);
				Message msg = new Message();
				msg.arg1 = totalTime;
				msg.arg2 = currentIndex;
				handler.sendMessage(msg);
			}
		}
	}
}
