package at.edu.uas.fmapp.classes;

public class TaskAssignment extends BaseClass {

	private Long workObjectId;
	private Long taskId;
	private Long workerId;
	private String frequency;
	private String frequencyInformation;

	public TaskAssignment(Long id) {
		super(id);
	}

	public Long getWorkObjectId() {
		return workObjectId;
	}

	public void setWorkObjectId(Long workObjectId) {
		this.workObjectId = workObjectId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
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

}
