package com.omerjerksticktracker;

import android.app.Activity;
import android.content.Intent;

public class utils extends Activity {
	public void sendMail(){
		/* Create the Intent */
    	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

    	/* Fill it with Data */
    	emailIntent.setType("plain/text");
    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
    	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

    	/* Send it off to the Activity-Chooser */
    	getApplicationContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}
}