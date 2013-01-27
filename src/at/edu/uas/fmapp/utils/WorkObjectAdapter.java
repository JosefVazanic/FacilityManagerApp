package at.edu.uas.fmapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.edu.uas.fmapp.R;
import at.edu.uas.fmapp.entity.Address;
import at.edu.uas.fmapp.entity.WorkObject;

public class WorkObjectAdapter extends ArrayAdapter<WorkObject> {

	private Context context;
	private int layoutResourceId;
	private WorkObject data[] = null;

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
			holder.number = (TextView) row.findViewById(R.id.objectNumber);
			holder.name = (TextView) row.findViewById(R.id.objectName);
			holder.addressLine1 = (TextView) row
					.findViewById(R.id.objectAddressLine1);
			holder.addressLine2 = (TextView) row
					.findViewById(R.id.objectAddressLine2);

			row.setTag(holder);
		} else {
			holder = (WorkObjectHolder) row.getTag();
		}

		WorkObject workObject = data[position];
		holder.number.setText(workObject.getNumber());
		holder.name.setText(workObject.getDescription());
		Address address = workObject.getAddress();
		holder.addressLine1.setText(address.getStreet() + " "
				+ address.getNumber());
		holder.addressLine2.setText(address.getZipCode() + " "
				+ address.getCity());

		return row;
	}

	private static class WorkObjectHolder {
		private TextView number;
		private TextView name;
		private TextView addressLine1;
		private TextView addressLine2;
	}

}
