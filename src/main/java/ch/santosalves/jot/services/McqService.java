package ch.santosalves.jot.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.santosalves.jot.dtos.Answer;
import ch.santosalves.jot.dtos.MultipleChoicesQuestion;
import ch.santosalves.jot.entities.Mcq;
import ch.santosalves.jot.entities.McqAnswer;
import ch.santosalves.jot.entities.Resource;
import ch.santosalves.jot.repositories.LevelsRepository;
import ch.santosalves.jot.repositories.McqAnswerRepository;
import ch.santosalves.jot.repositories.McqRepository;
import ch.santosalves.jot.repositories.ResourceRepository;

@Service
@EntityScan(basePackageClasses = { ch.santosalves.jot.entities.Level.class, LevelsRepository.class })
public class McqService {
	private static final Logger LOGGER = LoggerFactory.getLogger(McqService.class);

	@Autowired
	McqRepository mcqR;

	@Autowired
	ResourceRepository resR;

	@Autowired
	McqAnswerRepository mcqAR;

	/**
	 * Gets all available questions (no filter)
	 * 
	 * @return a totally unfiltered list of questions
	 */
	public List<MultipleChoicesQuestion> getAllQuestions() {
		Iterable<Mcq> iterator = mcqR.findAll();
		List<MultipleChoicesQuestion> questions = new ArrayList<>();
		iterator.forEach(mcq -> questions.add(new MultipleChoicesQuestion(mcq)));
		return questions;
	}

	@Transactional(rollbackFor = { Exception.class, DataIntegrityViolationException.class,
			ConstraintViolationException.class })
	public void addQuestion(MultipleChoicesQuestion question) throws Exception {
		Mcq mcq = new Mcq();
		mcq.setSecondsToAnswer(question.getSecondsToAnswer());

		Resource resQuestion = new Resource();
		resQuestion.setContent(question.getQuestion());

		Resource resExplanation = new Resource();
		resExplanation.setContent(question.getExplanation());

		mcq.setQuestion(resR.save(resQuestion));
		mcq.setExplanation(resR.save(resExplanation));

		if (mcqR.save(mcq).getId() == null) {
			throw new Exception("Could not add new question");
		}
	}

	/**
	 * Gets one question by id
	 * 
	 * @param id
	 *            The question id
	 * @return A dto object
	 */
	public MultipleChoicesQuestion getOne(Integer id) {
		return new MultipleChoicesQuestion(mcqR.findOne(id));
	}

	@Transactional(rollbackFor = { Exception.class, DataIntegrityViolationException.class,
			ConstraintViolationException.class })
	public void addNewAnswerToQuestion(Answer answer, Integer questionId) {

		McqAnswer mcqAnswer = new McqAnswer();
		Resource answerText = new Resource();
		answerText.setContent(answer.getAnswer());
		mcqAnswer.setResource(resR.save(answerText));
		mcqAnswer.setRightAnswer(answer.getIsRightAnswer() ? 1 : 0);

		Mcq mcq = mcqR.findOne(questionId);
		mcqAnswer.setMcq(mcq);
		mcq.getMcqAnswers().add(mcqAR.save(mcqAnswer));
		mcqR.save(mcq);
	}
}
