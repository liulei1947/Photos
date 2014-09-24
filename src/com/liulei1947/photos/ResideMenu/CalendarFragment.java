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
    private QuickAdapter<PhotosIndex> PhotosIndexAdapter;// ʧ��
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
                YZYAppWall mYZYAppWall = new YZYAppWall(getActivity());// ���� Activity.this Ϊ�豸�����ģ����ɴ��� Context ����

    			mYZYAppWall.showAppWall(false);//����Ϊ true ʱ�����ӡ��������־��Ϣ���� �鷢����ʽ�汾ʱ����������Ϊ false��  

                //resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

    
    //*************************************************************************
   
	
    
    
    
    
    
}
