package ch.santosalves.jot.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import ch.santosalves.jot.dtos.PreRegisteredApplication;
import ch.santosalves.jot.entities.PreRegisteredApplications;
import ch.santosalves.jot.repositories.LevelsRepository;
import ch.santosalves.jot.repositories.PreRegisteredApplicationsRepository;

@Service
@EntityScan(basePackageClasses = { ch.santosalves.jot.entities.Level.class, LevelsRepository.class })
public class PreRegisteredApplicationsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreRegisteredApplicationsService.class);

    @Autowired
    PreRegisteredApplicationsRepository prar;

    /**
     * 
     * @param email
     * @return
     */
    public PreRegisteredApplication addNew(PreRegisteredApplication applicant) {
        int pin = (int) (Math.random() * 10000);
        PreRegisteredApplications p = new PreRegisteredApplications();
        p.setEmail(applicant.getEmail());
        p.setFullName(applicant.getFullName());
        p.setPin("" + pin);
        java.sql.Timestamp tstp = new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000);
        p.setCreationDate(tstp);
        p.setSuccess(0);
        prar.save(p);

        return new PreRegisteredApplication(p);
    }

    public PreRegisteredApplication getByEmailAndPin(String email, String pin) {
        return new PreRegisteredApplication(prar.findOneByEmailAndPin(email, pin));
    }

    /**
     * When consumed, we have to set the consumption date to avoid other uses
     * 
     * @param email
     * @param pin
     */
    public void consumeApplication(String email, String pin) {
        PreRegisteredApplications p = prar.findOneByEmailAndPin(email, pin);
        p.setConsumptionDate(new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000));
        prar.save(p);
    }

    public void setAssessmentStatus(boolean success, String email, String pin) {
        PreRegisteredApplications p = prar.findOneByEmailAndPin(email, pin);
        p.setSuccess(success ? 1 : 0);
        prar.save(p);
    }
}
