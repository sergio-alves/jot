package ch.santosalves.jot.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the levels database table.
 * 
 */
@Entity
@Table(name = "levels")
@NamedQuery(name = "Level.findAll", query = "SELECT l FROM Level l")
public class Level implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	// bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource levelName;

	// bi-directional many-to-one association to Mcq
	@OneToMany(mappedBy = "levelBean")
	private List<Mcq> mcqs;

	public Level() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Resource getLevelName() {
		return this.levelName;
	}

	public void setLevelName(Resource resource) {
		this.levelName = resource;
	}

	public List<Mcq> getMcqs() {
		return this.mcqs;
	}

	public void setMcqs(List<Mcq> mcqs) {
		this.mcqs = mcqs;
	}

	public Mcq addMcq(Mcq mcq) {
		getMcqs().add(mcq);
		mcq.setLevelBean(this);

		return mcq;
	}

	public Mcq removeMcq(Mcq mcq) {
		getMcqs().remove(mcq);
		mcq.setLevelBean(null);

		return mcq;
	}

}