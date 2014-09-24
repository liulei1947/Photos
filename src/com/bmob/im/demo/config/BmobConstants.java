package com.bmob.im.demo.config;

import android.annotation.SuppressLint;
import android.os.Environment;


/** 
  * @ClassName: BmobConstants
  * @Description: TODO
  * @author smile
  * @date 2014-6-19 ����2:48:33
  */
@SuppressLint("SdCardPath")
public class BmobConstants {

	/**
	 * ��ŷ���ͼƬ��Ŀ¼
	 */
	public static String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory()	+ "/1204imdemo/image/";
	
	/**
	 * �ҵ�ͷ�񱣴�Ŀ¼
	 */
	public static String MyAvatarDir = "/sdcard/1204imdemo/avatar/";
	/**
	 * ���ջص�
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//�����޸�ͷ��
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//��������޸�ͷ��
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//ϵͳ�ü�ͷ��
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//����
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//����ͼƬ
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//λ��
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
