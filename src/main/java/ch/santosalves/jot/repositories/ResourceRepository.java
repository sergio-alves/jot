package ch.santosalves.jot.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.Resource;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Resource.class })
public interface ResourceRepository extends CrudRepository<Resource, Integer> {

}
