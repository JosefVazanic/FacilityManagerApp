package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import at.edu.uas.fmapp.classes.Worker;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

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

		appState.getProxy().getWorkerList(executionListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_welcome, menu);
		return true;
	}

	public void login(View v) {

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
					Toast.makeText(getBaseContext(), "password invalid",
							Toast.LENGTH_LONG).show();
				}

			}
		};

		EditText textPassword = (EditText) findViewById(R.id.textPassword);

		appState.getProxy().authenticateWorker(selectedWorker,
				textPassword.getText().toString(), executionListener);
	}

}
