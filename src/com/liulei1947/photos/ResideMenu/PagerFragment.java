package com.liulei1947.photos.ResideMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.view.xlist.XListView;
import com.bmob.im.demo.view.xlist.XListView.IXListViewListener;
import com.liulei1947.photos.PhotosView.ImageGridActivity;
import com.liulei1947.photos.PhotosView.MyViewPager;
import com.liulei1947.photos.PhotosView.MyViewPager.onSimpleClickListener;
import com.liulei1947.photos.PhotosView.PullToRefreshView;
import com.liulei1947.photos.PhotosView.PullToRefreshView.OnFooterRefreshListener;
import com.liulei1947.photos.PhotosView.PullToRefreshView.OnHeaderRefreshListener;
import com.liulei1947.photos.adapter.BaseAdapterHelper;
import com.liulei1947.photos.adapter.QuickAdapter;
import com.liulei1947.photos.bean.PhotosIndex;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.special.ResideMenu.ResideMenu;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PagerFragment extends Fragment implements IXListViewListener, OnHeaderRefreshListener, OnFooterRefreshListener, onSimpleClickListener{
	/** 内容列表   **/
	private XListView listview;
	/** 下拉刷新  **/
	private PullToRefreshView mPullToRefreshView;
	/** 内容  **/
	ImagePagerAdapter imagePagerAdapter;
	/** 子ViewPager **/
	private MyViewPager mPager;
	
	private float xDistance, yDistance;
	/** 记录按下的X坐标  **/
	private float mLastMotionX,mLastMotionY;
	/** 是否是左右滑动   **/
	private boolean mIsBeingDragged = true;
	DisplayImageOptions options;
	 private ResideMenu resideMenu;
	 private QuickAdapter<PhotosIndex> PhotosIndexAdapter;
	 protected static ImageLoader imageLoader = ImageLoader.getInstance();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.pager_fragment, null);
		 MenuActivity parentActivity = (MenuActivity) getActivity();
	        resideMenu = parentActivity.getResideMenu();
	        FrameLayout ignored_view = (FrameLayout) view.findViewById(R.id.addigon);
	        resideMenu.addIgnoredView(ignored_view);
		//mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.view_pager_refresh);
		//mPullToRefreshView.setOnHeaderRefreshListener(this);
		//mPullToRefreshView.setOnFooterRefreshListener(this);
		listview = (XListView) view.findViewById(R.id.view_pager_listview);
		
		addHeadView(inflater);	
		initData();
		return view;
	}
	
	/** 添加HeadView(广告控件) **/
	private void addHeadView(LayoutInflater inflater ) {
		View mHeadView = inflater.inflate(R.layout.fragment_head_view, null);
		mPager = (MyViewPager) mHeadView.findViewById(R.id.fragment_view_pager);
		//AdvAdapter mAdapter = new AdvAdapter();
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getActivity(), null);
		mPager.setAdapter(mAdapter);
		mPager.setOnSimpleClickListener(this);
		
		mPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mPager.getGestureDetector().onTouchEvent(event);
				// TODO Auto-generated method stub
				final float x = event.getRawX();
				final float y = event.getRawY();
				
                switch (event.getAction()) {  
                case MotionEvent.ACTION_DOWN:  
                    xDistance = yDistance = 0f;
                	mLastMotionX = x;
                	mLastMotionY = y;
                case MotionEvent.ACTION_MOVE:  
                    final float xDiff = Math.abs(x - mLastMotionX);
                    final float yDiff = Math.abs(y - mLastMotionY);
                    xDistance += xDiff;
                    yDistance += yDiff;
                    
                    float dx = xDistance - yDistance;
                    /** 左右滑动避免和下拉刷新冲突   **/
                    if (xDistance > yDistance || Math.abs(xDistance - yDistance) < 0.00001f) {
                        mIsBeingDragged = true;
                        mLastMotionX =  x;
                        mLastMotionY = y;
                        ((ViewParent) v.getParent()).requestDisallowInterceptTouchEvent(true);
                    } else {
                        mIsBeingDragged = false;
                        ((ViewParent) v.getParent()).requestDisallowInterceptTouchEvent(false);
                    }
                    break;  
                case MotionEvent.ACTION_UP:  
                 	break;  
                case MotionEvent.ACTION_CANCEL:
                	if(mIsBeingDragged) {
                		((ViewParent) v.getParent()).requestDisallowInterceptTouchEvent(false);
					}
                	break;
                default:  
                    break;  
                }  
                return false;  
			}
		});
		
		listview.addHeaderView(mHeadView);
	}
	
	
	/** 列表适配器   **/
	  
	  //*****************************************************************************************
		
		private class ImagePagerAdapter extends PagerAdapter {
			String title2;
			private int imageCount;
			private LayoutInflater inflater;
			List<PhotosIndex> info;
			public ImagePagerAdapter(Context context, List<PhotosIndex> info2) {
				this.info=info2;
				inflater = getLayoutInflater(getArguments());
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}

			@Override
			public int getCount() {
				return 5;
			}

			@Override
			public Object instantiateItem(ViewGroup view, int position) {
				View imageLayout = inflater.inflate(R.layout.photosview_index_list_header_item_pager_image, view, false);
				assert imageLayout != null;
				//PhotoView imageView = new PhotoView(view.getContext());
				ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
				TextView title2=(TextView)imageLayout.findViewById(R.id.showpaper_title);
				int newposition=position+1;
				 //title2.setText(info.get(position).getTitle());
				//String log=info.get(position).getTitle();
				//Log.e("test", log);
				final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
				String imageUrls="http://photos1204.qiniudn.com/"+"junxun%20"+"(";
				imageLoader.displayImage(imageUrls+newposition+BmobConstants.SuffixJPG, imageView, options, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						spinner.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
							case IO_ERROR:
								message = "Input/Output error";
								break;
							case DECODING_ERROR:
								message = "Image can't be decoded";
								break;
							case NETWORK_DENIED:
								message = "Downloads are denied";
								break;
							case OUT_OF_MEMORY:
								message = "Out Of Memory error";
								break;
							case UNKNOWN:
								message = "Unknown error";
								break;
						}
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

						spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						spinner.setVisibility(View.GONE);
					}
				});

				view.addView(imageLayout, 0);
				return imageLayout;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view.equals(object);
			}

			@Override
			public void restoreState(Parcelable state, ClassLoader loader) {
			}

			@Override
			public Parcelable saveState() {
				return null;
			}
	
		}
//*****************************************
		/** 广告控件适配器  **/
		public class AdvAdapter extends PagerAdapter{
			
			public AdvAdapter() {
				super();
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 4;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				 return arg0 == arg1;  
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				((ViewPager) container).removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
		    	ImageView imageView = new ImageView(getActivity().getApplicationContext());
		    	LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		    	imageView.setLayoutParams(params);
		    	imageView.setImageResource(R.drawable.ic_launcher);
		    	
		        ((ViewPager) container).addView(imageView, 0);  
		        return imageView;
			}
		}


		 //*************************************************************************
    public void initData() {
		// TODO Auto-generated method stub
    	Log.e("test", "initdata");
    	//********************************************************************************
		if (PhotosIndexAdapter == null) {
			PhotosIndexAdapter = new QuickAdapter<PhotosIndex>(getActivity(), R.layout.photos_index_item) {
				@Override
				protected void convert(BaseAdapterHelper helper, PhotosIndex lost) {
					helper.setText(R.id.tv_title, lost.getTitle())
							.setImageUrl(R.id.titleView,lost.getimageUrl(),R.drawable.holdpic,R.drawable.errorpic,400,300)
							.setText(R.id.tv_describe, lost.getDescribe())
							.setText(R.id.tv_time, lost.getCreatedAt())
							.setText(R.id.tv_photo, lost.getcomment());
				}
			};
			listview.setAdapter(PhotosIndexAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						//onImageGridClick(position-1);
					}
				});

		}

		// 默认加载失物界面
		queryPhotosIndex();
	}
    
    public void onImageGridClick(int position) {
    	 
  		PhotosIndex pd=PhotosIndexAdapter.getItem(position);
  		//imageCount=pd.getimageCount();
  		//englishTitle=pd.getenglishTitle();
  		//title=pd.getTitle();
  		Intent intent = new Intent(getActivity(),ImageGridActivity.class);
  		//intent.putExtra(BmobConstants.ImageCount,imageCount);
  		//intent.putExtra(BmobConstants.englishTitle, englishTitle);
  		//intent.putExtra(BmobConstants.PhotosTitle, title);
  		//intent.putExtra(Extra.IMAGES, imageUrls);
  		//intent.putExtra(Extra.IMAGE_POSITION, i);
  		startActivity(intent);
  		getActivity().overridePendingTransition (R.anim.open_next, R.anim.close_main);
  		
  	}
    
    private void queryPhotosIndex() {
		BmobQuery<PhotosIndex> query = new BmobQuery<PhotosIndex>();
		query.setLimit(500);
		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
		query.order("-createdAt");// 按照时间降序
		query.findObjects(getActivity(), new FindListener<PhotosIndex>() {

			@Override
			public void onSuccess(List<PhotosIndex> PhotosIndex) {
				// TODO Auto-generated method stub
				PhotosIndexAdapter.clear();
			
				if (PhotosIndex == null || PhotosIndex.size() == 0) {

					PhotosIndexAdapter.notifyDataSetChanged();
					return;
				}
				
				PhotosIndexAdapter.addAll(PhotosIndex);
				//setanima();
				//listview.setAdapter(PhotosIndexAdapter);

			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				
				listview.setAdapter(PhotosIndexAdapter);

			}
		});

		
	}

	@Override
	public void setOnSimpleClickListenr(int position) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity().getApplicationContext(), "测试", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		BmobQuery<PhotosIndex> query = new BmobQuery<PhotosIndex>();
		query.setLimit(500);
		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.order("-createdAt");// 按照时间降序
		query.findObjects(getActivity(), new FindListener<PhotosIndex>() {

			@Override
			public void onSuccess(List<PhotosIndex> PhotosIndex) {
				// TODO Auto-generated method stub
				PhotosIndexAdapter.clear();
			
				if (PhotosIndex == null || PhotosIndex.size() == 0) {

					PhotosIndexAdapter.notifyDataSetChanged();
					return;
				}
				
				PhotosIndexAdapter.addAll(PhotosIndex);
				//setanima();
				Toast.makeText(getActivity(), "更新完啦！",Toast.LENGTH_SHORT).show();
				 listview.stopRefresh();

			}

			@Override
			public void onError(int code, String arg0) {
				String s = "test";
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "网络错误",Toast.LENGTH_SHORT).show();
				listview.setAdapter(PhotosIndexAdapter);
				 listview.stopRefresh();
			}
		});
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
