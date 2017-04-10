package ch.santosalves.jot.dtos;

import java.util.ArrayList;
import java.util.List;

public class Questionnaire {
	private Integer id;
	private Level Level;
	private List<MultipleChoicesQuestion> questions;

	public static Questionnaire convertEntityToDto(ch.santosalves.jot.entities.Questionnaire eQ, Level level) {
		Questionnaire q = new Questionnaire();
		q.setId(eQ.getId());
		q.setLevel(level);
		List<MultipleChoicesQuestion> questions = new ArrayList<>();

		eQ.getMcqs().forEach(mcq -> {
			questions.add(new MultipleChoicesQuestion(mcq));
		});

		q.setQuestions(questions);

		return q;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Level getLevel() {
		return Level;
	}

	public void setLevel(Level difficultyLevel) {
		this.Level = difficultyLevel;
	}

	public List<MultipleChoicesQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<MultipleChoicesQuestion> questions) {
		this.questions = questions;
	}

	public Integer getQuestionsCount() {
		return this.questions.size();
	}

	public void setQuestionsCount(Integer questions) {
		// do nothing
	}
}
