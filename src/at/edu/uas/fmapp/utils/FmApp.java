package at.edu.uas.fmapp.utils;

import android.app.Application;

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
