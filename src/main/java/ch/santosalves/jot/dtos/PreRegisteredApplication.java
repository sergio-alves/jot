package ch.santosalves.jot.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import ch.santosalves.jot.entities.PreRegisteredApplications;

public class PreRegisteredApplication {
    private String email;
    private String pin;
    private String fullName;
    private LocalDateTime creationDateTime;
    private LocalDateTime consumptionDateTime;

    public PreRegisteredApplication() {
        // TODO Auto-generated constructor stub
    }

    public PreRegisteredApplication(PreRegisteredApplications applicant) {
        email = applicant.getEmail();
        pin = applicant.getPin();
        fullName = applicant.getFullName();
        creationDateTime = LocalDateTime.ofEpochSecond(applicant.getCreationDate().getTime() / 1000, 0, ZoneOffset.UTC);
        if (applicant.getConsumptionDate() != null) {
            consumptionDateTime = LocalDateTime.ofEpochSecond(applicant.getConsumptionDate().getTime() / 1000, 0,
                    ZoneOffset.UTC);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public boolean isValid() {
        LocalDateTime minusDays = LocalDateTime.now().minusDays(7);
        boolean isCreationDateNotTooOld = creationDateTime.compareTo(minusDays) > 0;
        boolean isConsumptionDateNull = this.consumptionDateTime == null;

        return isConsumptionDateNull && isCreationDateNotTooOld;
    }
}
