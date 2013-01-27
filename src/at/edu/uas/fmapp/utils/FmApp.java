package at.edu.uas.fmapp.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.entity.Worker;
import at.edu.uas.fmapp.server.FmServiceProxy;

public class FmApp extends Application {

	public static final String LOGOUT_ACTION = "at.edu.uas.fmapp.ACTION_LOGOUT";

	private Worker loggedInPerson;
	private List<WorkObject> allWorkObjects;
	private List<WorkObject> workObjectSearchResult;
	private WorkObject selectedItem;

	private FmServiceProxy proxy;

	public FmApp() {
		this.proxy = new FmServiceProxy();
		this.allWorkObjects = new ArrayList<WorkObject>();
		this.workObjectSearchResult = new ArrayList<WorkObject>();
	}

	public Worker getLoggedInPerson() {
		return loggedInPerson;
	}

	public void setLoggedInPerson(Worker loggedInPerson) {
		this.loggedInPerson = loggedInPerson;
	}

	public List<WorkObject> getAllWorkObjects() {
		return allWorkObjects;
	}

	public void setAllWorkObjects(List<WorkObject> allWorkObjects) {
		this.allWorkObjects = allWorkObjects;
	}

	public List<WorkObject> getWorkObjectSearchResult() {
		return workObjectSearchResult;
	}

	public void setWorkObjectSearchResult(
			List<WorkObject> workObjectsSearchResult) {
		this.workObjectSearchResult = workObjectsSearchResult;
	}

	public void setProxy(FmServiceProxy proxy) {
		this.proxy = proxy;
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
		broadcastIntent.setAction(LOGOUT_ACTION);
		sendBroadcast(broadcastIntent);
	}

}
