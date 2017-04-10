package ch.santosalves.jot.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the mcq database table.
 * 
 */
@Entity
@Table(name = "mcq")
@NamedQuery(name = "Mcq.findAll", query = "SELECT m FROM Mcq m")
public class Mcq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "seconds_to_answer", nullable = false)
	private Integer secondsToAnswer;

	// bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource question;

	// bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name = "explanation_res_id", nullable = false)
	private Resource explanation;

	// bi-directional many-to-one association to McqAnswer
	@OneToMany(mappedBy = "mcq")
	private List<McqAnswer> mcqAnswers;

	// bi-directional many-to-many association to Questionnaire
	@ManyToMany(mappedBy = "mcqs")
	private List<Questionnaire> questionnaires;

	// bi-directional many-to-one association to Level
	@ManyToOne
	@JoinColumn(name = "level", nullable = false)
	private Level levelBean;

	// bi-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(name = "questions_categories", joinColumns = {
			@JoinColumn(name = "question", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "category", nullable = false) })
	private List<Category> categories;

	public Mcq() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSecondsToAnswer() {
		return this.secondsToAnswer;
	}

	public void setSecondsToAnswer(Integer secondsToAnswer) {
		this.secondsToAnswer = secondsToAnswer;
	}

	public Resource getQuestion() {
		return this.question;
	}

	public void setQuestion(Resource resource1) {
		this.question = resource1;
	}

	public Resource getExplanation() {
		return this.explanation;
	}

	public void setExplanation(Resource resource2) {
		this.explanation = resource2;
	}

	public List<McqAnswer> getMcqAnswers() {
		return this.mcqAnswers;
	}

	public void setMcqAnswers(List<McqAnswer> mcqAnswers) {
		this.mcqAnswers = mcqAnswers;
	}

	public McqAnswer addMcqAnswer(McqAnswer mcqAnswer) {
		getMcqAnswers().add(mcqAnswer);
		mcqAnswer.setMcq(this);

		return mcqAnswer;
	}

	public McqAnswer removeMcqAnswer(McqAnswer mcqAnswer) {
		getMcqAnswers().remove(mcqAnswer);
		mcqAnswer.setMcq(null);

		return mcqAnswer;
	}

	public List<Questionnaire> getQuestionnaires() {
		return this.questionnaires;
	}

	public void setQuestionnaires(List<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}

	public Level getLevelBean() {
		return this.levelBean;
	}

	public void setLevelBean(Level levelBean) {
		this.levelBean = levelBean;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}