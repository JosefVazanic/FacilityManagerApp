package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import at.edu.uas.fmapp.classes.Address;
import at.edu.uas.fmapp.classes.WorkObject;
import at.edu.uas.fmapp.server.FmServiceExecutionListener;

public class SearchObjects extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_objects);

		initLogin();

		setCheckedRadioButton(null);
	}

	public void checkRadioButton(View v) {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.searchRadioGroup);
		RadioButton checkedRadioButton = (RadioButton) findViewById(radioGroup
				.getCheckedRadioButtonId());
		setCheckedRadioButton(checkedRadioButton);
		System.out.print("checkRadioButton Clicked");
	}

	private void setCheckedRadioButton(RadioButton checkedRadioButton) {
		LinearLayout layoutAddress = (LinearLayout) findViewById(R.id.layoutAddress);
		LinearLayout layoutQrCode = (LinearLayout) findViewById(R.id.layoutQrCode);
		LinearLayout layoutLocation = (LinearLayout) findViewById(R.id.layoutLocation);
		if (checkedRadioButton == null) {
			layoutAddress.setVisibility(LinearLayout.VISIBLE);
			layoutQrCode.setVisibility(LinearLayout.INVISIBLE);
			layoutLocation.setVisibility(LinearLayout.INVISIBLE);
		} else if (checkedRadioButton != null
				&& checkedRadioButton.getId() == R.id.radioAddress) {
			layoutAddress.setVisibility(LinearLayout.VISIBLE);
			layoutQrCode.setVisibility(LinearLayout.INVISIBLE);
			layoutLocation.setVisibility(LinearLayout.INVISIBLE);
		} else if (checkedRadioButton != null
				&& checkedRadioButton.getId() == R.id.radioQrCode) {
			layoutAddress.setVisibility(LinearLayout.INVISIBLE);
			layoutQrCode.setVisibility(LinearLayout.VISIBLE);
			layoutLocation.setVisibility(LinearLayout.INVISIBLE);
		} else if (checkedRadioButton != null
				&& checkedRadioButton.getId() == R.id.radioLocation) {
			layoutAddress.setVisibility(LinearLayout.INVISIBLE);
			layoutQrCode.setVisibility(LinearLayout.INVISIBLE);
			layoutLocation.setVisibility(LinearLayout.VISIBLE);
		}
	}

	public void searchAddress(View v) {
		List<WorkObject> result = new ArrayList<WorkObject>();
		result.add(createDummyData().get(0));
		appState.setResult(result);
		startActivity(new Intent(this, ResultObjects.class));
	}

	public void searchQrCode(View v) {
		appState.getProxy().getWorkObjectList(
				new FmServiceExecutionListener<List<WorkObject>>() {

					@Override
					public void onPostExecute(List<WorkObject> result) {
						appState.setResult(result);
						SearchObjects.this.startActivity(new Intent(
								SearchObjects.this, ResultObjects.class));
					}
				});
	}

	public void searchLocation(View v) {
		List<WorkObject> result = new ArrayList<WorkObject>();
		result.add(createDummyData().get(2));
		appState.setResult(result);
		startActivity(new Intent(this, ResultObjects.class));
	}

	private List<WorkObject> createDummyData() {
		List<WorkObject> dummyList = new ArrayList<WorkObject>();
		WorkObject dummy = null;

		dummy = new WorkObject(1L);
		dummy.setNumber("123");
		dummy.setDescription("Objekt A");
		dummy.setAddress(new Address(1L));
		dummy.getAddress().setCity("Wien");
		dummy.getAddress().setZipCode("1010");
		dummy.getAddress().setStreet("Dorfgasse");
		dummy.getAddress().setNumber("12");
		dummyList.add(dummy);

		dummy = new WorkObject(2L);
		dummy.setNumber("555");
		dummy.setDescription("Objekt B");
		dummy.setAddress(new Address(2L));
		dummy.getAddress().setCity("Wien");
		dummy.getAddress().setZipCode("1130");
		dummy.getAddress().setStreet("MachSauber Straﬂe");
		dummy.getAddress().setNumber("99");
		dummyList.add(dummy);

		dummy = new WorkObject(3L);
		dummy.setNumber("789");
		dummy.setDescription("Objekt C");
		dummy.setAddress(new Address(3L));
		dummy.getAddress().setCity("Wien");
		dummy.getAddress().setZipCode("1090");
		dummy.getAddress().setStreet("Schmutzgasse");
		dummy.getAddress().setNumber("10");
		dummyList.add(dummy);

		return dummyList;
	}

}
