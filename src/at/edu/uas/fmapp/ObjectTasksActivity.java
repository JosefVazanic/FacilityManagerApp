package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
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
				R.layout.task_list_item_view, getTasks());
		taskListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		taskListView.addHeaderView(listHeaderView);
		taskListView.setEmptyView(emptyListTextView);
		taskListView.setAdapter(adapter);
	}

	private TaskContainer[] getTasks() {
		List<TaskAssignment> currentTaskAssignments = new ArrayList<TaskAssignment>();
		for (TaskAssignment taskAssignment : appState.getUserTasksAssignments()) {
			if (taskAssignment.getWorkObjectId().equals(
					appState.getSelectedItem().getId())) {
				currentTaskAssignments.add(taskAssignment);
			}
		}

		List<TaskContainer> tasks = new ArrayList<TaskContainer>();
		for (TaskAssignment taskAssignment : currentTaskAssignments) {
			for (Task task : appState.getAllTasks()) {			
				if(taskAssignment.getTaskId().equals(task.getId())) {
					tasks.add(new TaskContainer(task, taskAssignment));
					break;
				}
			}
		}
		return tasks.toArray(new TaskContainer[0]);
	}

	public void loadNewTask() {
		// startActivity(new Intent(this, ObjectDetailActivity.class));
	}

}
