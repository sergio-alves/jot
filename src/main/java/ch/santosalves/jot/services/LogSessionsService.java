package ch.santosalves.jot.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ch.santosalves.jot.dtos.LoggedSession;
import ch.santosalves.jot.entities.LogSession;
import ch.santosalves.jot.repositories.LogSessionsRepository;

@Service
@EntityScan(basePackageClasses = { ch.santosalves.jot.entities.LogSession.class, LogSessionsRepository.class })
@PropertySource("classpath:application.properties")
public class LogSessionsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogSessionsService.class);

    @Autowired
    private LogSessionsRepository lsr;

    /**
     * Days between attempts
     */
    private @Value("${jot.delay.between.attempts}") Integer delayBetweenAttemps;

    public void persist(LogSession ls) {
        lsr.save(ls);
    }

    public List<LoggedSession> findByIpOrEmail(String ip, String email) {
        List<LoggedSession> list = new ArrayList<>();

        if (delayBetweenAttemps < 0) {
            return list;
        }

        Date date = Date.valueOf(LocalDate.now().minusDays(delayBetweenAttemps));

        lsr.findByIpOrEmailAndDateBefore(ip, email, date).forEach(l -> list.add(LoggedSession.convertEntityToDto(l)));
        return list;
    }
}
