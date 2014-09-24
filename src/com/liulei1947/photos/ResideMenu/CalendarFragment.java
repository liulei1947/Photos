package com.liulei1947.photos.ResideMenu;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.ui.SplashActivity;
import com.iwapp.sd.YZYAppWall;
import com.liulei1947.photos.adapter.BaseAdapterHelper;
import com.liulei1947.photos.adapter.QuickAdapter;
import com.liulei1947.photos.bean.PhotosIndex;
import com.special.ResideMenu.ResideMenu;

public class CalendarFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private QuickAdapter<PhotosIndex> PhotosIndexAdapter;// 失物
    private ListView listview;
    ListView testlistview;
   	//**************************************************************************
   	
   	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
       // setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        
        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  
            	startActivity(new Intent(getActivity(),SplashActivity.class));
                //resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        parentView.findViewById(R.id.yyyxwall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YZYAppWall mYZYAppWall = new YZYAppWall(getActivity());// 参数 Activity.this 为设备上下文，不可传入 Context 类型

    			mYZYAppWall.showAppWall(false);//参数为 true 时，会打印出部分日志信息，建 议发布正式版本时，参数设置为 false。  

                //resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

    
    //*************************************************************************
   
	
    
    
    
    
    
}
