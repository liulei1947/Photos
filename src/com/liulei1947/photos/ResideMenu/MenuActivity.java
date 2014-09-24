package com.liulei1947.photos.ResideMenu;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import cn.bmob.im.BmobChat;

import com.bmob.im.demo.config.Config;
import com.bmob.im.demo.ui.ChatMainActivity;
import com.bmob.im.demo.ui.SplashActivity;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.umeng.message.PushAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem aboutApplication;
    private ResideMenuItem itemSettings3;
 //友盟分享成员变量
  UMSocialService mController;
//******************************************************************************************
  
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //设置ActionBar 浮动到view 上层来  
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);    
        //设置ActionBar 背景色 透明  
        getActionBar().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));  
      
        setContentView(R.layout.main);
    	BmobChat.getInstance().init(this, Config.applicationId);
        XiaomiUpdateAgent.update(this);
   //***友盟***
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();
      //友盟分享成员变量
      //APPID:270108
  		//API Key:7c59189437de4764b71510fe2e698444
  		//Secret:cc064954ae6c4523a21429fdf6627796
        mController = UMServiceFactory.getUMSocialService("");
  			RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(this,
  		            "270108", "7c59189437de4764b71510fe2e698444",
  		            "cc064954ae6c4523a21429fdf6627796");
  		mController.getConfig().setSsoHandler(renrenSsoHandler);
       
        setUpMenu();
        changeFragment(new HomeFragment());       
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "主页");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "同窗");
        itemCalendar = new ResideMenuItem(this, R.drawable.chat, "聊天");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "设置");
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
       // resideMenu.addMenuItem(aboutApplication, ResideMenu.DIRECTION_RIGHT);
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

//        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//            }
//        });
//        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemProfile){
           // changeFragment(new PagerFragment());
        	Toast.makeText(this,"更多功能开发中，敬请期待...", Toast.LENGTH_SHORT).show();
        }else if (view == itemCalendar){

        	startActivity(new Intent(this,ChatMainActivity.class));
            //changeFragment(new CalendarFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
          
        }
        else if (view == aboutApplication){
           // changeFragment(new AboutFragment());
        }else if (view == itemSettings3){
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }

//    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
//        @Override
//        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
//        }
//    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu锛� 
    public  ResideMenu getResideMenu(){
        return resideMenu;
    }
    
  //*******************************************************
  	//create Menu
  	@Override
  	public boolean onCreateOptionsMenu(Menu menu) {

  		// Inflate the menu; this adds items to the action bar if it is present.
  		//getMenuInflater().inflate(R.menu.reside_menu, menu);
  		return true;
  	}

  	@Override
  	public boolean onOptionsItemSelected(MenuItem item) {
  		int id = item.getItemId();
  		if (id == R.id.ic_action_share) {
  			mController.setShareContent("[班级相册收藏那年美丽的你] 快来班级相册找我吧！，http://liulei.bmob.cn");
  			// 设置分享图片, 参数2为图片的url地址
  			mController.setShareMedia(new UMImage(this, 
  			                                      "http://photos1204.qiniudn.com/login_logo.png"));
  			//为了保证人人分享成功且能够在PC上正常显示，请设置website                                      
  			mController.setAppWebSite(SHARE_MEDIA.RENREN, "http://liulei.bmob.cn");
  			mController.openShare(this, false);
  			
  			return true;
  		}
      if (id == R.id.photos_social_group) {
    	  Intent intent = new Intent(this,ChatMainActivity.class);
    	  startActivity(intent);
    	  overridePendingTransition (R.anim.open_next, R.anim.close_main);
  		  return true;
  		}
  		return super.onOptionsItemSelected(item);
  	}
  	
    //******************************************************
  
	/**
	 * 连续按两次返回键就退出
	 */

	private static long firstTime;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
		}
		firstTime = System.currentTimeMillis();
	}
    
    
    
    
    
    
    
   
}
