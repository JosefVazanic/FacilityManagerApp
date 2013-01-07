package at.edu.uas.fmapp.classes;

public class WorkObject extends BaseClass {

	private String name, address;

	public WorkObject(Long id, String name, String address) {
		super(id);
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
