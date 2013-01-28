package at.edu.uas.fmapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import at.edu.uas.fmapp.entity.Address;
import at.edu.uas.fmapp.entity.Person;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.utils.FmHelper;

public class ObjectInformationActivity extends LoggedInBaseActivity {

	private TextView textObjectName;
	private TextView textContactPersonName;
	private TextView textContactPersonPhone;
	private TextView textContactPersonMobile;
	private TextView textContactPersonEmail;
	private TextView textObjectNumber;
	private TextView textAddressLine1;
	private TextView textAddressLine2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		init();
	}

	private void initView() {
		setContentView(R.layout.activity_object_information);
		textObjectName = (TextView) findViewById(R.id.text_object_name);
		textContactPersonName = (TextView) findViewById(R.id.text_contact_person);
		textContactPersonPhone = (TextView) findViewById(R.id.text_contact_person_phone);
		textContactPersonMobile = (TextView) findViewById(R.id.text_contact_person_mobile);
		textContactPersonEmail = (TextView) findViewById(R.id.text_contact_person_email);
		textObjectNumber = (TextView) findViewById(R.id.text_object_number);
		textAddressLine1 = (TextView) findViewById(R.id.text_address_line_1);
		textAddressLine2 = (TextView) findViewById(R.id.text_address_line_2);
	}

	private void init() {
		WorkObject currentWorkObject = appState.getSelectedItem();
		Person contactPerson = currentWorkObject.getContactPerson();
		Address address = currentWorkObject.getAddress();

		// object
		setTextOrHideIfEmpty(textObjectName, currentWorkObject.getDescription());

		// contact
		String contactPersonName = contactPerson.getFirstName() + " "
				+ contactPerson.getLastName();
		setTextOrHideIfEmpty(textContactPersonName, contactPersonName);
		setTextOrHideIfEmpty(textContactPersonPhone, contactPerson.getPhone());
		setTextOrHideIfEmpty(textContactPersonMobile, contactPerson.getMobile());
		setTextOrHideIfEmpty(textContactPersonEmail, contactPerson.getEmail());

		// object number
		setTextOrHideIfEmpty(textObjectNumber, currentWorkObject.getNumber());

		// address
		setTextOrHideIfEmpty(textAddressLine1, address.getStreet() + " "
				+ address.getNumber());
		setTextOrHideIfEmpty(textAddressLine2, address.getZipCode() + " "
				+ address.getCity());
	}

	private void setTextOrHideIfEmpty(TextView textView, String text) {
		if (FmHelper.isNullOrEmpty(text)) {
			textView.setVisibility(View.GONE);
			textView.setText("");
		} else {
			textView.setVisibility(View.VISIBLE);
			textView.setText(text);
		}
	}
}