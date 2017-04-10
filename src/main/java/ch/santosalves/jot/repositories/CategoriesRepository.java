package ch.santosalves.jot.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.Category;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Category.class })
public interface CategoriesRepository extends CrudRepository<Category, Integer> {

}
