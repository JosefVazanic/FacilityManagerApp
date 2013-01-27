package at.edu.uas.fmapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NewObjectTask extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_object_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_object_task, menu);
		return true;
	}

}
