package com.ltime.buspad.util;

import java.io.File;

import android.os.Environment;
import android.util.Log;

/**
 * 
 * ���ݺ�׺�������ļ�����
 * 
 * @author zyq
 * 
 */
public class FileUtil {
	private static String type = "*/*";
	public static String ip = "127.0.0.1";
	public static String deviceDMRUDN = "0";
	public static String deviceDMSUDN = "0";
	public static int port = 0;
	
	public static enum FileType{
		IMAGE,SOUND,VEDIO,DOCUMENT
	}
	
	public static boolean matchFile(String filePath,FileType type){
		boolean isMatch = false;
		String typeName = filePath.substring(filePath.lastIndexOf(".")+1).toUpperCase();
		if(FileType.IMAGE.equals(type)){
			if("PNG".equals(typeName)){
				isMatch = true;
			}else if("JPG".equals(typeName)){
				isMatch = true;
			}else if("JPEG".equals(typeName)){
				isMatch = true;
			}else if("BMP".equals(typeName)){
				isMatch = true;
			}else if("WBMP".equals(typeName)){
				isMatch = true;
			}
		}else if(FileType.SOUND.equals(type)){
			if("AAC".equals(typeName)){
				isMatch = true;
			}else if("MP3".equals(typeName)){
				isMatch = true;
			}else if("AMR".equals(typeName)){
				isMatch = true;
			}else if("OGG".equals(typeName)){
				isMatch = true;
			}else if("PCM".equals(typeName)){
				isMatch = true;
			}else if("WMA".equals(typeName)){
				isMatch = true;
			}else if("M4A".equals(typeName)){
				isMatch = true;
			}else if("WAV".equals(typeName)){
				isMatch = true;
			}
		}else if(FileType.VEDIO.equals(type)){
			if("MP4".equals(typeName)){
				isMatch = true;
			}else if("3GP".equals(typeName)){
				isMatch = true;
			}else if("WMV".equals(typeName)){
				isMatch = true;
			}else if("AVI".equals(typeName)){
				isMatch = true;
			}else if("MKV".equals(typeName)){
				isMatch = true;
			}else if ("DIVX".equals(typeName)) {
				isMatch = true;
			}else if ("F4V".equals(typeName)) {
				isMatch = true;
			}else if ("FLV".equals(typeName)) {
				isMatch = true;
			}else if("MOV".equals(typeName)){
				isMatch = true;
			}else if("MPEG".equals(typeName)){
				isMatch = true;
			}else if("MPG".equals(typeName)){
				isMatch = true;
			}else if("M4V".equals(typeName)){
				isMatch = true;
			}else if("RMVB".equals(typeName)){
				isMatch = true;
			}else if ("VOB".equals(typeName)) {
				isMatch = true;
			}else if ("WEBM".equals(typeName)) {
				isMatch = true;
			}else if ("XVID".equals(typeName)) {
				isMatch = true;
			}			
		}else if(FileType.DOCUMENT.equals(type)){
			if("TXT".equals(typeName)){
				isMatch = true;
			}else if("UMD".equals(typeName)){
				isMatch = true;
			}else if("CHM".equals(typeName)){
				isMatch = true;
			}else if("PDF".equals(typeName)){
				isMatch = true;
			}else if("EPUB".equals(typeName)){
				isMatch = true;
			}
		}
		return isMatch;
	}
	
	public static String getMimetType(String fileName){
		String mimetType = "*/*";
		
		if(fileName.toUpperCase().endsWith(".3GP")){
			mimetType = "video/3gpp";
		}else if(fileName.toUpperCase().endsWith(".APK")){
			mimetType = "application/vnd.android.package-archive";
		}else if(fileName.toUpperCase().endsWith(".ASF")){
			mimetType = "video/x-ms-asf";
		}else if(fileName.toUpperCase().endsWith(".AVI")){
			mimetType = "video/x-msvideo";
		}else if(fileName.toUpperCase().endsWith(".BIN")){
			mimetType = "application/octet-stream";
		}else if(fileName.toUpperCase().endsWith(".BMP")){
			mimetType = "image/bmp";
		}else if(fileName.toUpperCase().endsWith(".C")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".CLASS")){
			mimetType = "application/octet-stream";
		}else if(fileName.toUpperCase().endsWith(".CONF")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".CPP")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".DOC")){
			mimetType = "application/msword";
		}else if(fileName.toUpperCase().endsWith(".DOCX")){
			mimetType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		}else if(fileName.toUpperCase().endsWith(".XLS")){
			mimetType = "application/vnd.ms-excel";
		}else if(fileName.toUpperCase().endsWith(".XLSX")){
			mimetType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}else if(fileName.toUpperCase().endsWith(".EXE")){
			mimetType = "application/octet-stream";
		}else if(fileName.toUpperCase().endsWith(".GIF")){
			mimetType = "image/gif";
		}else if(fileName.toUpperCase().endsWith(".GSTAR")){
			mimetType = "application/x-gtar";
		}else if(fileName.toUpperCase().endsWith(".GZ")){
			mimetType = "application/x-gzip";
		}else if(fileName.toUpperCase().endsWith(".H")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".HTM")){
			mimetType = "text/html";
		}else if(fileName.toUpperCase().endsWith(".HTML")){
			mimetType = "text/html";
		}else if(fileName.toUpperCase().endsWith(".JAR")){
			mimetType = "application/java-archive";
		}else if(fileName.toUpperCase().endsWith(".JAVA")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".JPG")){
			mimetType = "image/jpeg";
		}else if(fileName.toUpperCase().endsWith(".JPEG")){
			mimetType = "image/jpeg";
		}else if(fileName.toUpperCase().endsWith(".JS")){
			mimetType = "application/x-javascript";
		}else if(fileName.toUpperCase().endsWith(".LOG")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".M3U")){
			mimetType = "audio/x-mpegurl";
		}else if(fileName.toUpperCase().endsWith(".M4A")){
			mimetType = "audio/mp4a-latm";
		}else if(fileName.toUpperCase().endsWith(".M4B")){
			mimetType = "audio/mp4a-latm";
		}else if(fileName.toUpperCase().endsWith(".M4P")){
			mimetType = "audio/mp4a-latm";
		}else if(fileName.toUpperCase().endsWith(".M4U")){
			mimetType = "video/vnd.mpegurl";
		}else if(fileName.toUpperCase().endsWith(".M4V")){
			mimetType = "video/x-m4v";
		}else if(fileName.toUpperCase().endsWith(".MOV")){
			mimetType = "video/quicktime";
		}else if(fileName.toUpperCase().endsWith(".MP2")){
			mimetType = "audio/x-mpeg";
		}else if(fileName.toUpperCase().endsWith(".MP3")){
			mimetType = "audio/x-mpeg";
		}else if(fileName.toUpperCase().endsWith(".MP4")){
			mimetType = "video/mp4";
		}else if(fileName.toUpperCase().endsWith(".MPC")){
			mimetType = "application/vnd.mpohun.certificate";
		}else if(fileName.toUpperCase().endsWith(".MPE")){
			mimetType = "video/mpeg";
		}else if(fileName.toUpperCase().endsWith(".MPEG")){
			mimetType = "video/mpeg";
		}else if(fileName.toUpperCase().endsWith(".MPG")){
			mimetType = "video/mpeg";
		}else if(fileName.toUpperCase().endsWith(".MPG4")){
			mimetType = "video/mp4";
		}else if(fileName.toUpperCase().endsWith(".MPGA")){
			mimetType = "audio/mpeg";
		}else if(fileName.toUpperCase().endsWith(".MSG")){
			mimetType = "application/vnd.ms-outlook";
		}else if(fileName.toUpperCase().endsWith(".OGG")){
			mimetType = "audio/ogg";
		}else if(fileName.toUpperCase().endsWith(".PDF")){
			mimetType = "application/pdf";
		}else if(fileName.toUpperCase().endsWith(".PNG")){
			mimetType = "image/png";
		}else if(fileName.toUpperCase().endsWith(".PPS")){
			mimetType = "application/vnd.ms-powerpoint";
		}else if(fileName.toUpperCase().endsWith(".PPT")){
			mimetType = "application/vnd.ms-powerpoint";
		}else if(fileName.toUpperCase().endsWith(".PPTX")){
			mimetType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		}else if(fileName.toUpperCase().endsWith(".PROP")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".RC")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".RMVB")){
			mimetType = "audio/x-pn-realaudio";
		}else if(fileName.toUpperCase().endsWith(".RTF")){
			mimetType = "application/rtf";
		}else if(fileName.toUpperCase().endsWith(".SH")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".TAR")){
			mimetType = "application/x-tar";
		}else if(fileName.toUpperCase().endsWith(".TGZ")){
			mimetType = "application/x-compressed";
		}else if(fileName.toUpperCase().endsWith(".TXT")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".WAV")){
			mimetType = "audio/x-wav";
		}else if(fileName.toUpperCase().endsWith(".WMA")){
			mimetType = "audio/x-ms-wma";
		}else if(fileName.toUpperCase().endsWith(".WPS")){
			mimetType = "audio/x-ms-wmv";
		}else if(fileName.toUpperCase().endsWith(".XML")){
			mimetType = "text/plain";
		}else if(fileName.toUpperCase().endsWith(".Z")){
			mimetType = "application/x-compress";
		}else if(fileName.toUpperCase().endsWith(".ZIP")){
			mimetType = "application/x-zip-compressed";
		}
		return mimetType;
	}

	public static String getFileType(String uri) {
		if (uri == null) {
			return type;
		}

		if (uri.endsWith(".mp3")) {
			return "audio/mpeg";
		}

		if (uri.endsWith(".mp4")) {
			return "video/mp4";
		}

		return type;
	}

	public static String getDeviceDMRUDN() {
		return deviceDMRUDN;
	}

	public static String getDeviceDMSUDN() {
		return deviceDMSUDN;
	}

	/**
	 * @param path
	 * �ļ���
	 * @return �����ɹ�����true������false��
	 */
	public static boolean mkdir(String name) {
		boolean bool = false;
		boolean state = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (state) {
			File f = Environment.getExternalStorageDirectory();
			String path = f.getPath();
			String dir = path + "/" + name + "/";
			File file = new File(dir);
			if (!file.exists()) {
				bool = file.mkdir();
			} else {
				Log.i("", "" + dir + "->�Ѵ���");
			}
		} else {
			Log.e("", "->�ⲿ�洢��������");
		}

		return bool;
	}
	
	/**
	 * ɾ���ļ�
	 * @param filePath
	 */
	public static void delTempFiles(String filePath){
		File temp = new File(filePath);
		if(temp.isDirectory()){
			File[] files = temp.listFiles();
			if(files != null && files.length > 0){
				for(File file : files){
					file.delete();
				}
			}
		}else{
			temp.delete();
		}
	}

}
