package com.bmob.im.demo.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.CommonUtils;
import com.bmob.im.demo.view.dialog.DialogTips;
import com.liulei1947.photos.ResideMenu.MenuActivity;
import com.liulei1947.photos.ResideMenu.R;

/**
 * @ClassName: LoginActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-3 ����4:41:42
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	EditText et_username, et_password;
	Button btn_login;
	TextView btn_register;
	BmobChatUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		//showNotice();
	}

//	public void showNotice() {
//		DialogTips dialog = new DialogTips(this,"��ʾ",getResources().getString(R.string.show_notice), "ȷ��",true,true);
//		// ���óɹ��¼�
//		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialogInterface, int userId) {
//				
//			}
//		});
//		// ��ʾȷ�϶Ի���
//		dialog.show();
//		dialog = null;
//	}
	
	private void init() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (TextView) findViewById(R.id.btn_register);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_register) {
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			overridePendingTransition (R.anim.open_next, R.anim.close_main);
		} else {
			boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
			if(!isNetConnected){
				ShowToast(R.string.network_tips);
				return;
			}
			login();
		}
	}
	
	private void login(){
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();

		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}

		final ProgressDialog progress = new ProgressDialog(
				LoginActivity.this);
		progress.setMessage("���ڵ�½...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		userManager.login(name, password, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progress.setMessage("���ڻ�ȡ�����б�...");
					}
				});
				//�����û��ĵ���λ���Լ����ѵ�����
				updateUserInfos();
				
				progress.dismiss();
				Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
				startActivity(intent);
				overridePendingTransition (R.anim.open_next, R.anim.close_main);
				finish();
			}

			@Override
			public void onFailure(int errorcode, String arg0) {
				// TODO Auto-generated method stub
				progress.dismiss();
				BmobLog.i(arg0);
				ShowToast(arg0);
			}
		});
	}
	
	private static long firstTime;
	/**
	 * ���������η��ؼ����˳�
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			ShowToast("�ٰ�һ���˳�����");
		}
		firstTime = System.currentTimeMillis();
	}
	
	
}
