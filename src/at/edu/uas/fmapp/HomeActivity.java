package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.entity.Worker;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;

public class HomeActivity extends LoggedInBaseActivity {

	private TextView homeMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();
	}

	private void initLayout() {
		setContentView(R.layout.activity_home);
		homeMessage = (TextView) findViewById(R.id.home_message);
	}

	private void init() {
		// set home message
		Worker worker = appState.getLoggedInPerson();
		homeMessage.setText(getString(R.string.activity_home_message,
				worker.getFirstName(), worker.getLastName()));

		// load objects
		loadAllObjects();
	}

	private void loadAllObjects() {
		FmServiceExecutionListener<List<WorkObject>> executionListener = new FmServiceExecutionListener<List<WorkObject>>() {

			@Override
			public void onPostExecute(List<WorkObject> result) {
				appState.setAllWorkObjects(result);
			}
		};

		appState.getProxy().getWorkObjectList(
				appState.getLoggedInPerson().getId(), executionListener);
		
		//TODO test code. remove it.
		FmServiceExecutionListener<List<WorkItem>> executionListener2 = new FmServiceExecutionListener<List<WorkItem>>() {

			@Override
			public void onPostExecute(List<WorkItem> result) {
			
				WorkItem workItemToUpdate = result.get(0);
				workItemToUpdate.setStatus("Done");
				
				FmServiceExecutionListener<WorkItem> executionListener2 = new FmServiceExecutionListener<WorkItem>() {

					@Override
					public void onPostExecute(WorkItem result) {
						
						WorkItem workItemToInsert = new WorkItem();
						workItemToInsert.setStatus("Done");
						workItemToInsert.setTaskAssignmentId(3L);
						workItemToInsert.setDate(new Date(System.currentTimeMillis()));
						
						FmServiceExecutionListener<WorkItem> executionListener3 = new FmServiceExecutionListener<WorkItem>() {

							@Override
							public void onPostExecute(WorkItem result) {
								
								System.out.println("");
							}
						};

						HomeActivity.this.appState.getProxy().insertOrUpdateWorkItem(workItemToInsert, executionListener3);
						
					}
				};
			
				HomeActivity.this.appState.getProxy().insertOrUpdateWorkItem(workItemToUpdate, executionListener2);
				
			}
		};

		List<TaskAssignment> taList = new ArrayList<TaskAssignment>();
		taList.add(new TaskAssignment(1L));
		taList.add(new TaskAssignment(2L));
	
		appState.getProxy().getWorkItemListForAssignments(taList, executionListener2);
		
	
		
	}

	public void onAllObjectsClick(View v) {
		ArrayList<WorkObject> result = new ArrayList<WorkObject>(
				appState.getAllWorkObjects());
		appState.setWorkObjectSearchResult(result);
		startActivity(new Intent(this, SearchObjectResultActivity.class));
	}

	public void onFindObjectClick(View v) {
		appState.setWorkObjectSearchResult(new ArrayList<WorkObject>());
		startActivity(new Intent(this, SearchObjectActivity.class));
	}
}