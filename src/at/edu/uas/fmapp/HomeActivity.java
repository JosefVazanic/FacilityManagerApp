package at.edu.uas.fmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		initLogin();
	}
	
	public void loadObjectSearch(View v) {
		startActivity(new Intent(this, SearchObjects.class));
	}

}
