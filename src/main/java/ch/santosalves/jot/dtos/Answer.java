package ch.santosalves.jot.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer {
	@XmlElement(name = "answer_id")
	private int answerId;
	@XmlElement(name = "answer_content")
	private String answer;
	@XmlElement
	private boolean isRightAnswer;

	public Answer() {
	}

	public Answer(int id, String resource, boolean rAnswer) {
		this.answerId = id;
		this.answer = resource;
		this.isRightAnswer = rAnswer;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int id) {
		this.answerId = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String resource) {
		this.answer = resource;
	}

	public boolean getIsRightAnswer() {
		return isRightAnswer;
	}

	public void setIsRightAnswer(boolean isRightAnswer) {
		this.isRightAnswer = isRightAnswer;
	}
}