package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the translations database table.
 * 
 */
@Embeddable
public class TranslationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="resource_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int resourceId;

	@Column(unique=true, nullable=false, length=5)
	private String lang;

	public TranslationPK() {
	}
	public int getResourceId() {
		return this.resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getLang() {
		return this.lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TranslationPK)) {
			return false;
		}
		TranslationPK castOther = (TranslationPK)other;
		return 
			(this.resourceId == castOther.resourceId)
			&& this.lang.equals(castOther.lang);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.resourceId;
		hash = hash * prime + this.lang.hashCode();
		
		return hash;
	}
}