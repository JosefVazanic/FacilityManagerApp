package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import at.edu.uas.fmapp.classes.Worker;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;

public class WelcomeActivity extends BaseActivity {

//	ArrayAdapter<Worker> m_adapter;
	private List<Worker> m_listWorker;
	
	private EditText m_username;
	private EditText m_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		m_username = (EditText) findViewById(R.id.textUsername);
		m_password = (EditText) findViewById(R.id.textPassword);
		
		// TODO remove - its used for fast login during development
		m_username.setText("TestWorker1");
		m_password.setText("worker123");

		FmServiceExecutionListener<List<Worker>> executionListener = new FmServiceExecutionListener<List<Worker>>() {

			@Override
			public void onPostExecute(List<Worker> result) {
				// set values for login spinner
				m_listWorker = result;
				if (m_listWorker == null) {
					m_listWorker = new ArrayList<Worker>();
				}
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
		FmServiceExecutionListener<Boolean> executionListener = new FmServiceExecutionListener<Boolean>() {

			@Override
			public void onPostExecute(Boolean result) {
				// set values for login spinner
				if (Boolean.TRUE.equals(result)) {
					Worker selectedWorker = null;
					for (Worker worker : m_listWorker) {
						if(m_username.getText().toString().equals(worker.getUserName())) {
							selectedWorker = worker;
						}
					}					
					appState.setLoggedInPerson(selectedWorker);
					startActivity(new Intent(WelcomeActivity.this,
							HomeActivity.class));
				} else {
					Toast.makeText(getBaseContext(), "password invalid",
							Toast.LENGTH_LONG).show();
				}

			}
		};

		appState.getProxy().authenticateWorker(m_username.getText().toString(),
				m_password.getText().toString(), executionListener);
	}

}
