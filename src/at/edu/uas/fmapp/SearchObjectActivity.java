package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import at.edu.uas.fmapp.entity.Address;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.utils.FmHelper;
import at.edu.uas.fmapp.utils.GeoUtils;

public class SearchObjectActivity extends LoggedInBaseActivity {

	private static final double SEARCH_RADIUS = 2;

	private RadioGroup radioButtonGroupMode;
	private LinearLayout layoutAddress;
	private LinearLayout layoutQrCode;
	private LinearLayout layoutLocation;
	private EditText textStreet;
	private EditText textZipCode;

	private Location lastLocation;
	private Long lastQrCodeId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();

	}

	private void initLayout() {
		setContentView(R.layout.activity_search_object);
		layoutAddress = (LinearLayout) findViewById(R.id.layout_address);
		layoutQrCode = (LinearLayout) findViewById(R.id.layout_qr_code);
		layoutLocation = (LinearLayout) findViewById(R.id.layout_location);
		radioButtonGroupMode = (RadioGroup) findViewById(R.id.radio_button_group_find_by);
		textStreet = (EditText) findViewById(R.id.text_street);
		textZipCode = (EditText) findViewById(R.id.text_postal_code);
	}

	private void init() {
		setCheckedRadioButton(-1);

	}

	public void checkRadioButton(View v) {
		setCheckedRadioButton(radioButtonGroupMode.getCheckedRadioButtonId());
	}

	private void setCheckedRadioButton(int checkedRadioButtonId) {
		switch (checkedRadioButtonId) {
		case R.id.radio_button_address:
			layoutAddress.setVisibility(LinearLayout.VISIBLE);
			layoutQrCode.setVisibility(LinearLayout.GONE);
			layoutLocation.setVisibility(LinearLayout.GONE);
			break;
		case R.id.radio_button_qr_code:
			layoutAddress.setVisibility(LinearLayout.GONE);
			layoutQrCode.setVisibility(LinearLayout.VISIBLE);
			layoutLocation.setVisibility(LinearLayout.GONE);
			break;
		case R.id.radio_button_location:
			layoutAddress.setVisibility(LinearLayout.GONE);
			layoutQrCode.setVisibility(LinearLayout.GONE);
			layoutLocation.setVisibility(LinearLayout.VISIBLE);
			break;
		default:
			layoutAddress.setVisibility(LinearLayout.VISIBLE);
			layoutQrCode.setVisibility(LinearLayout.GONE);
			layoutLocation.setVisibility(LinearLayout.GONE);
			break;
		}
	}

	public void searchByAddress(View view) {

		if (FmHelper.isNullOrEmpty(textZipCode.getText().toString())
				&& FmHelper.isNullOrEmpty(textStreet.getText().toString())) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle(getString(R.string.activity_search_object_validation_title));
			dialog.setMessage(getString(R.string.activity_search_object_validation_address_input));
			dialog.show();

			return;
		}

		searchObjects(SearchMode.ADDRESS);
	}

	public void searchByQrCode(View view) {
		searchObjects(SearchMode.QR_CODE);
	}

	public void searchByLocation(View view) {
		searchObjects(SearchMode.LOCATION);
	}

	private void searchObjects(SearchMode mode) {
		List<WorkObject> result = new ArrayList<WorkObject>();

		for (WorkObject workObject : appState.getAllWorkObjects()) {
			if (workObjectMatches(mode, workObject)) {
				result.add(workObject);
			}
		}

		startResultActivity(result);
	}

	private boolean workObjectMatches(SearchMode mode, WorkObject workObject) {

		Address address = workObject.getAddress();

		switch (mode) {

		case ADDRESS:

			String zipCode = textZipCode.getText().toString();
			String street = textStreet.getText().toString();
			boolean zipMatches = true;
			boolean streetMatches = true;

			if (!FmHelper.isNullOrEmpty(zipCode)) {
				zipMatches = address.getZipCode().equals(zipCode);
			}
			if (!FmHelper.isNullOrEmpty(street)) {
				streetMatches = address.getStreet().toLowerCase(Locale.getDefault())
						.contains(street.toLowerCase(Locale.getDefault()));
			}

			return streetMatches && zipMatches;

		case QR_CODE:

			return workObject.getId().equals(lastQrCodeId);

		case LOCATION:

			if (lastLocation == null) {
				return false;
			}

			double distance = GeoUtils.calculateHaversineDistance(
					lastLocation.getLatitude(), lastLocation.getLongitude(),
					address.getLatitude(), address.getLongitude());

			return distance <= SEARCH_RADIUS;

		}

		return false;
	}

	private void startResultActivity(List<WorkObject> objectResultList) {
		appState.setWorkObjectSearchResult(objectResultList);
		startActivity(new Intent(this, SearchObjectResultActivity.class));
	}

	private static enum SearchMode {
		ADDRESS, QR_CODE, LOCATION
	}
}