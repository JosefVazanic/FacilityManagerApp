package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import at.edu.uas.fmapp.utils.FmApp;

public class LoggedInBaseActivity extends Activity {

	protected FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appState = (FmApp) getApplicationContext();

		if (appState.getLoggedInPerson() == null) {
			// if there is no logged in person we have an erroneous state
			// and should not proceed.
			appState.logout();
			finish();
			return;
		}

		// filter for logout action
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(FmApp.LOGOUT_ACTION);
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("onReceive", "Logout in progress");
				startActivity(new Intent(getBaseContext(),
						WelcomeActivity.class));
				Toast.makeText(getBaseContext(),
						getString(R.string.activity_welcome_logout_message),
						Toast.LENGTH_LONG).show();
				finish();
			}
		}, intentFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_home:
			startActivity(new Intent(this, HomeActivity.class));
			return true;
		case R.id.menu_item_logout:
			appState.logout();
			return true;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
