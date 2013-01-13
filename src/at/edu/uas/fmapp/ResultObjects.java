package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import at.edu.uas.fmapp.classes.WorkObject;
import at.edu.uas.fmapp.utils.FmApp;
import at.edu.uas.fmapp.utils.WorkObjectAdapter;

public class ResultObjects extends Activity {

	private FmApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_objects);

		appState = (FmApp) getApplicationContext();
		Button loginName = (Button) findViewById(R.id.loginName);
		loginName.setText(appState.getLoggedInPerson().toString());

		WorkObjectAdapter adapter = new WorkObjectAdapter(this,
				R.layout.result_view, appState.getResult().toArray(
						new WorkObject[] {}));

		ListView listView = (ListView) findViewById(R.id.listViewResult);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				appState.setSelectedItem(appState.getResult().get(arg2));
				loadDetail();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result_objects, menu);
		return true;
	}

	public void logout(View v) {
		startActivity(new Intent(this, WelcomeActivity.class));
		appState.logout();
	}

	public void loadHome(View v) {
		startActivity(new Intent(this, HomeActivity.class));
	}

	public void loadSearch(View v) {
		startActivity(new Intent(this, SearchObjects.class));
	}

	public void loadDetail() {
		startActivity(new Intent(this, ObjectDetail.class));
	}

}
