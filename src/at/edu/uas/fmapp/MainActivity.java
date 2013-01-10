package at.edu.uas.fmapp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;
import at.edu.uas.fmapp.server.FmServiceProxy;

public class MainActivity extends Activity {

	FmServiceProxy proxy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		proxy = new FmServiceProxy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onTestButtonClick(View view) {

		FmServiceExecutionListener<List<String>> executionListener = new FmServiceExecutionListener<List<String>>() {

			@Override
			public void onPostExecute(List<String> result) {
				((TextView) findViewById(R.id.testTextView)).setText(result
						.toString());

			}
		};

		proxy.getTaskList("testUserId", executionListener);
	}
}
