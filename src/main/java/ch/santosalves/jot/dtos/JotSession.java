package ch.santosalves.jot.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JotSession {
    private String email;
    private Integer level = 3;
    private Integer currentQuestionNumber = 0;
    private Map<Integer, List<Integer>> answers = new HashMap<>();
    private Integer questionnaireId = null;
    private int pin;
    private LoginModel loginModel;
    private String fullName;

    public enum LoginModel {
        USER_PASSWORD, PIN
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(Integer currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }

    public Map<Integer, List<Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, List<Integer>> answers) {
        this.answers = answers;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
