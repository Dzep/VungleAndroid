package com.dzcoding.vungleandroid;

import com.dzcoding.vungleandroid.lib.VungleManager;
import com.dzcoding.vungleandroid.lib.VungleManager.VungleManagerEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements VungleManagerEventListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// setup VungleManager
		VungleManager.configure(this, "com.kooapps.testapp");
		VungleManager.setEventListener(this);
		VungleManager.setAutoRotation(true);
		
		final Button playVideoButton = (Button) findViewById(R.id.play_video_button);
		playVideoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isAvailable = VungleManager.isAvailable(true);
				if (isAvailable) {
					VungleManager.play();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		VungleManager.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		VungleManager.resume();
	} 
	
	/// VungleManager listener
	@Override
	public void onVungleManagerAdStart() {
		Toast.makeText(this, "Video started.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onVungleManagerAdEnd() {
		Toast.makeText(this, "Video started.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onVungleManagerView(boolean success) {
		Toast.makeText(this, "Video success = " + success, Toast.LENGTH_SHORT).show();
	}

}
