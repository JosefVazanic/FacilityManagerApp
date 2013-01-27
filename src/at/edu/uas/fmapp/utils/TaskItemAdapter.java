package at.edu.uas.fmapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import at.edu.uas.fmapp.R;

public class TaskItemAdapter extends ArrayAdapter<TaskContainer> {

	private Context context;
	private int layoutResourceId;
	private TaskContainer data[] = null;

	public TaskItemAdapter(Context context, int textViewResourceId,
			TaskContainer[] objects) {
		super(context, textViewResourceId, objects);
		this.layoutResourceId = textViewResourceId;
		this.context = context;
		this.data = objects;
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

		TaskContainer taskContainer = data[position];
		holder.name.setText(taskContainer.getTask().getName());

		return row;
	}

	private static class WorkObjectHolder {
		private TextView name;
		private Switch switcher;
	}

}
