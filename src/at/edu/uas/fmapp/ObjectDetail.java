package at.edu.uas.fmapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import at.edu.uas.fmapp.utils.FmApp;

public class ObjectDetail extends Activity {

	private FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_detail);

		appState = (FmApp) getApplicationContext();
		Button loginName = (Button) findViewById(R.id.loginName);
		loginName.setText(appState.getLoggedInPerson().toString());

		setTitle(appState.getSelectedItem().getName() + " - "
				+ appState.getSelectedItem().getAddress());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_object_detail, menu);
		return true;
	}

	public void logout(View v) {
		startActivity(new Intent(this, WelcomeActivity.class));
		appState.logout();
	}

	public void loadHome(View v) {
		startActivity(new Intent(this, HomeActivity.class));
	}

	public void loadResult(View v) {
		startActivity(new Intent(this, ResultObjects.class));
	}
	
	public void loadWay(View v) {
		startActivity(new Intent(this, ResultObjectsWay.class));
	}

	public void loadInformation(View v) {
		startActivity(new Intent(this, ResultObjectsInformation.class));
	}

	public void loadTasks(View v) {
		startActivity(new Intent(this, ResultObjectsTasks.class));
	}
	
}
