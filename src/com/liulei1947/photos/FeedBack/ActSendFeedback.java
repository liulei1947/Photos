package com.liulei1947.photos.FeedBack;

import com.bmob.im.demo.bean.Feedback;
import com.liulei1947.photos.ResideMenu.R;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActSendFeedback extends Activity  {

	EditText et_content;
	static String msg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendfeedback);

		setTitle("发送反馈");

		et_content = (EditText) findViewById(R.id.et_content);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		String content = et_content.getText().toString();
		if(!TextUtils.isEmpty(content)){
			if(content.equals(msg)){
				Toast.makeText(this, "请勿重复提交反馈", Toast.LENGTH_SHORT).show();
			}else {
				msg = content;
				// 发送反馈信息
				sendMessage(content);
				Toast.makeText(this, "您的反馈信息已发送", Toast.LENGTH_SHORT).show();
			}
		}else {
			Toast.makeText(this, "请填写反馈内容", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 发送反馈信息给开发者
	 * @param message 反馈信息
	 */
	private void sendMessage(String message){
		BmobPushManager bmobPush = new BmobPushManager(this);
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("isDeveloper", true);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);

		saveFeedbackMsg(message);
	}

	/**
	 * 保存反馈信息到服务器
	 * @param msg 反馈信息
	 */
	private void saveFeedbackMsg(String msg){
		Feedback feedback = new Feedback();
		feedback.setContent(msg);
		feedback.save(this, new SaveListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Log.i("bmob", "反馈信息已保存到服务器");
				}

				@Override
				public void onFailure(int code, String arg0) {
					// TODO Auto-generated method stub
					Log.e("bmob", "保存反馈信息失败："+arg0);
				}
			});
	}
	
	//*******************************************************
	//create Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sendfeedback_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// 发送反馈信息	
			onClick(et_content);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
