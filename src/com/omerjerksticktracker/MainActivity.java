/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omerjerksticktracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 * <p>
 * Notice how we deal with the possibility that the Google Play services APK is not
 * installed/enabled/updated on a user's device.
 */
public class MainActivity extends FragmentActivity {
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMap();
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, "796902621769");
        } else {
          Log.v("message", "Already registered"); 
        }
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.settings_about:
	            DialogFragment aboutDialog = new About();
	            aboutDialog.show(getSupportFragmentManager(), "missiles");
	            return true;
	        case R.id.settings_track:
	        	track();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
    

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        
    }
    
    private void track(){
    	final LatLng test = new LatLng (28.7486035, 77.1199197);
    	mMap.addMarker(new MarkerOptions().position(test)
    			.title("Stick")
    			.snippet("Stick is being developed in CASRAE :)"));
    	CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(test)      // Sets the center of the map to Mountain View
        .zoom(15)                   // Sets the zoom
        .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        for(;;){
    		new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                	updateLocation();
                }
            }, 5000);
    	}
    	
    }
    
    private void updateLocation(){
    	
    }
    
    public class showNewsFeed extends AsyncTask <String, Void, String> {
		HttpClient httpclient = new DefaultHttpClient();
		
		 HttpPost httppost = new HttpPost("http://www.ludlowcastle.co.in/stick/location.php");
		 String result;
		 protected String doInBackground(String... voids ){
			 try {
		         HttpResponse response = httpclient.execute(httppost);
		         HttpEntity entity = response.getEntity();
		         InputStream is = entity.getContent();
		         result = convertStreamToString(is);
		         System.out.println("String response = " + result);
		     } catch (Exception e){
		         e.printStackTrace();
		     }
		     
		     return result;
		 }
		protected void onProgressUpdate(Void...voids){
			
		}
		
		
		protected void onPostExecute(String r){
			super.onPostExecute(r);

			ProgressBar loading;
			try {
            	JSONObject obj=new JSONObject(r);
            	
               
              } catch (Exception e) {
            	  e.printStackTrace();
              } 
		}
			 
			 
		 
	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append((line + "\n"));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
}
