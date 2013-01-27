package at.edu.uas.fmapp.entity;

import java.util.Date;

public class WorkItem extends BaseClass {

	private Long taskAssignmentId;
	private String status;
	private Date date;

	public WorkItem() {

	}

	public Long getTaskAssignmentId() {
		return taskAssignmentId;
	}

	public void setTaskAssignmentId(Long taskAssignmentId) {
		this.taskAssignmentId = taskAssignmentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
