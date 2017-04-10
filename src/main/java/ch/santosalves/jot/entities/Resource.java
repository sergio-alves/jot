package ch.santosalves.jot.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the resources database table.
 * 
 */
@Entity
@Table(name = "resources")
@NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;

	@Lob
	@Column(nullable = false)
	private String content;

	// bi-directional many-to-one association to Level
	@OneToMany(mappedBy = "levelName")
	private List<Level> levels;

	// bi-directional many-to-one association to Mcq
	@OneToMany(mappedBy = "question")
	private List<Mcq> mcqs1;

	// bi-directional many-to-one association to Mcq
	@OneToMany(mappedBy = "explanation")
	private List<Mcq> mcqs2;

	// bi-directional many-to-one association to McqAnswer
	@OneToMany(mappedBy = "resource")
	private List<McqAnswer> mcqAnswers;

	// bi-directional many-to-one association to Translation
	@OneToMany(mappedBy = "resource")
	private List<Translation> translations;

	// bi-directional many-to-one association to Category
	@OneToMany(mappedBy = "resource")
	private List<Category> categories;

	public Resource() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Level> getLevels() {
		return this.levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public Level addLevel(Level level) {
		getLevels().add(level);
		level.setLevelName(this);

		return level;
	}

	public Level removeLevel(Level level) {
		getLevels().remove(level);
		level.setLevelName(null);

		return level;
	}

	public List<Mcq> getMcqs1() {
		return this.mcqs1;
	}

	public void setMcqs1(List<Mcq> mcqs1) {
		this.mcqs1 = mcqs1;
	}

	public Mcq addMcqs1(Mcq mcqs1) {
		getMcqs1().add(mcqs1);
		mcqs1.setQuestion(this);

		return mcqs1;
	}

	public Mcq removeMcqs1(Mcq mcqs1) {
		getMcqs1().remove(mcqs1);
		mcqs1.setQuestion(null);

		return mcqs1;
	}

	public List<Mcq> getMcqs2() {
		return this.mcqs2;
	}

	public void setMcqs2(List<Mcq> mcqs2) {
		this.mcqs2 = mcqs2;
	}

	public Mcq addMcqs2(Mcq mcqs2) {
		getMcqs2().add(mcqs2);
		mcqs2.setExplanation(this);

		return mcqs2;
	}

	public Mcq removeMcqs2(Mcq mcqs2) {
		getMcqs2().remove(mcqs2);
		mcqs2.setExplanation(null);

		return mcqs2;
	}

	public List<McqAnswer> getMcqAnswers() {
		return this.mcqAnswers;
	}

	public void setMcqAnswers(List<McqAnswer> mcqAnswers) {
		this.mcqAnswers = mcqAnswers;
	}

	public McqAnswer addMcqAnswer(McqAnswer mcqAnswer) {
		getMcqAnswers().add(mcqAnswer);
		mcqAnswer.setResource(this);

		return mcqAnswer;
	}

	public McqAnswer removeMcqAnswer(McqAnswer mcqAnswer) {
		getMcqAnswers().remove(mcqAnswer);
		mcqAnswer.setResource(null);

		return mcqAnswer;
	}

	public List<Translation> getTranslations() {
		return this.translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	public Translation addTranslation(Translation translation) {
		getTranslations().add(translation);
		translation.setResource(this);

		return translation;
	}

	public Translation removeTranslation(Translation translation) {
		getTranslations().remove(translation);
		translation.setResource(null);

		return translation;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
		category.setResource(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
		category.setResource(null);

		return category;
	}

}