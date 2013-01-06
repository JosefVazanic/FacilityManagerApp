package at.edu.uas.fmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import at.edu.uas.fmapp.utils.FmApp;

public class SearchObjects extends Activity {

	FmApp appState;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_objects);

		appState = (FmApp) getApplicationContext();
		TextView loginName = (TextView) findViewById(R.id.loginName);
		loginName.setText(appState.getLoggedInPerson());
		
		setCheckedRadioButton(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_objects, menu);
		return true;
	}
	
	public void checkRadioButton(View v) {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.searchRadioGroup);
		RadioButton checkedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
		setCheckedRadioButton(checkedRadioButton);
		System.out.print("checkRadioButton Clicked");
	}

	private void setCheckedRadioButton(RadioButton checkedRadioButton) {
		LinearLayout layoutAddress = (LinearLayout) findViewById(R.id.layoutAddress);
		LinearLayout layoutQrCode = (LinearLayout) findViewById(R.id.layoutQrCode);
		LinearLayout layoutLocation = (LinearLayout) findViewById(R.id.layoutLocation);
		if(checkedRadioButton == null) {
			layoutAddress.setVisibility(LinearLayout.VISIBLE);
			layoutQrCode.setVisibility(LinearLayout.INVISIBLE);
			layoutLocation.setVisibility(LinearLayout.INVISIBLE);
		} else if(checkedRadioButton != null && checkedRadioButton.getId() == R.id.radioAddress) {
			layoutAddress.setVisibility(LinearLayout.VISIBLE);
			layoutQrCode.setVisibility(LinearLayout.INVISIBLE);
			layoutLocation.setVisibility(LinearLayout.INVISIBLE);
		} else if(checkedRadioButton != null && checkedRadioButton.getId() == R.id.radioQrCode) {
			layoutAddress.setVisibility(LinearLayout.INVISIBLE);
			layoutQrCode.setVisibility(LinearLayout.VISIBLE);
			layoutLocation.setVisibility(LinearLayout.INVISIBLE);
		} else if(checkedRadioButton != null && checkedRadioButton.getId() == R.id.radioLocation) {
			layoutAddress.setVisibility(LinearLayout.INVISIBLE);
			layoutQrCode.setVisibility(LinearLayout.INVISIBLE);
			layoutLocation.setVisibility(LinearLayout.VISIBLE);
		}
	}

	public void logout(View v) {
		startActivity(new Intent(this, WelcomeActivity.class));
		appState.logout();
	}

	public void loadHome(View v) {
		startActivity(new Intent(this, HomeActivity.class));
	}
	
}
