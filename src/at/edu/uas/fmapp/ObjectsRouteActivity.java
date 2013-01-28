package at.edu.uas.fmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import at.edu.uas.fmapp.entity.Address;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.utils.FmApp;
import at.edu.uas.fmapp.utils.GeoUtils;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.RouteManager;
import com.mapquest.android.maps.RouteResponse;
import com.mapquest.android.maps.ServiceResponse.Info;

public class ObjectsRouteActivity extends MapActivity {

	private FmApp appState;

	private MapView mapView;
	private WebView itineraryView;

	private MyLocationOverlay myLocationOverlay;
	private DefaultItemizedOverlay poiOverlay;
	private RouteManager routeManager;
	private GeoPoint workObjectGeoPoint;

	private LinearLayout mapLayout;
	private LinearLayout itineraryLayout;
	private Button showItineraryButton;
	private Button showMapButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appState = (FmApp) getApplicationContext();

		if (appState.getLoggedInPerson() == null) {
			// if there is no logged in person we have an erroneous state
			// and should not proceed.
			appState.logout();
			finish();
			return;
		}

		// filter for logout action
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(FmApp.LOGOUT_ACTION);
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("onReceive", "Logout in progress");
				startActivity(new Intent(getBaseContext(),
						WelcomeActivity.class));
				Toast.makeText(getBaseContext(),
						getString(R.string.activity_welcome_logout_message),
						Toast.LENGTH_LONG).show();
				finish();
			}
		}, intentFilter);

		initView();
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_home:
			startActivity(new Intent(this, HomeActivity.class));
			return true;
		case R.id.menu_item_logout:
			appState.logout();
			return true;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void initView() {
		setContentView(R.layout.activity_object_route);
		mapView = (MapView) findViewById(R.id.mapView);
		itineraryView = (WebView) findViewById(R.id.itineraryView);
		mapLayout = (LinearLayout) findViewById(R.id.mapLayout);
		itineraryLayout = (LinearLayout) findViewById(R.id.itineraryLayout);
		showItineraryButton = (Button) findViewById(R.id.showItineraryButton);
		showMapButton = (Button) findViewById(R.id.showMapButton);
	}

	protected void init() {
		initMapView();
		initMyLocationOverlay();
		initPoiOverlay();
		initRouteManager();
	}

	private void initMapView() {
		// set the zoom level, center point & enable the zoom controls
		mapView.getController().setZoom(14);
		mapView.setBuiltInZoomControls(true);
	}

	private void initMyLocationOverlay() {
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
		myLocationOverlay.runOnFirstFix(new OnFirstFixRunnable());
	}

	private void initPoiOverlay() {
		Drawable icon = getResources().getDrawable(R.drawable.location_marker);
		poiOverlay = new DefaultItemizedOverlay(icon);

		WorkObject workObject = appState.getSelectedItem();
		Address address = workObject.getAddress();
		workObjectGeoPoint = new GeoPoint(
				GeoUtils.convertCoordinateToFloat(address.getLatitude()),
				GeoUtils.convertCoordinateToFloat(address.getLongitude()));

		poiOverlay.addItem(new OverlayItem(workObjectGeoPoint, workObject
				.getDescription(), ""));
		mapView.getOverlays().add(poiOverlay);
	}

	private void initRouteManager() {
		routeManager = new RouteManager(this);
		routeManager.setMapView(mapView);
		routeManager.setItineraryView(itineraryView);
		routeManager.setDebug(true);
		routeManager.setRouteCallback(new MyRouteCallback());
	}

	public void displayRoute(GeoPoint from, GeoPoint to) {
		if (from == null || to == null) {
			return;
		}
		routeManager.createRoute(
				GeoUtils.getCoordinateStringFromGeoPoint(from),
				GeoUtils.getCoordinateStringFromGeoPoint(to));
	}

	public void onShowMapClick(View view) {
		mapLayout.setVisibility(View.VISIBLE);
		showItineraryButton.setVisibility(View.VISIBLE);

		itineraryLayout.setVisibility(View.GONE);
		showMapButton.setVisibility(View.GONE);
	}

	public void onShowRouteClick(View view) {
		mapLayout.setVisibility(View.GONE);
		showItineraryButton.setVisibility(View.GONE);

		itineraryLayout.setVisibility(View.VISIBLE);
		showMapButton.setVisibility(View.VISIBLE);
	}

	private class MyRouteCallback implements RouteManager.RouteCallback {

		@Override
		public void onError(RouteResponse routeResponse) {
			Info info = routeResponse.info;
			int statusCode = info.statusCode;

			String message = getString(R.string.activity_object_route_error,
					statusCode, info.messages.toString());
			Toast.makeText(getApplicationContext(), message.toString(),
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onSuccess(RouteResponse routeResponse) {
			showItineraryButton.setEnabled(true);
		}
	}

	private class OnFirstFixRunnable implements Runnable {

		@Override
		public void run() {
			// zoom to current location
			GeoPoint currentLocation = myLocationOverlay.getMyLocation();
			mapView.getController().animateTo(currentLocation);
			mapView.getController().setZoom(14);
			mapView.getOverlays().add(myLocationOverlay);
			myLocationOverlay.setFollowing(true);
			// display route
			displayRoute(myLocationOverlay.getMyLocation(), workObjectGeoPoint);
		}
	}
}