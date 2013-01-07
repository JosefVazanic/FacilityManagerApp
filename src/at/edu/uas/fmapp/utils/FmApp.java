package at.edu.uas.fmapp.utils;

import java.util.List;

import android.app.Application;
import at.edu.uas.fmapp.classes.WorkObject;

public class FmApp extends Application {

	private String loggedInPerson;
	private List<WorkObject> result;
	private WorkObject selectedItem;

	public String getLoggedInPerson() {
		return loggedInPerson;
	}

	public void setLoggedInPerson(String loggedInPerson) {
		this.loggedInPerson = loggedInPerson;
	}

	public List<WorkObject> getResult() {
		return result;
	}

	public void setResult(List<WorkObject> result) {
		this.result = result;
	}

	public WorkObject getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(WorkObject selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void logout() {
		setLoggedInPerson(null);
	}

}
