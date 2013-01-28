package at.edu.uas.fmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ObjectDetailActivity extends LoggedInBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_detail);
		
		TextView textObjectName = (TextView) findViewById(R.id.text_object_name);
		textObjectName.setText(appState.getSelectedItem().getDescription());

	}
	
	public void loadRoute(View v) {
		startActivity(new Intent(this, ObjectRouteActivity.class));
	}

	public void loadInformation(View v) {
		startActivity(new Intent(this, ObjectInformationActivity.class));
	}

	public void loadTasks(View v) {
		startActivity(new Intent(this, ObjectTasksActivity.class));
	}
	
}
