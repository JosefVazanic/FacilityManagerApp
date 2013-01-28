package at.edu.uas.fmapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Intent;
import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.entity.Worker;
import at.edu.uas.fmapp.server.FmServiceProxy;

public class FmApp extends Application {

	public static final String LOGOUT_ACTION = "at.edu.uas.fmapp.ACTION_LOGOUT";

	private Worker loggedInPerson;
	private List<WorkObject> allWorkObjects;
	private List<WorkObject> workObjectSearchResult;
	private List<Task> taskList;
	private List<TaskAssignment> userTaskAssignments;
	private Map<Long, WorkItem> userTaskAssignmentsWorkItems;
	private WorkObject selectedItem;
	private List<TaskContainer> taskContainerList;
	private TaskContainer taskContainer;

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

	public WorkObject getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(WorkObject selectedItem) {
		this.selectedItem = selectedItem;
	}

	public FmServiceProxy getProxy() {
		return proxy;
	}
	
	public List<Task> getAllTasks() {
		if(taskList == null) {
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}
	
	public void setAllTasks(List<Task> result) {
		this.taskList = result;
	}

	public List<TaskAssignment> getUserTasksAssignments() {
		if(userTaskAssignments == null) {
			userTaskAssignments = new ArrayList<TaskAssignment>();
		}
		return userTaskAssignments;
	}

	public void setUserTasksAssignments(List<TaskAssignment> result) {
		this.userTaskAssignments = result;
	}
	
	public Map<Long, WorkItem> getUserTaskAssignmentWorkItems() {
		if(userTaskAssignmentsWorkItems == null) {
			userTaskAssignmentsWorkItems = new HashMap<Long,WorkItem>();
		}
		return userTaskAssignmentsWorkItems;
	}
	
	public void setUserTaskAssignmentWorkItems(Map<Long, WorkItem> map) {
		this.userTaskAssignmentsWorkItems = map;
	}
	
	public TaskContainer getCurrentTask() {
		if(userTaskAssignmentsWorkItems == null) {
			taskContainer = new TaskContainer();
		}
		return taskContainer;
	}
	
	public void setCurrentTask(TaskContainer taskContainer) {
		this.taskContainer = taskContainer;
	}
	
	public List<TaskContainer> getTaskContainerList() {
		return taskContainerList;
	}

	public void setTaskContainerList(List<TaskContainer> taskContainerList) {
		this.taskContainerList = taskContainerList;
	}

	public void logout() {
		setLoggedInPerson(null);
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(LOGOUT_ACTION);
		sendBroadcast(broadcastIntent);
	}
	
	public static boolean isTaskChecked(TaskContainer taskContainer) {
		boolean state = false;
		WorkItem workItem = taskContainer.getWorkItem();
		if (workItem != null && "Done".equals(workItem.getStatus())) {
			state = true;
		}
		return state;
	}
	
}
