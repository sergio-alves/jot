package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the questionnaires database table.
 * 
 */
@Entity
@Table(name="questionnaires")
@NamedQuery(name="Questionnaire.findAll", query="SELECT q FROM Questionnaire q")
public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int level;

	//bi-directional many-to-many association to Mcq
	@ManyToMany
	@JoinTable(
		name="questions_in_questionnaires"
		, joinColumns={
			@JoinColumn(name="questionnary_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="question_id", nullable=false)
			}
		)
	private List<Mcq> mcqs;

	//bi-directional many-to-one association to QuestionsInQuestionnaire
	@OneToMany(mappedBy="questionnaire")
	private List<QuestionsInQuestionnaire> questionsInQuestionnaires;

	public Questionnaire() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Mcq> getMcqs() {
		return this.mcqs;
	}

	public void setMcqs(List<Mcq> mcqs) {
		this.mcqs = mcqs;
	}

	public List<QuestionsInQuestionnaire> getQuestionsInQuestionnaires() {
		return this.questionsInQuestionnaires;
	}

	public void setQuestionsInQuestionnaires(List<QuestionsInQuestionnaire> questionsInQuestionnaires) {
		this.questionsInQuestionnaires = questionsInQuestionnaires;
	}

	public QuestionsInQuestionnaire addQuestionsInQuestionnaire(QuestionsInQuestionnaire questionsInQuestionnaire) {
		getQuestionsInQuestionnaires().add(questionsInQuestionnaire);
		questionsInQuestionnaire.setQuestionnaire(this);

		return questionsInQuestionnaire;
	}

	public QuestionsInQuestionnaire removeQuestionsInQuestionnaire(QuestionsInQuestionnaire questionsInQuestionnaire) {
		getQuestionsInQuestionnaires().remove(questionsInQuestionnaire);
		questionsInQuestionnaire.setQuestionnaire(null);

		return questionsInQuestionnaire;
	}

}