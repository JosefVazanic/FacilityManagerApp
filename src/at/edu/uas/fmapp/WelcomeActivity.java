package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import at.edu.uas.fmapp.entity.Worker;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;
import at.edu.uas.fmapp.utils.FmApp;

public class WelcomeActivity extends Activity {

	private EditText m_username;
	private EditText m_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();
	}

	private void initLayout() {
		setContentView(R.layout.activity_welcome);
		m_username = (EditText) findViewById(R.id.text_username);
		m_password = (EditText) findViewById(R.id.text_password);
	}

	private void init() {
		// TODO remove - its used for fast login during development
		m_username.setText("TestWorker1");
		m_password.setText("worker123");
	}

	public void login(View v) {
		FmServiceExecutionListener<Worker> executionListener = new FmServiceExecutionListener<Worker>() {

			@Override
			public void onPostExecute(Worker result) {
				if (result != null) {
					getAppState().setLoggedInPerson(result);
					startActivity(new Intent(WelcomeActivity.this,
							HomeActivity.class));
				} else {
					Toast.makeText(getBaseContext(),
							R.string.activity_welcome_login_failed_message,
							Toast.LENGTH_LONG).show();
				}

			}
		};

		getAppState().getProxy().authenticateWorker(
				m_username.getText().toString(),
				m_password.getText().toString(), executionListener);
	}

	private FmApp getAppState() {
		return (FmApp) getApplicationContext();
	}
}
