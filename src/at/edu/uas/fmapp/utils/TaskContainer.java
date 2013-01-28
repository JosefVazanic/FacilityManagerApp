package at.edu.uas.fmapp.utils;

import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkItem;

public class TaskContainer {

	private Task task;
	private TaskAssignment assignment;
	private WorkItem workItem;
	
	public TaskContainer() {
		super();
	}

	public TaskContainer(Task task, TaskAssignment assignment) {
		this();
		this.task = task;
		this.assignment = assignment;
	}
	
	public TaskContainer(Task task, TaskAssignment assignment, WorkItem workItem) {
		this(task,assignment);
		this.workItem = workItem;
	}
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskAssignment getAssignment() {
		return assignment;
	}

	public void setAssignment(TaskAssignment assignment) {
		this.assignment = assignment;
	}

	public WorkItem getWorkItem() {
		return workItem;
	}

	public void setWorkItem(WorkItem workItem) {
		this.workItem = workItem;
	}

}
