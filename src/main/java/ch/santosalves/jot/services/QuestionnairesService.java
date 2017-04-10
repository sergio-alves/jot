package ch.santosalves.jot.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import ch.santosalves.jot.dtos.Questionnaire;
import ch.santosalves.jot.repositories.QuestionnairesRepository;

@Service
@EntityScan(basePackageClasses = { ch.santosalves.jot.entities.Questionnaire.class, QuestionnairesRepository.class })
public class QuestionnairesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnairesService.class);

	@Autowired
	QuestionnairesRepository qr;

	@Autowired
	LevelsService ls;

	public List<Questionnaire> getQuestionnairesForLevel(Integer level) {
		List<Questionnaire> l = new ArrayList<>();

		qr.findByLevel(level).forEach(questionnaire -> {
			l.add(Questionnaire.convertEntityToDto(questionnaire, ls.getLevelById(level)));
		});

		return l;
	}

	public Questionnaire getQuestionnaireById(Integer id) {
		ch.santosalves.jot.entities.Questionnaire q = qr.findOne(id);
		return Questionnaire.convertEntityToDto(q, ls.getLevelById(q.getLevel()));
	}

	public List<Questionnaire> getAllQuestionnaires() {
		List<Questionnaire> l = new ArrayList<>();

		qr.findAll().forEach(questionnaire -> {
			Questionnaire q = Questionnaire.convertEntityToDto(questionnaire,
					ls.getLevelById(questionnaire.getLevel()));
			l.add(q);
		});

		return l;
	}
}
