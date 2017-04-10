package ch.santosalves.jot.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.Level;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Level.class })
public interface LevelsRepository extends CrudRepository<Level, Integer> {

}
