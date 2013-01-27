package at.edu.uas.fmapp;

import android.os.Bundle;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Person;
import at.edu.uas.fmapp.entity.WorkObject;

public class ObjectInformationActivity extends LoggedInBaseActivity {

	private WorkObject currentWorkObject;

	private TextView m_txtContactPersonName;
	private TextView m_txtContactPersonPhone;
	private TextView m_txtNumber;
	private TextView m_txtDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_information);

		m_txtContactPersonName = (TextView) findViewById(R.id.textViewContactPersonName);
		m_txtContactPersonPhone = (TextView) findViewById(R.id.textViewContactPersonPhone);
		m_txtNumber = (TextView) findViewById(R.id.textViewNumber);
		m_txtDescription = (TextView) findViewById(R.id.textViewDescription);

		currentWorkObject = appState.getSelectedItem();
		Person contactPerson = currentWorkObject.getContactPerson();

		// ContactPerson Name
		m_txtContactPersonName.setText(contactPerson.getFirstName() + " "
				+ contactPerson.getLastName());
		// ContactPerson Phone
		m_txtContactPersonPhone.setText(contactPerson.getMobile());
		// Number
		m_txtNumber.setText(currentWorkObject.getNumber());
		// Description
		m_txtDescription.setText(currentWorkObject.getDescription());

	}
}