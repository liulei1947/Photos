package com.liulei1947.Push;

import com.liulei1947.photos.FeedBack.ActFeedbackList;
import com.liulei1947.photos.ResideMenu.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class MyMessageReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String message = intent.getStringExtra("msg");
				Log.d("bmob", "�յ������Ϣ = "+message);
				NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				
				Notification n = new Notification();  
		        n.icon = R.drawable.ic_launcher;  
		        n.tickerText = "这是新的推送消息";  
		        n.when = System.currentTimeMillis();  
		        //n.flags=Notification.FLAG_ONGOING_EVENT;  
		        Intent intent2 = new Intent(context, ActFeedbackList.class);  
		        PendingIntent pi = PendingIntent.getActivity(context, 0, intent2, 0);  
		        n.setLatestEventInfo(context, "消息", message, pi);  
		        n.defaults |= Notification.DEFAULT_SOUND;
		        n.flags = Notification.FLAG_AUTO_CANCEL;
		        nm.notify(1, n);
	}

	
	

}
