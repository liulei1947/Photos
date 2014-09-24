package com.liulei1947.photos.ResideMenu;
import com.liulei1947.photos.FeedBack.ActSendFeedback;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * User: special
 * Date: 13-12-22

 * Mail: specialcyci@gmail.com
 */
public class AboutFragment extends Activity {
	 
    @Override
    public void onCreate(Bundle savedInstanceState) { 
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
        setTitle("关于");
        try {
			setUpViews();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
   
    //******************************************************
    private void setUpViews() throws NameNotFoundException {
 
        TextView Version = (TextView)findViewById(R.id.Version);
        Version.setText("版本  "+getAppVersionName(this));//显示版本号
        findViewById(R.id.sendFeedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActSendFeedback = new Intent(AboutFragment.this, ActSendFeedback.class);
                startActivity(intentActSendFeedback);
                overridePendingTransition (R.anim.open_next, R.anim.close_main);
            }
        });
       
    }

    //****获取应用版本
    public String getAppVersionName(Context context) throws NameNotFoundException{
    	String versionName="";
    	PackageManager packageManager=context.getPackageManager();
    	PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
    	versionName=packageInfo.versionName;
		return versionName;
     }
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}
	 
    
    
}
