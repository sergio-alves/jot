package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the questions_in_questionnaires database table.
 * 
 */
@Entity
@Table(name="questions_in_questionnaires")
@NamedQuery(name="QuestionsInQuestionnaire.findAll", query="SELECT q FROM QuestionsInQuestionnaire q")
public class QuestionsInQuestionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private QuestionsInQuestionnairePK id;

	//bi-directional many-to-one association to Questionnaire
	@ManyToOne
	@JoinColumn(name="questionnary_id", nullable=false, insertable=false, updatable=false)
	private Questionnaire questionnaire;

	public QuestionsInQuestionnaire() {
	}

	public QuestionsInQuestionnairePK getId() {
		return this.id;
	}

	public void setId(QuestionsInQuestionnairePK id) {
		this.id = id;
	}

	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}