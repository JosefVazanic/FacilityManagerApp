package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import at.edu.uas.fmapp.classes.Worker;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;
import at.edu.uas.fmapp.utils.FmApp;

public class WelcomeActivity extends Activity {

	FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		appState = (FmApp) getApplicationContext();

		FmServiceExecutionListener<List<Worker>> executionListener = new FmServiceExecutionListener<List<Worker>>() {

			@Override
			public void onPostExecute(List<Worker> result) {
				// set values for login spinner
				if (result == null) {
					result = new ArrayList<Worker>();
				}
				Spinner loginSpinner = (Spinner) findViewById(R.id.loginSpinner);
				ArrayAdapter<Worker> adapter = new ArrayAdapter<Worker>(
						getBaseContext(), android.R.layout.simple_spinner_item,
						result);
				adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				loginSpinner.setAdapter(adapter);
				loginSpinner.setSelection(Integer.parseInt("0"));
			}
		};

		appState.getProxy().getWorkers(executionListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_welcome, menu);
		return true;
	}

	public void loadHomeLayout(View v) {

		Spinner loginspinner = (Spinner) findViewById(R.id.loginSpinner);
		final Worker selectedWorker = (Worker) loginspinner.getSelectedItem();
		FmServiceExecutionListener<Boolean> executionListener = new FmServiceExecutionListener<Boolean>() {

			@Override
			public void onPostExecute(Boolean result) {
				// set values for login spinner
				if (Boolean.TRUE.equals(result)) {
					appState.setLoggedInPerson(selectedWorker);
					startActivity(new Intent(WelcomeActivity.this,
							HomeActivity.class));
				} else {
					// TODO show error
				}

			}
		};

		appState.getProxy().authenticateWorker(selectedWorker, "worker123",
				executionListener);
	}

	public void loadMainLayout(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

}
