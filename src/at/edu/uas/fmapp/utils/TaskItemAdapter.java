package at.edu.uas.fmapp.utils;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import at.edu.uas.fmapp.DetailObjectTask;
import at.edu.uas.fmapp.R;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;

public class TaskItemAdapter extends ArrayAdapter<TaskContainer> {

	private Context context;
	private int layoutResourceId;
	private TaskContainer data[] = null;
	private FmApp appState;

	public TaskItemAdapter(Context context, int textViewResourceId,
			TaskContainer[] objects, FmApp appState) {
		super(context, textViewResourceId, objects);
		this.layoutResourceId = textViewResourceId;
		this.context = context;
		this.data = objects;
		this.appState = appState;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WorkObjectHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new WorkObjectHolder();
			holder.name = (TextView) row.findViewById(R.id.taskName);
			holder.switcher = (Switch) row.findViewById(R.id.taskSwitcher);

			row.setTag(holder);
		} else {
			holder = (WorkObjectHolder) row.getTag();
		}

		final TaskContainer taskContainer = data[position];
		holder.name.setText(taskContainer.getTask().getName());
		holder.name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				appState.setCurrentTask(taskContainer);
				context.startActivity(new Intent(getContext(),
						DetailObjectTask.class));
			}
		});

		holder.switcher.setOnCheckedChangeListener(null);
		holder.switcher.setChecked(FmApp.isTaskChecked(taskContainer));
		holder.switcher
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						Toast.makeText(
								getContext(),
								"The Task '"
										+ taskContainer.getTask().getName()
										+ "' is " + (isChecked ? "OK" : "DO"),
								Toast.LENGTH_SHORT).show();
						WorkItem workItem = taskContainer.getWorkItem();
						if (isChecked) {
							if (workItem == null) {
								workItem = new WorkItem();
								workItem.setDate(new Date());
								workItem.setTaskAssignmentId(taskContainer
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

		return row;
	}

	private FmServiceExecutionListener<WorkItem> executionListener = new FmServiceExecutionListener<WorkItem>() {
		@Override
		public void onPostExecute(WorkItem result) {
			List<TaskContainer> current = appState.getTaskContainerList();
			for (TaskContainer taskContainer : current) {
				if (taskContainer.getAssignment().equals(
						result.getTaskAssignmentId())) {
					taskContainer.setWorkItem(result);
				}
			}
			appState.setTaskContainerList(current);
		}
	};

	private static class WorkObjectHolder {
		private TextView name;
		private Switch switcher;
	}

}
