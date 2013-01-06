package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import at.edu.uas.fmapp.utils.FmApp;

public class WelcomeActivity extends Activity {

	FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		appState = (FmApp) getApplicationContext();

		// dummy login data
		String[] array = new String[] { "Hans Putzer", "Dieter Rein",
				"Sigmunde Hauswart", "Barbara Sauber", "Bruce Schmutzkiller" };

		// login spinner
		Spinner loginspinner = (Spinner) findViewById(R.id.loginSpinner);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array);
		adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		loginspinner.setAdapter(adapter2);
		loginspinner.setSelection(Integer.parseInt("0"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_welcome, menu);
		return true;
	}

	public void loadHomeLayout(View v) {
		startActivity(new Intent(this, HomeActivity.class));
		
		Spinner loginspinner = (Spinner) findViewById(R.id.loginSpinner);
		appState.setLoggedInPerson((String) loginspinner.getSelectedItem());
	}

	public void loadMainLayout(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

}
