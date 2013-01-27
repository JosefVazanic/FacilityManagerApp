package at.edu.uas.fmapp;

import android.os.Bundle;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Person;
import at.edu.uas.fmapp.entity.WorkObject;

public class ObjectInformationActivity extends LoggedInBaseActivity {
	
	private WorkObject currentWorkObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_information);
		
		currentWorkObject = appState.getSelectedItem();
		Person contactPerson = currentWorkObject.getContactPerson();
		
		// ContactPersonName
		String strContactPersonName = contactPerson.getFirstName() + " " + contactPerson.getLastName();
		TextView txtContactPersonName = (TextView) findViewById(R.id.textViewContactPersonName);
		txtContactPersonName.setText(strContactPersonName);
	}
}