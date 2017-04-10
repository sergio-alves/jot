package ch.santosalves.jot.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import ch.santosalves.jot.entities.LogSession;

@EnableAutoConfiguration
@EntityScan(basePackageClasses = { LogSession.class })
public interface LogSessionsRepository extends CrudRepository<LogSession, Integer> {

	List<LogSession> findByIpOrEmailAndDateBefore(String ip, String email, Date date);
}
