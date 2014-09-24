package com.liulei1947.photos.ResideMenu;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.im.task.BRequest;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.adapter.NearPeopleAdapter;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.ActivityBase;
import com.bmob.im.demo.ui.SetMyInfoActivity;
import com.bmob.im.demo.util.CollectionUtils;
import com.bmob.im.demo.view.xlist.XListView;
import com.bmob.im.demo.view.xlist.XListView.IXListViewListener;
import com.liulei1947.photos.ResideMenu.R;
import com.liulei1947.photos.adapter.BaseAdapterHelper;
import com.liulei1947.photos.adapter.QuickAdapter;
import com.liulei1947.photos.bean.PhotosIndex;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * ���������б�
 * 
 * @ClassName: NewFriendActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-6 ����4:28:09
 */
public class HomeActivity extends ActivityBase implements IXListViewListener,OnItemClickListener {

	XListView mListView;
	//NearPeopleAdapter adapter;
	String from = "";
	CustomApplcation mApplication;
	List<User> nears = new ArrayList<User>();
	 private QuickAdapter<PhotosIndex> PhotosIndexAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_near_people);
		initXListView();
	}

	private void initXListView() {
		mListView = (XListView) findViewById(R.id.list_near);
		mListView.setOnItemClickListener(this);
		// ���Ȳ�������ظ���
		mListView.setPullLoadEnable(false);
		// ��������
		mListView.setPullRefreshEnable(true);
		// ���ü�����
		mListView.setXListViewListener(this);
		//
		mListView.pullRefreshing();
		initData();
		
	}

	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		onLoadMore();
		
	}

	private void refreshLoad(){
		if (mListView.getPullLoading()) {
			mListView.stopLoadMore();
		}
	}
	
	private void refreshPull(){
		if (mListView.getPullRefreshing()) {
			mListView.stopRefresh();
		}
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		BmobQuery<PhotosIndex> query = new BmobQuery<PhotosIndex>();
		query.setLimit(500);
		PhotosIndexAdapter.clear();
		query.order("-createdAt");// ����ʱ�併��
		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.findObjects(this, new FindListener<PhotosIndex>() {

			@Override
			public void onSuccess(List<PhotosIndex> arg0) {
				// TODO Auto-generated method stub
				PhotosIndexAdapter.addAll(arg0);
				refreshPull();
				
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowLog("��ѯ���฽�����˳���:"+arg1);
				mListView.setPullLoadEnable(false);
				
			}

		});
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
    

	//******************************
	 //*************************************************************************
    public void initData() {
		// TODO Auto-generated method stub
    	  Log.e("test", "initdata");
		if (PhotosIndexAdapter == null) {
			PhotosIndexAdapter = new QuickAdapter<PhotosIndex>(this, R.layout.photos_index_item) {
				@Override
				protected void convert(BaseAdapterHelper helper, PhotosIndex lost) {
					helper.setText(R.id.tv_title, lost.getTitle())
							.setImageUrl(R.id.titleView,lost.getimageUrl(),R.drawable.holdpic,R.drawable.errorpic,400,300)
							.setText(R.id.tv_describe, lost.getDescribe())
							.setText(R.id.tv_time, lost.getCreatedAt())
							.setText(R.id.tv_photo, lost.getcomment());
				}
			};
			
			
			mListView.setAdapter(PhotosIndexAdapter);
			 Log.e("test", "setadapter");
			 mListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						//onImageGridClick(position-1);
					}
				});

		}

		// Ĭ�ϼ���ʧ�����
		queryPhotosIndex();
	}

	

	private void queryPhotosIndex(){
		//double latitude = Double.parseDouble(mApplication.getLatitude());
		//double longtitude = Double.parseDouble(mApplication.getLongtitude());
		//��ѯ����
		BmobQuery<PhotosIndex> query = new BmobQuery<PhotosIndex>();
		query.setLimit(500);
		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
		query.findObjects(this, new FindListener<PhotosIndex>() {

			@Override
			public void onSuccess(List<PhotosIndex> arg0) {
				// TODO Auto-generated method stub
				PhotosIndexAdapter.clear();
				PhotosIndexAdapter.addAll(arg0);
				
				
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowLog("��ѯ���฽�����˳���:"+arg1);
				mListView.setPullLoadEnable(false);
				refreshLoad();
			}

		});
	}
	
	
	
	
	
	
	
	
	
	
	
}
