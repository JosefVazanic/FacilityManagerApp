package at.edu.uas.fmapp.entity;

public class WorkObject extends BaseClass {

	private String number;
	private String description;

	private Address address;

	private Person contactPerson;

	public WorkObject(Long id) {
		super(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Person contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Override
	public String toString() {
		return number + ", " + description;
	}
}
