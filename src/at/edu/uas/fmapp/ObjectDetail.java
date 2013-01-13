package at.edu.uas.fmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ObjectDetail extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_detail);

		initLogin();

		setTitle(appState.getSelectedItem().getName() + " - "
				+ appState.getSelectedItem().getAddress());

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
