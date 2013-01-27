package at.edu.uas.fmapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import at.edu.uas.fmapp.entity.Address;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.utils.FmHelper;
import at.edu.uas.fmapp.utils.GeoUtils;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SearchObjectActivity extends LoggedInBaseActivity {

	private static final double SEARCH_RADIUS_IN_KM = 2;

	private RadioGroup radioButtonGroupMode;
	private LinearLayout layoutAddress;
	private LinearLayout layoutQrCode;
	private LinearLayout layoutLocation;
	private EditText textStreet;
	private EditText textZipCode;

	private LocationListener locationListener = null;
	private Location lastLocation;
	private Long lastQrCodeWorkObjectId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
		setCheckedRadioButton(radioButtonGroupMode.getCheckedRadioButtonId());
		requestLocationUpdates();
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopLocationUpdates();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// handle scan result if there's one
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			// process content of scan and trigger search
			processQrScanResult(scanResult.getContents());
			searchObjects(SearchMode.QR_CODE);
		}
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
		Location lastGpsLocation = getLocationManager().getLastKnownLocation(
				LocationManager.GPS_PROVIDER);
		Location lastNetworkLocation = getLocationManager()
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (GeoUtils.isBetterLocation(lastGpsLocation, lastNetworkLocation)) {
			lastLocation = lastGpsLocation;
		} else {
			lastLocation = lastNetworkLocation;
		}
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
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();

		// searchObjects(SearchMode.QR_CODE);
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
				streetMatches = address.getStreet()
						.toLowerCase(Locale.getDefault())
						.contains(street.toLowerCase(Locale.getDefault()));
			}

			return streetMatches && zipMatches;

		case QR_CODE:

			// compare scanned id with id of work object
			return workObject.getId().equals(lastQrCodeWorkObjectId);

		case LOCATION:

			if (lastLocation == null) {
				return false;
			}

			double distance = GeoUtils.calculateHaversineDistance(
					lastLocation.getLatitude(), lastLocation.getLongitude(),
					GeoUtils.convertCoordinateToFloat(address.getLatitude()),
					GeoUtils.convertCoordinateToFloat(address.getLongitude()));

			return distance <= SEARCH_RADIUS_IN_KM;

		}

		return false;
	}

	private void startResultActivity(List<WorkObject> objectResultList) {
		appState.setWorkObjectSearchResult(objectResultList);
		startActivity(new Intent(this, SearchObjectResultActivity.class));
	}

	private void requestLocationUpdates() {
		// register location listener for updates every 1 second & 500 meter
		LocationManager locationManager = getLocationManager();

		locationListener = new CurrentLocationListener();
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 1000, 500, locationListener);
		}
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 1000, 500,
					locationListener);
		}
	}

	private void stopLocationUpdates() {
		// stop requesting location updates
		getLocationManager().removeUpdates(locationListener);
	}

	private void updateLocation(Location location) {
		// no update necessary if last known location is
		// more precise than received location.
		if (GeoUtils.isBetterLocation(location, lastLocation)) {
			lastLocation = location;
		}
	}

	private LocationManager getLocationManager() {
		return (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	private void processQrScanResult(String scanResult) {
		lastQrCodeWorkObjectId = null;

		// parse code scan result
		if (FmHelper.isNullOrEmpty(scanResult)) {
			return;
		}
		String[] qrCodeParts = scanResult.split(";");
		if (qrCodeParts != null && qrCodeParts.length < 2) {
			return;
		}
		String scannedWorkObjectIdString = qrCodeParts[0];
		if (FmHelper.isNullOrEmpty(scannedWorkObjectIdString)) {
			return;
		}

		// convert scanned it to long value
		Long scannedWorkObjectId = null;
		try {
			scannedWorkObjectId = Long.valueOf(scannedWorkObjectIdString);
		} catch (Exception e) {
			Log.d("Scanned work object id convert error", e.getMessage());
		}

		lastQrCodeWorkObjectId = scannedWorkObjectId;
	}

	private static enum SearchMode {
		ADDRESS, QR_CODE, LOCATION
	}

	private class CurrentLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// nothing to do
		}

		@Override
		public void onProviderEnabled(String provider) {
			// nothing to do
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// nothing to do
		}
	}
}