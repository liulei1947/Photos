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

/** ����ҳ
  * @ClassName: SplashActivity
  * @Description: TODO
  * @author smile
  * @date 2014-6-4 ����9:45:43
  */
public class PhotosSplashActivity extends BaseActivity {

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN =200;
	ShimmerTextView tv;
	TextView copyright;
	Shimmer shimmer;
	TextView wwwaddress;
	//��λ��ȡ��ǰ�û��ĵ���λ��
	private LocationClient mLocationClient;
	private BaiduReceiver mReceiver;// ע��㲥�����������ڼ��������Լ���֤key

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
		 (Typeface.createFromAsset(getAssets(),"fonts/Signarita Zhai Rhianne.ttf"));    //�����Լ���������ʽ
		 tv.setTypeface
		 (Typeface.createFromAsset(getAssets(),"fonts/Signarita Zhai Rhianne.ttf"));    //�����Լ���������ʽ
	     shimmer = new Shimmer();
	     shimmer.start(tv);
		// ��ʼ��
		BmobChat.getInstance().init(this, Config.applicationId);
		//������λ
		initLocClient();
		
		// ע�� SDK �㲥������
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new BaiduReceiver();
		registerReceiver(mReceiver, iFilter);
				
		
		if (userManager.getCurrentUser() != null) {
			//ÿ���Զ���½��ʱ�����Ҫ�����µ�ǰλ��
			updateUserLocation();
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}
	
	/** ������λ�����µ�ǰ�û��ľ�γ������
	  * @Title: initLocClient
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	private void initLocClient(){
		mLocationClient = CustomApplcation.getInstance().mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ:�߾���ģʽ
		option.setCoorType("bd09ll"); // ������������:�ٶȾ�γ��
		option.setScanSpan(1000);//���÷���λ����ļ��ʱ��Ϊ1000ms:����1000Ϊ�ֶ���λһ�Σ����ڻ����1000��Ϊ��ʱ��λ
		option.setIsNeedAddress(false);//����Ҫ������ַ��Ϣ
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
	 * ����㲥�����࣬���� SDK key ��֤�Լ������쳣�㲥
	 */
	public class BaiduReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				ShowToast("key ��֤����! ���� AndroidManifest.xml �ļ��м�� key ����");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				ShowToast("��ǰ�������Ӳ��ȶ�������������������!");
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// �˳�ʱ���ٶ�λ
		if(mLocationClient!=null && mLocationClient.isStarted()){
			mLocationClient.stop();
		}
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	 //******************************************************************************   
    /**
	 * ��ѯȫ��ͼƬ������Ϣ queryPhotosIndex
	 * 
	 * @return void
	 * @throws
	 */
	private void queryPhotosIndex() {
		BmobQuery<PhotosIndex> query = new BmobQuery<PhotosIndex>();
		query.setLimit(500);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.order("-createdAt");// ����ʱ�併��
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
			//����PicturesShowInformation������idΪ6b6c11c537������
			BmobQuery<PicturesShowInformation> query = new BmobQuery<PicturesShowInformation>();
			query.setLimit(15);
			query.addWhereEqualTo("Id", "show");
			query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
			query.order("-createdAt");// ����ʱ�併��
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
