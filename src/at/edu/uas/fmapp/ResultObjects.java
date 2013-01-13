package at.edu.uas.fmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.edu.uas.fmapp.classes.WorkObject;
import at.edu.uas.fmapp.utils.WorkObjectAdapter;

public class ResultObjects extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_objects);

		initLogin();

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

	public void loadDetail() {
		startActivity(new Intent(this, ObjectDetail.class));
	}

}
