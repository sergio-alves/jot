package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the translations database table.
 * 
 */
@Entity
@Table(name="translations")
@NamedQuery(name="Translation.findAll", query="SELECT t FROM Translation t")
public class Translation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TranslationPK id;

	@Lob
	@Column(nullable=false)
	private String content;

	//bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name="resource_id", nullable=false, insertable=false, updatable=false)
	private Resource resource;

	public Translation() {
	}

	public TranslationPK getId() {
		return this.id;
	}

	public void setId(TranslationPK id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}