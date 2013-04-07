package com.omerjerksticktracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

public class GCMIntentService extends GCMBaseIntentService {
	public static String SENDER_ID ="796902621769";
	
	public GCMIntentService() {
        super(SENDER_ID);
    }
	
	@Override
    protected void onRegistered(Context context, String regId) {
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://www.ludlowcastle.co.in/gcm/register.php");
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("regId", regId));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        httpclient.execute(httppost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		GCMRegistrar.setRegisteredOnServer(context, true);
    }
	
	@Override
	protected void onUnregistered(Context context, String regId){
		
	}
	
	@Override
	protected void onMessage(final Context context, Intent intent){
		final String message = intent.getExtras().getString("message");

	    final NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Emergency")
		        .setContentText(message);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		
		
		Handler h = new Handler(Looper.getMainLooper());
	    h.post(new Runnable(){

	         public void run() {
	             // TODO Auto-generated method stub
	         Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	         NotificationManager mNotificationManager =
	        		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	        		int mId=1;
	        		// mId allows you to update the notification later on.
	        		mNotificationManager.notify(mId, mBuilder.build());
	        		
	         }         
	     });
		
	    try {
	        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
	        r.play();
	    } catch (Exception e) {
	    	Toast.makeText(context, "Shit happened", Toast.LENGTH_LONG).show();
	    }
	}
	
	@Override
	protected void onError(Context context, String errorId){
		
	}
	
}