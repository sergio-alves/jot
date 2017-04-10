package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the questions_in_questionnaires database table.
 * 
 */
@Embeddable
public class QuestionsInQuestionnairePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="questionnary_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int questionnaryId;

	@Column(name="question_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int questionId;

	public QuestionsInQuestionnairePK() {
	}
	public int getQuestionnaryId() {
		return this.questionnaryId;
	}
	public void setQuestionnaryId(int questionnaryId) {
		this.questionnaryId = questionnaryId;
	}
	public int getQuestionId() {
		return this.questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof QuestionsInQuestionnairePK)) {
			return false;
		}
		QuestionsInQuestionnairePK castOther = (QuestionsInQuestionnairePK)other;
		return 
			(this.questionnaryId == castOther.questionnaryId)
			&& (this.questionId == castOther.questionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.questionnaryId;
		hash = hash * prime + this.questionId;
		
		return hash;
	}
}