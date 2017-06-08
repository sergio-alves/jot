package ch.santosalves.jot.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring4.SpringTemplateEngine;

import ch.santosalves.jot.dtos.Answer;
import ch.santosalves.jot.dtos.JotSession;
import ch.santosalves.jot.dtos.JotSession.LoginModel;
import ch.santosalves.jot.dtos.LoggedSession;
import ch.santosalves.jot.dtos.MultipleChoicesQuestion;
import ch.santosalves.jot.dtos.PreRegisteredApplication;
import ch.santosalves.jot.dtos.QuestionAnswer;
import ch.santosalves.jot.dtos.Questionnaire;
import ch.santosalves.jot.entities.LogSession;
import ch.santosalves.jot.repositories.McqAnswerRepository;
import ch.santosalves.jot.services.LevelsService;
import ch.santosalves.jot.services.LogSessionsService;
import ch.santosalves.jot.services.PreRegisteredApplicationsService;
import ch.santosalves.jot.services.QuestionnairesService;
import ch.santosalves.jot.services.SendmailService;

/**
 * The JOT Controller. All pages should be public.
 * 
 * @author Sergio
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping(path = "/jot")
@PropertySource("classpath:application.properties")
public class JotController {
    private static final double SUCCESS_THRESHOLD = 0.7;

    private static final Logger LOGGER = LoggerFactory.getLogger(JotController.class);

    @Autowired
    LevelsService ls;

    @Autowired
    QuestionnairesService qs;

    @Autowired
    McqAnswerRepository as;
    
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private LogSessionsService lss;

    @Autowired
    private PreRegisteredApplicationsService pras;

    @Autowired
    private SendmailService sendmail;

    @Value("${jot.cvs.repository}")
    private String repositoryPath;

    @Value("${jot.default.questionnaire.id}")
    private int defaultQuestionnaireId;

    @Value("${jot.login.model}")
    private LoginModel loginModel;

    /**
     * The main page, jot/ is the starting point for any assessment attempt.
     * 
     * @param model
     * @param session
     * 
     * @return
     */
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("levels", ls.getLevelsList());
        JotSession s = new JotSession();
        s.setLoginModel(loginModel);
        model.addAttribute("jotSession", s);
        model.addAttribute("loginModel", s.getLoginModel().ordinal());
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(HttpSession sesion, Model model) {
        return "pages/login";
    }

    /**
     * The page called after authentication (email)
     * 
     * @param model
     * @param session
     * @param jotSession
     * @param request
     * @return
     */
    @PostMapping("/")
    public String beforeMcq(Model model, HttpSession session, @ModelAttribute JotSession jotSession,
            final HttpServletRequest request) {
        session.setAttribute("jotSession", jotSession);

        // Get IP address
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        if (loginModel.equals(LoginModel.PIN)) {
            PreRegisteredApplication p = pras.getByEmailAndPin(jotSession.getEmail(), "" + jotSession.getPin());

            if (p == null) {
                // ERROR - not registered
                StringBuilder content = new StringBuilder("<p>Questionnaire not valid</p>");
                content.append("<p>Dear Applicant,<br />The pin/email provided is wrong.</p>");
                model.addAttribute("errorcontent", content.toString());
            } else {
                jotSession.setFullName(p.getFullName());
                if (p.isValid()) {

                    // defaultQuestionnaireId
                    Questionnaire questionnaire = qs.getQuestionnaireById(defaultQuestionnaireId);

                    jotSession.setQuestionnaireId(defaultQuestionnaireId);

                    LogSession ls = new LogSession();
                    ls.setEmail(jotSession.getEmail());
                    ls.setIp(ipAddress);
                    ls.setLevel(questionnaire.getLevel().getId());
                    ls.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
                    lss.persist(ls);

                    // Consume the application
                    pras.consumeApplication(jotSession.getEmail(), "" + jotSession.getPin());

                    return "redirect:/jot/mcq";

                } else {
                    // ERROR - Questionnaire not valid
                    StringBuilder content = new StringBuilder("<p>Questionnaire not valid</p>");
                    content.append("<p>");
                    content.append("Dear Applicant,<br />");
                    content.append(
                            "The pin/email provided has already been used in a previous assessment attempt <b>or</b> your take too long to pass the assessment.<br/>");
                    content.append("After pin generation, you have 7 days to pass the assessment.");
                    content.append("</p>");

                    model.addAttribute("errorcontent", content.toString());
                }
            }

            return "pages/error";

        } else {
            LOGGER.info("email = {}", jotSession.getEmail());

            // initialize questionnaire and update jotSession object setting
            // questionnaire id
            List<Questionnaire> questionnaires = qs.getQuestionnairesForLevel(jotSession.getLevel());

            if (questionnaires.size() == 0) {
                return "redirect:/jot/no-questionnaires";
            }

            Questionnaire questionnaire = selectRandomQuestionnaireFromLevel(questionnaires);
            jotSession.setQuestionnaireId(questionnaire.getId());

            List<LoggedSession> existingSessions = lss.findByIpOrEmail(ipAddress, jotSession.getEmail());

            if (existingSessions.size() > 0) {
                return "pages/too-much-connections";
            } else {
                LogSession ls = new LogSession();
                ls.setEmail(jotSession.getEmail());
                ls.setIp(ipAddress);
                ls.setLevel(jotSession.getLevel());
                ls.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
                lss.persist(ls);

                return "redirect:/jot/mcq";
            }
        }
    }

    @RequestMapping("/mcq")
    public String mcq(Model model, HttpSession session, @ModelAttribute QuestionAnswer questionAnswer) {
        JotSession jotSession = (JotSession) session.getAttribute("jotSession");

        if (!isAuthorized(session)) {
            return "redirect:/jot/";
        }

        // Load current questionnaire
        Questionnaire questionnaire = qs.getQuestionnaireById(jotSession.getQuestionnaireId());

        // Add response to the OJT session object
        jotSession.getAnswers().put(questionAnswer.getQuestionId(), questionAnswer.getAnswers());

        LOGGER.info("The user " + jotSession.getFullName() + " answered " + questionAnswer.getAnswers().stream().map(mapper-> "ID : "+ mapper +" " + as.findOne(mapper).getResource().getContent()).collect(Collectors.joining(", ")) + " for the question id " + questionAnswer.getQuestionId());

        if (questionnaire.getQuestionsCount() > jotSession.getCurrentQuestionNumber()) {
            // Get next question
            MultipleChoicesQuestion mcq = putQuestionToModelAndIncrementQuestionsCounter(jotSession, questionnaire);

            LOGGER.info("Going to display question : {}", mcq.getQuestion());
            model.addAttribute("mcqQuestion", mcq.getQuestion());
            model.addAttribute("mcqId", mcq.getId());
            model.addAttribute("mcqAnswers", mcq.getAnswers());
            model.addAttribute("mcqSecondsToAnswer", mcq.getSecondsToAnswer());

            // update session object
            jotSession.setQuestionnaireId(questionnaire.getId());

            model.addAttribute("questionsCount", questionnaire.getQuestionsCount());
            model.addAttribute("questionnaireId", questionnaire.getId());

            QuestionAnswer qa = new QuestionAnswer();
            qa.setQuestionId(mcq.getId());
            model.addAttribute("questionAnswer", qa);
            model.addAttribute("currentQuestionNumber", jotSession.getCurrentQuestionNumber());
        } else {
            return "redirect:summary";
        }

        return "pages/mcq";
    }

    public static class ResponseAnswer {
    	private String question;
    	private List<String> responses;
    	private String response;
    	private String explanation;
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public List<String> getResponses() {
			return responses;
		}
		public void setResponses(List<String> responses) {
			this.responses = responses;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
		public String getExplanation() {
			return explanation;
		}
		public void setExplanation(String explanation) {
			this.explanation = explanation;
		}
    }
    
    @RequestMapping("/summary")
    public String summary(Model model, HttpSession session) {
        JotSession jotSession = (JotSession) session.getAttribute("jotSession");
        
        List<ResponseAnswer> raList = new ArrayList<>();
        
        if (!isAuthorized(session)) {
            return "redirect:/jot/";
        }

        // Load current questionnaire
        Questionnaire questionnaire = qs.getQuestionnaireById(jotSession.getQuestionnaireId());

        MultipleChoicesQuestion mcq = null;
        int right = 0;
        ResponseAnswer ra = new ResponseAnswer();
        for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
            mcq = questionnaire.getQuestions().get(i);

            ra.setQuestion(mcq.getQuestion());
            ra.setExplanation(mcq.getExplanation());
            // for each question in questionnaire

            // get list of answered responses for the question
            List<Integer> answer = jotSession.getAnswers().get(mcq.getId());
            List<Integer> realAnsers = new ArrayList<>();

            
            Optional<Answer> optAnswer = mcq.getAnswers().stream().filter(predicate->predicate.getAnswerId()==answer.get(0)).findFirst();
            
            ra.setResponse(optAnswer.isPresent() ? optAnswer.get().getAnswer() : "Answer not found");
            
            List<String> answersList = mcq.getAnswers().stream().map(a->a.getAnswer()).collect(Collectors.toList());
            ra.setResponses(answersList);

            raList.add(ra);
            
            mcq.getAnswers().forEach(a -> {
                if (a.getIsRightAnswer()) {
                    realAnsers.add(a.getAnswerId());
                }
            });

            if (!answer.isEmpty()) {
                Collections.sort(answer);
                Collections.sort(realAnsers);

                if (answer.equals(realAnsers)) {
                    // all answers are right
                    right++;
                }
            }
        }

        
        
        
        boolean succeed = (right) > SUCCESS_THRESHOLD * questionnaire.getQuestionsCount();

        model.addAttribute("loginModel", loginModel.ordinal());
        model.addAttribute("successThreshold", SUCCESS_THRESHOLD);
        model.addAttribute("succeed", succeed ? 1 : 0);
        model.addAttribute("numberOfAnswersRight", right);
        model.addAttribute("numberOfQuestions", questionnaire.getQuestionsCount());

        if (loginModel.equals(LoginModel.PIN)) {
            pras.setAssessmentStatus(succeed, jotSession.getEmail(), "" + jotSession.getPin());
            sendmail.sendResultsEmail(jotSession, succeed, right, SUCCESS_THRESHOLD * questionnaire.getQuestionsCount(), raList);
        }

        return "pages/summary";
    }

    @RequestMapping("/submit-cv")
    public String summary(Model model, HttpSession session, MultipartFile file) {
        if (!isAuthorized(session)) {
            return "redirect:/jot/";
        }

        try {
            // Save cv to directory
            Path p = Paths.get(repositoryPath);
            p.resolve(file.getOriginalFilename());
            Streams.copy(file.getInputStream(), new FileOutputStream(p.toFile()), true);
            // Send an email
            // TODO: Sendemail
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errors", "Could not transfert correctly the CV.");
        }

        return "pages/submit-cv";
    }

    @GetMapping("/no-questionnaires")
    public String noQuestionnaire(HttpSession sesion, Model model) {
        return "pages/no-questionnaire";
    }

    private boolean isAuthorized(HttpSession session) {
        JotSession jotSession = (JotSession) session.getAttribute("jotSession");
        return jotSession != null && !jotSession.getEmail().isEmpty();
    }

    private Questionnaire selectRandomQuestionnaireFromLevel(List<Questionnaire> questionnaires) {
        Random r = new Random(System.nanoTime());
        Questionnaire questionnaire = questionnaires.get(r.nextInt(questionnaires.size()));
        return questionnaire;
    }

    private MultipleChoicesQuestion putQuestionToModelAndIncrementQuestionsCounter(JotSession session,
            Questionnaire questionnaire) {
        MultipleChoicesQuestion mcq = questionnaire.getQuestions().get(session.getCurrentQuestionNumber());
        session.setCurrentQuestionNumber(session.getCurrentQuestionNumber() + 1);
        return mcq;
    }
}
