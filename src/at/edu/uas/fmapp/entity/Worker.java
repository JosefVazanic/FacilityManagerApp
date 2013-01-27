package at.edu.uas.fmapp.entity;

public class Worker extends Person {

	protected String userName;
	protected Long latitude;
	protected Long longitude;

	public Worker(Long id) {
		super(id);
	}

	public Worker(String userName) {
		super();
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
}
