package at.edu.uas.fmapp;

import java.util.Arrays;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onTestButtonClick(View view) {
		XMLRPCClient client = new XMLRPCClient(
				"http://wi-gate.technikum-wien.at:60354/");
		// get tasks
		String[] tasks = new String[0];
		String result = "";
		try {
			tasks = (String[]) client.call("getTaskList");
			result = Arrays.asList(tasks).toString();
		} catch (XMLRPCException e) {
			result = "Failed: " + e.getMessage();
		}

		((TextView) findViewById(R.id.testTextView)).setText(result);
	}
}
