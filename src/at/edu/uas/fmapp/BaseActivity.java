package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import at.edu.uas.fmapp.utils.FmApp;

public class BaseActivity extends Activity {

	protected FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appState = (FmApp) getApplicationContext();
	}
	
	protected void initLogin() {
		if(appState.getLoggedInPerson() != null) {
			Button loginName = (Button) findViewById(R.id.loginName);
			if(loginName != null) {
				loginName.setText(appState.getLoggedInPerson().toString());
			}
		}
	}

	public void logout(View v) {
		startActivity(new Intent(this, WelcomeActivity.class));
		appState.logout();
	}

	public void loadHome(View v) {
		startActivity(new Intent(this, HomeActivity.class));
	}

}
