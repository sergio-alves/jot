package ch.santosalves.jot.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.McqAnswer;
import ch.santosalves.jot.entities.Resource;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { McqAnswer.class })
public interface McqAnswerRepository extends CrudRepository<McqAnswer, Integer> {

}
