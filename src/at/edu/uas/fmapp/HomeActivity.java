package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import at.edu.uas.fmapp.utils.FmApp;

public class HomeActivity extends Activity {

	FmApp appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		appState = (FmApp) getApplicationContext();
		TextView loginName = (TextView) findViewById(R.id.loginName);
		loginName.setText(appState.getLoggedInPerson());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
	
	public void logout(View v) {
		startActivity(new Intent(this, WelcomeActivity.class));
		appState.logout();
	}
	
	public void loadObjectSearch(View v) {
		startActivity(new Intent(this, SearchObjects.class));
	}

}
