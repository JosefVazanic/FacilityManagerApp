package at.edu.uas.fmapp.utils;

import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;

public class TaskContainer {

	private Task task;
	private TaskAssignment assignment;

	public TaskContainer(Task task, TaskAssignment assignment) {
		super();
		this.task = task;
		this.assignment = assignment;
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

}
