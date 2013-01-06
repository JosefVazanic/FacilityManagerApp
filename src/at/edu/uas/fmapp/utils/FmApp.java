package at.edu.uas.fmapp.utils;

import android.app.Application;
import android.content.Intent;
import at.edu.uas.fmapp.WelcomeActivity;

public class FmApp extends Application {

	private String loggedInPerson;

	public String getLoggedInPerson() {
		return loggedInPerson;
	}

	public void setLoggedInPerson(String loggedInPerson) {
		this.loggedInPerson = loggedInPerson;
	}

	public void logout() {
		setLoggedInPerson(null);
	}

}
