package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import at.edu.uas.fmapp.utils.FmApp;

public class BaseActivity extends Activity {

	protected FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appState = (FmApp) getApplicationContext();
		
		// filter for logout action
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.package.ACTION_LOGOUT");
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("onReceive", "Logout in progress");
				startActivity(new Intent(getBaseContext(),
						WelcomeActivity.class));
				Toast.makeText(getBaseContext(), "logout successfully",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}, intentFilter);
	}

	protected void initLogin() {
		if (appState.getLoggedInPerson() != null) {
			Button loginName = (Button) findViewById(R.id.loginName);
			if (loginName != null) {
				loginName.setText(appState.getLoggedInPerson().toString());
			}
		}
	}

	public void logout(View v) {
		appState.logout();
	}

	public void loadHome(View v) {
		startActivity(new Intent(this, HomeActivity.class));
	}

}
