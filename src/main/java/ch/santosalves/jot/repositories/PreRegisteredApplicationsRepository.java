package ch.santosalves.jot.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.PreRegisteredApplications;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { PreRegisteredApplications.class })
public interface PreRegisteredApplicationsRepository extends CrudRepository<PreRegisteredApplications, Integer> {

    PreRegisteredApplications findOneByEmailAndPin(String email, String pin);
}
