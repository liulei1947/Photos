package com.bmob.im.demo.config;

import android.annotation.SuppressLint;
import android.os.Environment;


/** 
  * @ClassName: BmobConstants
  * @Description: TODO
  * @author smile
  * @date 2014-6-19 下午2:48:33
  */
@SuppressLint("SdCardPath")
public class BmobConstants {

	/**
	 * 存放发送图片的目录
	 */
	public static String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory()	+ "/1204imdemo/image/";
	
	/**
	 * 我的头像保存目录
	 */
	public static String MyAvatarDir = "/sdcard/1204imdemo/avatar/";
	/**
	 * 拍照回调
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
	public static final String EXTRA_STRING = "extra_string";
	public static final String SARTINFO= "StartInfo";
	public static final String Prefix="http://photos1204.qiniudn.com/";
	public static final String SuffixJPG=").jpg";
	public static final String SuffixPNG=").png";
	public static final String IMAGES = "1";
	public static final String englishTitle = "englishTitle";
	public static final String IMAGE_POSITION = "2";
	public static final String ImageCount = "ImageCount";
	public static final String PhotosTitle = "PhotosTitle";
	public static final String htmlUrl = "htmlUrl";
	public static final String videoUrl = "videoUrl";
	public static final String tag = "tag";
}
