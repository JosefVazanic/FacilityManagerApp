package at.edu.uas.fmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ObjectDetailActivity extends LoggedInBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_detail);

		setTitle(appState.getSelectedItem().getDescription() + " - "
				+ appState.getSelectedItem().getAddress());

	}
	
	public void loadWay(View v) {
		startActivity(new Intent(this, ObjectsRouteActivity.class));
	}

	public void loadInformation(View v) {
		startActivity(new Intent(this, ObjectInformationActivity.class));
	}

	public void loadTasks(View v) {
		startActivity(new Intent(this, ObjectTasksActivity.class));
	}
	
}
