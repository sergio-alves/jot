package ch.santosalves.jot.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.Mcq;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Mcq.class })
public interface McqRepository extends CrudRepository<Mcq, Integer> {

}
