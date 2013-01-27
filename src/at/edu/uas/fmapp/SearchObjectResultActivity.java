package at.edu.uas.fmapp;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;
import at.edu.uas.fmapp.utils.WorkObjectAdapter;

public class SearchObjectResultActivity extends LoggedInBaseActivity {

	private ListView objectListView;
	private TextView emptyListTextView;
	private View listHeaderView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();
	}

	private void initLayout() {
		setContentView(R.layout.activity_search_object_result);
		objectListView = (ListView) findViewById(R.id.objectResultListView);
		emptyListTextView = (TextView) findViewById(R.id.textEmptyObjectResultList);
		listHeaderView = View.inflate(this, R.layout.object_list_header_view,
				null);
	}

	private void init() {
		WorkObjectAdapter adapter = new WorkObjectAdapter(this,
				R.layout.object_list_item_view, appState
						.getWorkObjectSearchResult().toArray(
								new WorkObject[] {}));
		objectListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		objectListView.addHeaderView(listHeaderView);
		objectListView.setEmptyView(emptyListTextView);
		objectListView.setAdapter(adapter);
		objectListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				loadDetail(appState.getWorkObjectSearchResult().get(--position));
			}

		});
	}

	public void loadDetail(WorkObject selectedObject) {
		appState.setSelectedItem(selectedObject);
		FmServiceExecutionListener<List<Task>> executionListener = new FmServiceExecutionListener<List<Task>>() {
			@Override
			public void onPostExecute(List<Task> result) {
				appState.setAllTasks(result);
			}
		};
		appState.getProxy().getTaskList(executionListener);
		FmServiceExecutionListener<List<TaskAssignment>> executionListenerTaskAssignment = new FmServiceExecutionListener<List<TaskAssignment>>() {
			@Override
			public void onPostExecute(List<TaskAssignment> result) {
				appState.setUserTasksAssignments(result);
			}
		};
		appState.getProxy().getTaskAssignmentList(
				appState.getLoggedInPerson().getId(),
				executionListenerTaskAssignment);

		startActivity(new Intent(this, ObjectDetailActivity.class));
	}
}