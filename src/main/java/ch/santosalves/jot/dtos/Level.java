package ch.santosalves.jot.dtos;

public class Level {
	private int id;
	private String name;

	public Level() {
		// TODO Auto-generated constructor stub
	}

	public Level(ch.santosalves.jot.entities.Level entity) {
		id = entity.getId();
		name = entity.getLevelName().getContent();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Level convertEntityToDto(ch.santosalves.jot.entities.Level entity) {
		Level l = new Level();
		l.setId(entity.getId());
		l.setName(entity.getLevelName().getContent());
		return l;
	}
}
