package ch.santosalves.jot.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.santosalves.jot.entities.Mcq;
import ch.santosalves.jot.entities.McqAnswer;

public class MultipleChoicesQuestion {
	private int id;
	private List<Answer> answers;
	private String question;
	private String explanation;
	private int secondsToAnswer;
	private Level level;
	private Map<Integer, String> categories = new HashMap<Integer, String>();

	public MultipleChoicesQuestion() {
	}

	public MultipleChoicesQuestion(Mcq mcq) {
		id = mcq.getId();
		explanation = mcq.getExplanation().getContent();
		level = new Level(mcq.getLevelBean());
		question = mcq.getQuestion().getContent();
		secondsToAnswer = mcq.getSecondsToAnswer();

		mcq.getCategories().forEach(c -> categories.put(c.getId(), c.getResource().getContent()));

		answers = new ArrayList<>();
		Answer a = null;
		mcq.getLevelBean().getLevelName();

		for (McqAnswer answer : mcq.getMcqAnswers()) {
			a = new Answer();
			a.setAnswerId(answer.getId());
			a.setAnswer(answer.getResource().getContent());
			a.setIsRightAnswer(answer.getRightAnswer() == 1 ? true : false);
			answers.add(a);
		}
	}

	/**
	 * A read only list
	 * 
	 * @return
	 */
	public List<Answer> getAnswers() {
		return Collections.unmodifiableList(answers);
	}

	public int getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public String getExplanation() {
		return explanation;
	}

	public int getSecondsToAnswer() {
		return secondsToAnswer;
	}

	public Level getLevel() {
		return level;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void setSecondsToAnswer(int secondsToAnswer) {
		this.secondsToAnswer = secondsToAnswer;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Map<Integer, String> getCategories() {
		return categories;
	}

	public void setCategories(Map<Integer, String> categories) {
		this.categories = categories;
	}
}
