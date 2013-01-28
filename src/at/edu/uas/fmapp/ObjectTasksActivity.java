package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.utils.TaskContainer;
import at.edu.uas.fmapp.utils.TaskItemAdapter;

public class ObjectTasksActivity extends LoggedInBaseActivity {

	private ListView taskListView;
	private TextView emptyListTextView;
	private View listHeaderView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();
	}

	private void initLayout() {
		setContentView(R.layout.activity_object_tasks);
		taskListView = (ListView) findViewById(R.id.taskResultListView);
		emptyListTextView = (TextView) findViewById(R.id.textEmptyTaskList);
		listHeaderView = View.inflate(this, R.layout.task_list_header_view,
				null);
	}

	private void init() {
		TaskItemAdapter adapter = new TaskItemAdapter(this,
				R.layout.task_list_item_view, getTasks(), appState);
		taskListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		taskListView.addHeaderView(listHeaderView);
		taskListView.setEmptyView(emptyListTextView);
		taskListView.setAdapter(adapter);
	}

	private TaskContainer[] getTasks() {
		// Get the right Task for the TaskAssignment
		List<TaskContainer> tasks = new ArrayList<TaskContainer>();
		for (TaskAssignment taskAssignment : appState.getUserTasksAssignments()) {
			for (Task task : appState.getAllTasks()) {
				if (taskAssignment.getTaskId().equals(task.getId())) {
					WorkItem workItem = appState
							.getUserTaskAssignmentWorkItems().get(
									taskAssignment.getId());
					if (workItem == null) {
						tasks.add(new TaskContainer(task, taskAssignment));
					} else {
						tasks.add(new TaskContainer(task, taskAssignment,
								workItem));
					}
					break;
				}
			}
		}
		appState.setTaskContainerList(tasks);
		return appState.getTaskContainerList().toArray(new TaskContainer[0]);
	}

	public void loadNewTask(View v) {
		startActivity(new Intent(this, NewObjectTask.class));
	}

}
