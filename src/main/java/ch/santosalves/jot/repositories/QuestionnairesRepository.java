package ch.santosalves.jot.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.santosalves.jot.entities.Questionnaire;

@Repository
public interface QuestionnairesRepository extends CrudRepository<Questionnaire, Integer> {

	List<Questionnaire> findByLevel(Integer level);
}
