package at.edu.uas.fmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.utils.WorkObjectAdapter;

public class SearchObjectResultActivity extends LoggedInBaseActivity {

	private ListView objectListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();
	}

	private void initLayout() {
		setContentView(R.layout.activity_search_object_result);
		objectListView = (ListView) findViewById(R.id.objectResultListView);
	}

	private void init() {
		WorkObjectAdapter adapter = new WorkObjectAdapter(this,
				R.layout.object_list_item_view, appState.getWorkObjectSearchResult()
						.toArray(new WorkObject[] {}));
		objectListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		objectListView.setAdapter(adapter);
		objectListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				loadDetail(appState.getWorkObjectSearchResult().get(position));
			}

		});
	}

	public void loadDetail(WorkObject selectedObject) {
		appState.setSelectedItem(selectedObject);
		startActivity(new Intent(this, ObjectDetailActivity.class));
	}
}