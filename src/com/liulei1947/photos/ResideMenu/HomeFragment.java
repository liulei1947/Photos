package com.liulei1947.photos.ResideMenu;

import io.vov.vitamio.demo.VideoViewBuffer;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.view.xlist.XListView;
import com.bmob.im.demo.view.xlist.XListView.IXListViewListener;
import com.liulei1947.photos.PhotosView.ImageGridActivity;
import com.liulei1947.photos.PhotosView.MyViewPager;
import com.liulei1947.photos.PhotosView.MyViewPager.onSimpleClickListener;
import com.liulei1947.photos.PhotosView.WebViewActivity;
import com.liulei1947.photos.adapter.BaseAdapterHelper;
import com.liulei1947.photos.adapter.QuickAdapter;
import com.liulei1947.photos.bean.PhotosIndex;
import com.liulei1947.photos.bean.PicturesShowInformation;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.special.ResideMenu.ResideMenu;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * User: special
 * Date: 13-12-22
 * Time: 涓嬪崍3:26
 * Mail: specialcyci@gmail.com
 */
@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements IXListViewListener, onSimpleClickListener,OnDismissCallback{
	int pagerPosition;
	int imageCount;
	int Count;
	int showpagercount=5;
	String title;
	String htmlUrl;
	String videoUrl;
	String tag;
	TextView title2;
	String englishTitle="junxun%20";
    String imageUrls;
    private View parentView;
    private XListView listview;
    private QuickAdapter<PhotosIndex> PhotosIndexAdapter;
    List<PicturesShowInformation> info3;
    private ResideMenu resideMenu;
    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
    private MyViewPager pager2;
    ImagePagerAdapter2 imagePagerAdapter2;
    DisplayImageOptions options;
    private float xDistance, yDistance;
	/** 记录按下的X坐标  **/
	private float mLastMotionX,mLastMotionY;
	/** 是否是左右滑动   **/
	private boolean mIsBeingDragged = true;

    protected static ImageLoader imageLoader = ImageLoader.getInstance();
    final Handler handler=new Handler(){  
        public void handleMessage(android.os.Message msg) {  
        	if(msg.what==0x123){
        		
        		controlViewPagerShow();
        		
        	}
        	
        	
        };  
    };
    //*************************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.calendar, container, false);
        // add gesture operation's ignored views
        getActivity().setTitle("1204");
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        //添加ignored_view不响应Residemenu的滑动事件
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.addigon);
        resideMenu.addIgnoredView(ignored_view);
        initXListView();
      
        //***********************************************
        new Thread(){  
	          public void run() {  
	                while(true){  
	                   if(true){  
						handler.sendEmptyMessage(0x123);  
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    }  
	                }  
	            };  
	       }.start();  
  
        initData();
        addHeadView(inflater);
        initView();
        //initData();
      
        return parentView;
    }

    private void initView(){
    	initData();
    	
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            	//下拉刷新和Viewpager占了两个position,故position-2
            	onImageGridClick(position-2);
            }
        });
    }
  //**************************************************
  	public void onImageGridClick(int position) {
 
  		PhotosIndex pd=PhotosIndexAdapter.getItem(position);
  		imageCount=pd.getimageCount();
  		englishTitle=pd.getenglishTitle();
  		title=pd.getTitle();
  		Intent intent = new Intent(getActivity(),ImageGridActivity.class);
  		intent.putExtra(BmobConstants.ImageCount,imageCount);
  		intent.putExtra(BmobConstants.englishTitle, englishTitle);
  		intent.putExtra(BmobConstants.PhotosTitle, title);
  		//intent.putExtra(Extra.IMAGES, imageUrls);
  		//intent.putExtra(Extra.IMAGE_POSITION, i);
  		startActivity(intent);
  		getActivity().overridePendingTransition (R.anim.open_next, R.anim.close_main);
  		
  	}
  	
  	//**********************************************************************
	public void setanima() {
		//为ListView创建动画和滑动消除
		swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(PhotosIndexAdapter, (OnDismissCallback) this));
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		swingBottomInAnimationAdapter.setAbsListView(listview);
		//为ListView添加动画和滑动消除
		listview.setAdapter(swingBottomInAnimationAdapter);		
		
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
				//为ListView添加动画和滑动消除
				setanima();
				
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				
				listview.setAdapter(PhotosIndexAdapter);

			}
		});

		
	}
	
	 //*******************************************************************************
	private void initXListView() {
		listview   = (XListView) parentView.findViewById(R.id.listView);
		// 首先不允许加载更多
		listview.setPullLoadEnable(false);
		// 允许下拉
		listview.setPullRefreshEnable(true);
		// 设置监听器
		listview.setXListViewListener(this);
		//
		listview.pullRefreshing();

		
	}
	//************************************************************
    /**  
     * 控制ViewPager自动切换  
    */  
    public void controlViewPagerShow(){
    	int current = pager2.getCurrentItem();
	    if(current==showpagercount-1){
    	pager2.setCurrentItem(0);
    	}else{
    		
    		pager2.setCurrentItem(current+1);
    		
    	}
    }  
	 //*************************************************************************
    public void initData() {
		// TODO Auto-generated method stub
    	
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
						onImageGridClick(position-1);
					}
				});

		}

		// 默认加载失物界面
		queryPhotosIndex();
	}
    
  //*****************************************************************************************
    @Override
	public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
		// TODO Auto-generated method stub
		for (int position : reverseSortedPositions) {
			PhotosIndexAdapter.remove(position);	
			}
	}


	public void onRefresh() {
		// TODO Auto-generated method stub
		queryIndexInfo();
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
				setanima();
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

	//*******************************************************
	public void queryIndexInfo(){
		//查找PicturesShowInformation表里面id为show的数据
		BmobQuery<PicturesShowInformation> query = new BmobQuery<PicturesShowInformation>();
		query.setLimit(15);
		query.addWhereEqualTo("Id", "show");
		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
		query.order("-createdAt");// 按照时间降序
		query.findObjects(getActivity(), new FindListener<PicturesShowInformation>() {
		    @Override
		    public void onSuccess(List<PicturesShowInformation> info2) {
		        // TODO Auto-generated method stub
		    	showpagercount=info2.size();
		    	info3=info2;
		    	imagePagerAdapter2.notifyDataSetChanged();
		        imagePagerAdapter2=new ImagePagerAdapter2(getActivity(),info2);
		    	//更新listViewHeader 的showPager数据
				 pager2.setAdapter(imagePagerAdapter2);
		    	
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	Log.e("queryIndexInfo()", "onError");
		  
		    }
		});
		
		}
	
	 //*****************************************************************************************
	
		private class ImagePagerAdapter2 extends PagerAdapter {
			String title2;
			String url;
			private int imageCount;
			private LayoutInflater inflater;
			List<PicturesShowInformation> info;
			public ImagePagerAdapter2(Context context, List<PicturesShowInformation> info2) {
				this.info=info2;
				inflater = getLayoutInflater(getArguments());
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}

			@Override
			public int getCount() {
				try {
					Count=info.size();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Count=5;
				}
				return Count;
			}

			@Override
			public Object instantiateItem(ViewGroup view, int position) {
				View imageLayout = inflater.inflate(R.layout.photosview_index_list_header_item_pager_image, view, false);
				assert imageLayout != null;
				ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
				TextView title2=(TextView)imageLayout.findViewById(R.id.showpaper_title);
				TextView time=(TextView)imageLayout.findViewById(R.id.showpaper_time);
				int newposition=position+1;
				try {
					title2.setText(info.get(position).getTitle());
					time.setText(info.get(position).getCreatedAt());
					url = info.get(position).getimageUrl();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("test", "获取info中的信息失败");
				}
				
				final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
				imageLoader.displayImage(url, imageView, options, new SimpleImageLoadingListener() {
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
//****************************************************************
		/** 添加HeadView(广告控件) **/
		private void addHeadView(LayoutInflater inflater ) {
			FrameLayout pagerLayout = new FrameLayout(getActivity());
	       // resideMenu.addIgnoredView(pagerLayout);

			pager2 =new MyViewPager(getActivity(), null);
			//queryIndexInfo();	
			imagePagerAdapter2=new ImagePagerAdapter2(getActivity(),info3);
			 //imagePagerAdapter=new ImagePagerAdapter(imageCount);
			pager2.setAdapter(imagePagerAdapter2);
			//获取屏幕像素相关信息
	        DisplayMetrics dm = new DisplayMetrics();
	        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
	        //根据屏幕信息设置Pager2广告容器的宽高	
			pager2.setLayoutParams(new LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));
			pagerLayout.addView(pager2);
			CirclePageIndicator circlePageIndicator = new CirclePageIndicator(getActivity());
			circlePageIndicator.setViewPager(pager2);
			circlePageIndicator.setPadding(20,dm.heightPixels * 2 / 5-50, 20, 50);
			pagerLayout.addView(circlePageIndicator);
			ImagePagerAdapter2 mAdapter = new ImagePagerAdapter2(getActivity(),info3);
			pager2.setAdapter(mAdapter);
			pager2.setOnSimpleClickListener(this);
			
			pager2.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					pager2.getGestureDetector().onTouchEvent(event);
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
			
			listview.addHeaderView(pagerLayout);
			queryIndexInfo();
		}
		
		
		


		@Override
  public void setOnSimpleClickListenr(int position) {
			// TODO Auto-generated method stub
		
			try {
				PicturesShowInformation pd=info3.get(position);
				videoUrl=pd.getvideoUrl();
				tag=pd.gettag();
				imageCount=pd.getimageCount();
				englishTitle=pd.getenglishTitle();
				title=pd.getTitle();
				htmlUrl=pd.gethtmlUrl();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				tag="";
				videoUrl="";
			}
			//Intent intent = new Intent(getActivity(),WebViewActivity.class);
	  		Intent intent = new Intent();
	  		intent.putExtra(BmobConstants.ImageCount,imageCount);
	  		intent.putExtra(BmobConstants.englishTitle, englishTitle);
	  		intent.putExtra(BmobConstants.PhotosTitle, title);
			intent.putExtra(BmobConstants.htmlUrl, htmlUrl);
			intent.putExtra(BmobConstants.videoUrl, videoUrl);
			if(tag.equals("move")){
				intent.setClass(getActivity(),VideoViewBuffer.class);
				startActivity(intent);
			}
			if(tag.equals("html")){
	  		//intent.putExtra(Extra.IMAGES, imageUrls);
	  		//intent.putExtra(Extra.IMAGE_POSITION, i);
		    intent.setClass(getActivity(),WebViewActivity.class);
	  		startActivity(intent);
	  		getActivity().overridePendingTransition (R.anim.open_next, R.anim.close_main);
	
			}
			if(tag.equals("")){
		  		Toast.makeText(getActivity(), "哎呀！出错了", Toast.LENGTH_SHORT).show();
		
				}
		}

    
    
}
