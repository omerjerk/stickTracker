package com.omerjerksticktracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	
	EditText uname, pword;
	String username, password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		uname = (EditText) findViewById(R.id.email);
		pword = (EditText) findViewById(R.id.password);

	}
	
	public void login(View v){
		username = uname.getText().toString();
		password = pword.getText().toString();
		
		if ( username.equals("") || password.equals("") ){
			
			if( username.equals("")){
				uname.setError("Please enter username");
			}
			
			if( password.equals("")){
				pword.setError("Please enter password");
			}
		} else if( !(username.equals("netra")) || !(password.equals("netra")) ) {
			
			if( !username.equals("netra") ){
				uname.setError("Incorrect Username");
			}
			
			if ( !password.equals("netra") ){
				pword.setError("Incorrect Password");
			}			
		} else {
			Intent mapActivity = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(mapActivity);
		}
	}

}
