package at.edu.uas.fmapp.classes;

public class BaseClass {

	protected Long id;

	public BaseClass() {
		super();
	}

	public BaseClass(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
