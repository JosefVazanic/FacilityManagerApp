package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import at.edu.uas.fmapp.entity.AdditionalWorkItem;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;

public class NewObjectTask extends LoggedInBaseActivity {
	
	private AdditionalWorkItem additionalWorkItem;
	
	private Spinner m_frequency_spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_object_task);
	
		m_frequency_spinner = (Spinner) findViewById(R.id.editTaskFrequency);
		List<String> list = new ArrayList<String>();
		list.add("Once");
		list.add("Daily");
		list.add("Weekly");
		list.add("Monthly");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		m_frequency_spinner.setAdapter(dataAdapter);
	}
	
	public void addTask(View v) {
		additionalWorkItem = new AdditionalWorkItem();
		// Name
		EditText taskName = (EditText) findViewById(R.id.editTaskName);
		additionalWorkItem.setTaskName(taskName.getText().toString());
		// Description
		EditText taskDescription = (EditText) findViewById(R.id.editTaskDescription);
		additionalWorkItem.setTaskDescription(taskDescription.getText().toString());
		// Frequency
		additionalWorkItem.setFrequency((String) m_frequency_spinner.getSelectedItem());
		// Frequency Information
		EditText taskFrequencyInformation = (EditText) findViewById(R.id.editTaskFrequencyInformation);
		additionalWorkItem.setFrequencyInformation(taskFrequencyInformation.getText().toString());		
		// State
		Switch switcher = (Switch) findViewById(R.id.editTaskDetailSwitcher);
		additionalWorkItem.setStatus(switcher.isChecked() ? "Done" : "ToDo");
		// Default values depanding on user and workobject
		additionalWorkItem.setWorkerId(appState.getLoggedInPerson().getId());
		additionalWorkItem.setWorkObjectId(appState.getSelectedItem().getId());
		
		appState.getProxy().insertAdditionalWorkItem(additionalWorkItem, executionListener);
	}

	private FmServiceExecutionListener<Boolean> executionListener = new FmServiceExecutionListener<Boolean>() {
		@Override
		public void onPostExecute(Boolean result) {
			if(Boolean.TRUE.equals(result)) {
				Toast.makeText(getApplicationContext(), "Succesfully added!", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(), ObjectTasksActivity.class));
			} else {
				Toast.makeText(getApplicationContext(), "Not succesfully added!", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
}
