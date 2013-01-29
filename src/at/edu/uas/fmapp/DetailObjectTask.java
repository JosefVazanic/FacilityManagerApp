package at.edu.uas.fmapp;

import java.util.Date;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;
import at.edu.uas.fmapp.utils.FmApp;
import at.edu.uas.fmapp.utils.TaskContainer;

public class DetailObjectTask extends LoggedInBaseActivity {

	private TextView m_taskname;
	private TextView m_taskdescription;
	private Switch m_taskswitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_object_task);

		m_taskname = (TextView) findViewById(R.id.textViewTaskName);
		m_taskdescription = (TextView) findViewById(R.id.textViewTaskDescription);
		m_taskswitcher = (Switch) findViewById(R.id.taskDetailSwitcher);

		final TaskContainer currentTaskContainer = appState.getCurrentTask();

		setTitle(currentTaskContainer.getTask().getName());

		m_taskname.setText(currentTaskContainer.getTask().getName());
		m_taskdescription.setText(currentTaskContainer.getTask()
				.getDescription());

		m_taskswitcher.setOnCheckedChangeListener(null);
		m_taskswitcher.setChecked(FmApp.isTaskChecked(currentTaskContainer));
		m_taskswitcher
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						Toast.makeText(
								getApplicationContext(),
								"The Task '"
										+ currentTaskContainer.getTask()
												.getName() + "' is "
										+ (isChecked ? "OK" : "DO"),
								Toast.LENGTH_SHORT).show();
						WorkItem workItem = currentTaskContainer.getWorkItem();
						if (isChecked) {
							if (workItem == null) {
								workItem = new WorkItem();
								workItem.setDate(new Date());
								workItem.setTaskAssignmentId(currentTaskContainer
										.getAssignment().getId());
							}
							workItem.setStatus("Done");
						} else {
							workItem.setStatus("ToDo");
						}
						appState.getProxy().insertOrUpdateWorkItem(workItem,
								executionListener);
					}
				});
	}

	private FmServiceExecutionListener<WorkItem> executionListener = new FmServiceExecutionListener<WorkItem>() {
		@Override
		public void onPostExecute(WorkItem result) {
			for (TaskContainer taskContainer : appState.getTaskContainerList()) {
				if (taskContainer.getAssignment().equals(
						result.getTaskAssignmentId())) {
					taskContainer.setWorkItem(result);
				}
			}
			appState.getCurrentTask().setWorkItem(result);
		}
	};

}
