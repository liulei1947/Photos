package com.liulei1947.photos.PhotosView;

import java.util.List;

import cn.bmob.im.BmobChat;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.config.Config;
import com.bmob.im.demo.ui.SplashActivity;
import com.liulei1947.photos.ResideMenu.HomeFragment;
import com.liulei1947.photos.ResideMenu.MenuActivity;
import com.liulei1947.photos.ResideMenu.R;
import com.liulei1947.photos.adapter.BaseAdapterHelper;
import com.liulei1947.photos.adapter.QuickAdapter;
import com.liulei1947.photos.bean.PhotosIndex;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.liulei1947.photos.PhotosView.SlidingUpPanelLayout;
import com.liulei1947.photos.PhotosView.SlidingUpPanelLayout.PanelSlideListener;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class slidinguppanel2 extends FragmentActivity implements View.OnClickListener,OnDismissCallback {
    private static final String TAG = "DemoActivity";
    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem aboutApplication;
    private ResideMenuItem itemSettings3;
    private QuickAdapter<PhotosIndex> PhotosIndexAdapter;// 失物
    private ListView listview;
    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";
    TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    private static final String STATE_POSITION = "STATE_POSITION";
	int pagerPosition;
	int imageCount=5;
	String title;
	String englishTitle="junxun%20";
    String imageUrls;
	DisplayImageOptions options;
	 SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	protected static ImageLoader imageLoader = ImageLoader.getInstance();
	Gallery gallery;
	ViewPager pager;
	ViewPager pager2;
    private SlidingUpPanelLayout mLayout;
    final Handler handler=new Handler(){  
        public void handleMessage(android.os.Message msg) {  
        	if(msg.what==0x123){
        		
        		controlViewPagerShow();
        		
        	}
        	
        	
        };  
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //设置ActionBar 浮动到view 上层来  
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);    
        //设置ActionBar 背景色 透明  
        getActionBar().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));  
      
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        BmobChat.getInstance().init(this, Config.applicationId);
       
        setContentView(R.layout.android_sliding_up_panel2);
        // add gesture operation's ignored views
        options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.resetViewBeforeLoading(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
    imageUrls="http://photos1204.qiniudn.com/"+englishTitle+"(";
	pager = (HackyViewPager) findViewById(R.id.pager);
	pager.setAdapter(new ImagePagerAdapter(imageCount));
	pager.setCurrentItem(pagerPosition);
	//***********************************************************
	/**  
     * 创建消息处理机制  
     */  
     





	
	
	
//****************************************	
	//Gallery gallery = new Gallery(this);
	//gallery.setAdapter(new ImageGalleryAdapter());
//	gallery.setOnItemClickListener(new OnItemClickListener() {
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			//startImagePagerActivity(position);
//		}
//	});

       // mPager = (ViewPager)findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
       // mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
       // pager.setAdapter(mAdapter);
        mIndicator.setViewPager(pager);
        setUpMenu();
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
       
        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                setActionBarTranslation(mLayout.getCurrentParalaxOffset());
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });
        listview = (ListView) findViewById(R.id.upsidelist);
        initData();
        
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
  
        
//        Button f = (Button) findViewById(R.id.follow);
//        f.setText(Html.fromHtml(getString(R.string.follow)));
//        f.setMovementMethod(LinkMovementMethod.getInstance());
//        f.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse("http://liulei.bmob.cn/"));
//                startActivity(i);
//            }
//        });


        boolean actionBarHidden = savedInstanceState != null && savedInstanceState.getBoolean(SAVED_STATE_ACTION_BAR_HIDDEN, false);
        if (actionBarHidden) {
            int actionBarHeight = getActionBarHeight();
            setActionBarTranslation(-actionBarHeight);//will "hide" an ActionBar
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_STATE_ACTION_BAR_HIDDEN, mLayout.isPanelExpanded());
    }

   

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    private int getActionBarHeight(){
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public void setActionBarTranslation(float y) {
        // Figure out the actionbar height
        int actionBarHeight = getActionBarHeight();
        // A hack to add the translation to the action bar
        ViewGroup content = ((ViewGroup) findViewById(android.R.id.content).getParent());
        int children = content.getChildCount();
        for (int i = 0; i < children; i++) {
            View child = content.getChildAt(i);
            if (child.getId() != android.R.id.content) {
                if (y <= -actionBarHeight) {
                    child.setVisibility(View.GONE);
                } else {
                    child.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        child.setTranslationY(y);
                    } else {
                        AnimatorProxy.wrap(child).setTranslationY(y);
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null && mLayout.isPanelExpanded() || mLayout.isPanelAnchored()) {
            mLayout.collapsePanel();
        } else {
            super.onBackPressed();
        }
    }
    
    //******************************************************************

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //FrameLayout ignored_view = (FrameLayout)findViewById(R.id.ignored_view);
        LinearLayout ignored_view = (LinearLayout)findViewById(R.id.test_view);
        resideMenu.addIgnoredView(ignored_view);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "Profile");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");
        aboutApplication = new ResideMenuItem(this, R.drawable.photos_about, "关于");
        itemSettings3 = new ResideMenuItem(this, R.drawable.icon_settings, "Settings2");
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        aboutApplication.setOnClickListener(this);
        itemSettings3.setOnClickListener(this);
        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(aboutApplication, ResideMenu.DIRECTION_RIGHT);
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

      
    }

    
    /**  
     * 控制ViewPager显示  
    */  
    public void controlViewPagerShow(){
    	//int curr=gallery.getSelectedItemPosition();
    	int current = pager.getCurrentItem();
	    if(current==4){
    	pager.setCurrentItem(0);
    	pager2.setCurrentItem(0);
    	}else{
    		pager.setCurrentItem(current+1);
    		pager2.setCurrentItem(current+1);
    		
    	}
      
    }  

    
    
    
    
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub


        if (view == itemHome){
        	startActivity(new Intent(this,SplashActivity.class));


        }else if (view == itemProfile){
        	startActivity(new Intent(this,SplashActivity.class));

        }else if (view == itemCalendar){
        	startActivity(new Intent(this,SplashActivity.class));


        }else if (view == itemSettings){
        	startActivity(new Intent(this,SplashActivity.class));

        }
        else if (view == aboutApplication){
        	startActivity(new Intent(this,SplashActivity.class));


        }else if (view == itemSettings3){
        	startActivity(new Intent(this,SplashActivity.class));


        }

        resideMenu.closeMenu();

	}
    
	  @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	        return resideMenu.dispatchTouchEvent(ev);
	    }

  //************************************************************************************
	  //*************************************************************************
	    public void initData() {
			// TODO Auto-generated method stub
	    	  Log.e("test", "initdata");
			if (PhotosIndexAdapter == null) {
				PhotosIndexAdapter = new QuickAdapter<PhotosIndex>(this, R.layout.index_item) {
					@Override
					protected void convert(BaseAdapterHelper helper, PhotosIndex lost) {
						helper
						.setImageUrl(R.id.titleView,lost.getimageUrl(),R.drawable.holdpic,R.drawable.errorpic,400,300)
								.setText(R.id.tv_title, lost.getTitle())
								.setText(R.id.tv_describe, lost.getDescribe())
								.setText(R.id.tv_time, lost.getCreatedAt())
								.setText(R.id.tv_photo, lost.getcomment());
					}
				};
				FrameLayout pagerLayout = new FrameLayout(this);
				int vertical=1;
				//pagerLayout.setOrientation(vertical);
				pager2 =new ViewPager(this);
				pager2.setAdapter(new ImagePagerAdapter(imageCount));
				//获取屏幕像素相关信息
		        DisplayMetrics dm = new DisplayMetrics();
		        getWindowManager().getDefaultDisplay().getMetrics(dm);
		         
		        //根据屏幕信息设置Pager2广告容器的宽高
		         
				pager2.setLayoutParams(new LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));
				pagerLayout.addView(pager2);
				CirclePageIndicator circlePageIndicator = new CirclePageIndicator(this);
				
				circlePageIndicator.setViewPager(pager2);
				pagerLayout.addView(circlePageIndicator);
				
				
				 //gallery = new Gallery(this);
				 //gallery.setSpacing(20);
				
				//gallery.setAdapter(new ImageGalleryAdapter());
				listview.addHeaderView(pagerLayout);
				
				listview.setAdapter(PhotosIndexAdapter);
				listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						onImageGridClick(position-1);
					}
				});

				 Log.e("test", "setadapter");

			}

			// 默认加载失物界面
			queryPhotosIndex();
		}
	    
	    
	    //******************************************************************************
	    //**************************************************
	  	public void onImageGridClick(int position) {
	  		PhotosIndex pd=PhotosIndexAdapter.getItem(position);
	  		imageCount=pd.getimageCount();
	  		englishTitle=pd.getenglishTitle();
	  		title=pd.getTitle();
	  		Intent intent = new Intent(this,ImageGridActivity.class);
	  		intent.putExtra(BmobConstants.ImageCount,imageCount);
	  		intent.putExtra(BmobConstants.englishTitle, englishTitle);
	  		intent.putExtra(BmobConstants.PhotosTitle, title);
	  		//intent.putExtra(Extra.IMAGES, imageUrls);
	  		//intent.putExtra(Extra.IMAGE_POSITION, i);
	  		startActivity(intent);
	  		
	  	}
		public void setanima() {
			swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(PhotosIndexAdapter, (OnDismissCallback) this));
			swingBottomInAnimationAdapter.setInitialDelayMillis(300);
			swingBottomInAnimationAdapter.setAbsListView(listview);
			listview.setAdapter(swingBottomInAnimationAdapter);
			
			
		}
	    
	    
	    
	    
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
			query.order("-createdAt");
			query.findObjects(this, new FindListener<PhotosIndex>() {

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
					//listview.setAdapter(PhotosIndexAdapter);
					

				}

				@Override
				public void onError(int code, String arg0) {
					// TODO Auto-generated method stub
					
					listview.setAdapter(PhotosIndexAdapter);

				}
			});
			
		}
//*****************************************************************************************
		
		//****************************************************************
			private class ImagePagerAdapter extends PagerAdapter {

				private int imageCount;
				private LayoutInflater inflater;

				ImagePagerAdapter(int imageCount) {
					this.imageCount = imageCount;
					inflater = getLayoutInflater();
				}

				@Override
				public void destroyItem(ViewGroup container, int position, Object object) {
					container.removeView((View) object);
				}

				@Override
				public int getCount() {
					return imageCount;
				}

				@Override
				public Object instantiateItem(ViewGroup view, int position) {
					View imageLayout = inflater.inflate(R.layout.photosview_index_list_header_item_pager_image, view, false);
					assert imageLayout != null;
					//PhotoView imageView = new PhotoView(view.getContext());
					ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
					final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
					int newposition=position+1;
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
							Toast.makeText(slidinguppanel2.this, message, Toast.LENGTH_SHORT).show();

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
//************************************************************************
			private class ImageGalleryAdapter extends BaseAdapter {
				public int getCount() {
					return 29;
				}

				@Override
				public Object getItem(int position) {
					return position;
				}

				@Override
				public long getItemId(int position) {
					return position;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					ImageView imageView = (ImageView) convertView;
					if (imageView == null) {
						imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_gallery_image, parent, false);
					}
					int newposition=position+1;
					imageLoader.displayImage(imageUrls+newposition+BmobConstants.SuffixJPG, imageView, options);
					return imageView;
				}
			}
			@Override
			public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				for (int position : reverseSortedPositions) {
					PhotosIndexAdapter.remove(position);
					}
			}


    
    
    
    
    
    
}
