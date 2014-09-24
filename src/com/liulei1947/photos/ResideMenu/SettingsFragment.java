package com.liulei1947.photos.ResideMenu;


import com.liulei1947.photos.FeedBack.ActSendFeedback;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * User: special
 * Date: 13-12-22
 * Time: 涓嬪崍3:28
 * Mail: specialcyci@gmail.com
 */
public class SettingsFragment extends Fragment {
	 private View parentView;
	  UMSocialService mController;
	 ListView listview;
	 Myadapter madapter;
	 String[] setting=new String[]{
		"检查更新",
		"问题反馈",
		"官方网站",
		"清除缓存",
		"分享",
		"关于",
		"退出",
	 };
	 int[] setting_images=new int[]{
				R.drawable.setting_navigation_refresh,
				R.drawable.setting_feedback_chat,
				R.drawable.setting_web_site,
				R.drawable.setting_clear,
				R.drawable.setting_share,
				R.drawable.setting_action_about,
				R.drawable.setting_getout,
			 };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	getActivity().setTitle("用户设置");
    	parentView = inflater.inflate(R.layout.settings, container, false);
    	
    	initView();
    	return parentView;
    }
	private void initView() {
		// TODO Auto-generated method stub
		listview = (ListView) parentView.findViewById(R.id.setting_listView);
	    madapter=new Myadapter();;
		listview.setAdapter(madapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
	            switch(position+1){
	            	case 1:
	            		XiaomiUpdateAgent.update(getActivity());
	            	break;
	            	case 2:
	            		Intent intentActSendFeedback = new Intent(getActivity(), ActSendFeedback.class);
	                    startActivity(intentActSendFeedback);
	                    getActivity().overridePendingTransition (R.anim.open_next, R.anim.close_main);
		            	break;
	            	case 3:
	            		Intent intent=new Intent();
	            		intent.setData(Uri.parse("http://timebox.bmob.cn"));
	            		intent.setAction(Intent.ACTION_VIEW);
	            		getActivity().startActivity(intent);
		            	break;
	            	case 4:
	            		Toast.makeText(getActivity(),"清除缓存成功",Toast.LENGTH_SHORT).show();
		            	break;
	            	case 5:
	            		mController = UMServiceFactory.getUMSocialService("");
	        			RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(getActivity(),
	        		            "270108", "7c59189437de4764b71510fe2e698444",
	        		            "cc064954ae6c4523a21429fdf6627796");
	        		mController.getConfig().setSsoHandler(renrenSsoHandler);
	            		mController.setShareContent("[快看做高品质的视频分享社区] 快看精彩不同！，http://timebox.bmob.cn");
	          			// 设置分享图片, 参数2为图片的url地址
	          			mController.setShareMedia(new UMImage(getActivity(), 
	          			                                      "http://timebox.qiniudn.com/Icon.png"));
	          			//为了保证人人分享成功且能够在PC上正常显示，请设置website                                      
	          			mController.setAppWebSite(SHARE_MEDIA.RENREN, "http://timebox.bmob.cn");
	          			mController.openShare(getActivity(), false);
	          		//直接分享
	          			mController.directShare(getActivity(), SHARE_MEDIA.SINA,
	          			            new SnsPostListener() {

	          			            @Override
	          			            public void onStart() {
	          			                //Toast.makeText(getActivity(), "分享开始",Toast.LENGTH_SHORT).show();
	          			            }

	          			            @Override
	          			            public void onComplete(SHARE_MEDIA platform,int eCode, SocializeEntity entity) {
	          			                if(eCode == StatusCode.ST_CODE_SUCCESSED){
	          			                    Toast.makeText(getActivity(), "分享成功",Toast.LENGTH_SHORT).show();
	          			                }else{
	          			                    Toast.makeText(getActivity(), "分享失败",Toast.LENGTH_SHORT).show();
	          			                }
	          			            }

	        						
	          			    });
		            	break;
	            	case 6:

	            		Intent intentAbout = new Intent(getActivity(), AboutFragment.class);
	                    startActivity(intentAbout);
	                    getActivity().overridePendingTransition (R.anim.open_next, R.anim.close_main);
		            	break;
	            	case 7:

	            		getActivity().finish();
	            		//System.exit(0);
		            	break;
		            				
	            }
	            
	            }
	        });
	}
	public  void changeFragment(Fragment targetFragment){
	        //resideMenu.clearIgnoredViewList();
	        getActivity().getSupportFragmentManager()
	                .beginTransaction()
	                .replace(R.id.main_fragment, targetFragment, "fragment")
	                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
	                .commit();
	    }
	
	//******Listview Adpter Start
class Myadapter extends BaseAdapter{
	LayoutInflater inflater;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return setting.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=getLayoutInflater(getArguments()).inflate(R.layout.setting_item, parent, false);
		ImageView setting_imageView1= (ImageView) convertView.findViewById(R.id.setting_imageView1);
		TextView setting_text = (TextView) convertView.findViewById(R.id.setting_text);
		setting_text.setText(setting[position]);
		setting_imageView1.setImageResource(setting_images[position]);
		return convertView;
	}
	
}

//******Listview Adpter end









}
