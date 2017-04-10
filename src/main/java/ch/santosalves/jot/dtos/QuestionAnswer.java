package ch.santosalves.jot.dtos;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswer {
	private Integer questionId;
	private List<Integer> answers = new ArrayList<>();

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public List<Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}
}
