package ch.santosalves.jot.dtos;

public class Category {
	private Integer id;
	private String name;

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
