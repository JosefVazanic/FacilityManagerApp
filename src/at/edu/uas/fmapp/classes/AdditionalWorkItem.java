package at.edu.uas.fmapp.classes;

public class AdditionalWorkItem extends BaseClass {

	private String taskName;
	private String taskDescription;
	private String frequency;
	private String frequencyInformation;
	private String status;
	private Long workerId;
	private Long workObjectId;

	public AdditionalWorkItem() {

	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyInformation() {
		return frequencyInformation;
	}

	public void setFrequencyInformation(String frequencyInformation) {
		this.frequencyInformation = frequencyInformation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public Long getWorkObjectId() {
		return workObjectId;
	}

	public void setWorkObjectId(Long workObjectId) {
		this.workObjectId = workObjectId;
	}

}
