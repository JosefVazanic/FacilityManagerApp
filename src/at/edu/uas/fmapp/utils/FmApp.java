package at.edu.uas.fmapp.utils;

import java.util.List;

import android.app.Application;
import android.content.Intent;
import at.edu.uas.fmapp.classes.WorkObject;
import at.edu.uas.fmapp.classes.Worker;
import at.edu.uas.fmapp.server.FmServiceProxy;

public class FmApp extends Application {

	private Worker loggedInPerson;
	private List<WorkObject> result;
	private WorkObject selectedItem;

	private FmServiceProxy proxy;

	public FmApp() {
		this.proxy = new FmServiceProxy();
	}

	public Worker getLoggedInPerson() {
		return loggedInPerson;
	}

	public void setLoggedInPerson(Worker loggedInPerson) {
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

	public FmServiceProxy getProxy() {
		return proxy;
	}

	public void logout() {
		setLoggedInPerson(null);
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("com.package.ACTION_LOGOUT");
		sendBroadcast(broadcastIntent);
	}

}
