package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;
import at.edu.uas.fmapp.utils.WorkObjectAdapter;

public class SearchObjectResultActivity extends LoggedInBaseActivity {

	private ListView objectListView;
	private TextView emptyListTextView;
	private View listHeaderView;
	private WorkObjectAdapter adapter;

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
		adapter = new WorkObjectAdapter(this,
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
		appState.getProxy().getTaskList(executionListener);
		appState.getProxy().getTaskAssignmentList(
				appState.getLoggedInPerson().getId(),
				executionListenerTaskAssignment);
		startActivity(new Intent(this, ObjectDetailActivity.class));
	}

	private FmServiceExecutionListener<List<Task>> executionListener = new FmServiceExecutionListener<List<Task>>() {
		@Override
		public void onPostExecute(List<Task> result) {
			appState.setAllTasks(result);
		}
	};

	private FmServiceExecutionListener<List<TaskAssignment>> executionListenerTaskAssignment = new FmServiceExecutionListener<List<TaskAssignment>>() {
		@Override
		public void onPostExecute(List<TaskAssignment> result) {
			// Get all TaskAssignments of the current user
			List<TaskAssignment> currentTaskAssignments = new ArrayList<TaskAssignment>();
			for (TaskAssignment taskAssignment : result) {
				if (taskAssignment.getWorkObjectId().equals(
						appState.getSelectedItem().getId())) {
					currentTaskAssignments.add(taskAssignment);
				}
			}
			appState.setUserTasksAssignments(currentTaskAssignments);
			appState.getProxy().getWorkItemListForAssignments(currentTaskAssignments,
					executionListenerWorkObject);
		}
	};

	private FmServiceExecutionListener<List<WorkItem>> executionListenerWorkObject = new FmServiceExecutionListener<List<WorkItem>>() {
		@Override
		public void onPostExecute(List<WorkItem> result) {
			Map<Long, WorkItem> map = new HashMap<Long, WorkItem>();
			for (WorkItem i : result)
				map.put(i.getTaskAssignmentId(), i);
			appState.setUserTaskAssignmentWorkItems(map);
		}
	};

}