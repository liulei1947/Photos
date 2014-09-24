package com.liulei1947.photos.ResideMenu;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import cn.bmob.im.BmobChat;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.config.Config;
import com.bmob.im.demo.ui.BaseActivity;
import com.bmob.im.demo.ui.LoginActivity;
import com.liulei1947.photos.ResideMenu.R;
import com.liulei1947.photos.Shimmer.Shimmer;
import com.liulei1947.photos.Shimmer.ShimmerTextView;
import com.liulei1947.photos.bean.PhotosIndex;
import com.liulei1947.photos.bean.PicturesShowInformation;

/** 引导页
  * @ClassName: SplashActivity
  * @Description: TODO
  * @author smile
  * @date 2014-6-4 上午9:45:43
  */
public class PhotosSplashActivity extends BaseActivity {

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN =200;
	ShimmerTextView tv;
	TextView copyright;
	Shimmer shimmer;
	TextView wwwaddress;
	//定位获取当前用户的地理位置
	private LocationClient mLocationClient;
	private BaiduReceiver mReceiver;// 注册广播接收器，用于监听网络以及验证key

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_activity_splash);
		queryIndexInfo();
		queryPhotosIndex();
		 tv = (ShimmerTextView)findViewById(R.id.shimmer);
		 copyright = (TextView)findViewById(R.id.copyright);
		 wwwaddress = (TextView)findViewById(R.id.wwwaddress);
		 wwwaddress.setTypeface
		 (Typeface.createFromAsset(getAssets(),"fonts/Signarita Zhai Rhianne.ttf")); 
		 copyright.setTypeface
		 (Typeface.createFromAsset(getAssets(),"fonts/Signarita Zhai Rhianne.ttf"));    //设置自己的字体样式
		 tv.setTypeface
		 (Typeface.createFromAsset(getAssets(),"fonts/Signarita Zhai Rhianne.ttf"));    //设置自己的字体样式
	     shimmer = new Shimmer();
	     shimmer.start(tv);
		// 初始化
		BmobChat.getInstance().init(this, Config.applicationId);
		//开启定位
		initLocClient();
		
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new BaiduReceiver();
		registerReceiver(mReceiver, iFilter);
				
		
		if (userManager.getCurrentUser() != null) {
			//每次自动登陆的时候就需要更新下当前位置
			updateUserLocation();
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}
	
	/** 开启定位，更新当前用户的经纬度坐标
	  * @Title: initLocClient
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	private void initLocClient(){
		mLocationClient = CustomApplcation.getInstance().mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式:高精度模式
		option.setCoorType("bd09ll"); // 设置坐标类型:百度经纬度
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms:低于1000为手动定位一次，大于或等于1000则为定时定位
		option.setIsNeedAddress(false);//不需要包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startAnimActivity(MenuActivity.class);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
				break;
			case GO_LOGIN:
				startAnimActivity(LoginActivity.class);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
				break;
			}
		}
	};
	
	
	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class BaiduReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				ShowToast("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				ShowToast("当前网络连接不稳定，请检查您的网络设置!");
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if(mLocationClient!=null && mLocationClient.isStarted()){
			mLocationClient.stop();
		}
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	 //******************************************************************************   
    /**
	 * 查询全部图片索引信息 queryPhotosIndex
	 * 
	 * @return void
	 * @throws
	 */
	private void queryPhotosIndex() {
		BmobQuery<PhotosIndex> query = new BmobQuery<PhotosIndex>();
		query.setLimit(500);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<PhotosIndex>() {

			@Override
			public void onSuccess(List<PhotosIndex> PhotosIndex) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
	}
	
	//*******************************************************
		public void queryIndexInfo(){
			//查找PicturesShowInformation表里面id为6b6c11c537的数据
			BmobQuery<PicturesShowInformation> query = new BmobQuery<PicturesShowInformation>();
			query.setLimit(15);
			query.addWhereEqualTo("Id", "show");
			query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
			query.order("-createdAt");// 按照时间降序
			query.findObjects(this, new FindListener<PicturesShowInformation>() {
			    @Override
			    public void onSuccess(List<PicturesShowInformation> info2) {
			        // TODO Auto-generated method stub
			    	
			    }
			    @Override
			    public void onError(int code, String msg) {
			        // TODO Auto-generated method stub
			      
			    }
			});
			
			}	
	

	
	
	
	
}
