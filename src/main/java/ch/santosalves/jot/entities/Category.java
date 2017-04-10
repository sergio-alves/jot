package ch.santosalves.jot.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the categories database table.
 * 
 */
@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	private Integer parent;

	// bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name = "name", nullable = false)
	private Resource resource;

	// bi-directional many-to-many association to Mcq
	@ManyToMany(mappedBy = "categories")
	private List<Mcq> mcqs;

	public Category() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<Mcq> getMcqs() {
		return this.mcqs;
	}

	public void setMcqs(List<Mcq> mcqs) {
		this.mcqs = mcqs;
	}

}