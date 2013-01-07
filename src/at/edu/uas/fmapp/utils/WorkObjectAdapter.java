package at.edu.uas.fmapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.edu.uas.fmapp.R;
import at.edu.uas.fmapp.classes.WorkObject;

public class WorkObjectAdapter extends ArrayAdapter<WorkObject> {

	Context context;
	int layoutResourceId;
	WorkObject data[] = null;

	public WorkObjectAdapter(Context context, int textViewResourceId,
			WorkObject[] objects) {
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
			holder.m_name = (TextView) row.findViewById(R.id.textViewName);
			holder.m_address = (TextView) row.findViewById(R.id.textViewAddress);

			row.setTag(holder);
		} else {
			holder = (WorkObjectHolder) row.getTag();
		}

		WorkObject workObject = data[position];
		holder.m_name.setText(workObject.getName());
		holder.m_address.setText(workObject.getAddress());

		return row;
	}
	
	static class WorkObjectHolder
    {
		TextView m_name, m_address;
    }

}
