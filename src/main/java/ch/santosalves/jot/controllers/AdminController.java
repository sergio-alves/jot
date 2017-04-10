package ch.santosalves.jot.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.context.SpringWebContext;

import ch.santosalves.jot.dtos.Answer;
import ch.santosalves.jot.dtos.MultipleChoicesQuestion;
import ch.santosalves.jot.dtos.PreRegisteredApplication;
import ch.santosalves.jot.services.CategoriesService;
import ch.santosalves.jot.services.LevelsService;
import ch.santosalves.jot.services.McqService;
import ch.santosalves.jot.services.PreRegisteredApplicationsService;
import ch.santosalves.jot.services.QuestionnairesService;
import ch.santosalves.jot.services.SendmailService;

@Controller
@EnableAutoConfiguration
@RequestMapping(path = "/admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    McqService mcqS;

    @Autowired
    QuestionnairesService qs;

    @Autowired
    private CategoriesService cs;

    @Autowired
    private LevelsService ls;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private PreRegisteredApplicationsService preRegisteredApplicationsService;

    @Autowired
    private SendmailService sendmail;

    @GetMapping("/")
    public String getAllQuestions(HttpSession session, Model model) {
        return "pages/admin/index";
    }

    @GetMapping("/register-applicant")
    public String registerApplicant(HttpSession session, Model model) {
        model.addAttribute("applicant", new PreRegisteredApplication());
        return "pages/admin/register-applicant";
    }

    @PostMapping("/register-applicant")
    public String registerApplicant(HttpSession session, Model model, PreRegisteredApplication application) {
        PreRegisteredApplication applicant = preRegisteredApplicationsService.addNew(application);
        session.setAttribute("applicant-registered", applicant);
        return "redirect:/admin/applicant-registered";
    }

    @GetMapping("/applicant-registered")
    public String applicantRegistered(HttpSession session, Model model) {
        model.addAttribute("applicant", session.getAttribute("applicant-registered"));
        PreRegisteredApplication applicant = (PreRegisteredApplication) session.getAttribute("applicant-registered");
        sendmail.sendApplicationMail(applicant.getFullName(), applicant.getEmail(), applicant.getPin());
        session.removeAttribute("applicant-registered");
        return "pages/admin/applicant-registered";
    }

    @GetMapping("/questions/list")
    public ResponseEntity<List<MultipleChoicesQuestion>> getAllQuestions(HttpSession session) {
        LOGGER.debug("serving GET /admin/questions");
        // model.addAttribute("questions", mcqS.getAllQuestions());
        return new ResponseEntity<List<MultipleChoicesQuestion>>(mcqS.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/questions/new")
    public String getCreateNewQuestionFormular(HttpSession session, Model model) {
        LOGGER.debug("serving GET /admin/questions/new");
        MultipleChoicesQuestion multipleChoicesQuestion = new MultipleChoicesQuestion();
        multipleChoicesQuestion.getAnswers().add(new Answer());
        model.addAttribute("newquestion", multipleChoicesQuestion);
        return "admin/createquestion";
    }

    @PostMapping("/questions")
    public String addNewQuestion(HttpSession session, @ModelAttribute MultipleChoicesQuestion question)
            throws Exception {
        LOGGER.debug("serving POST /admin/questions");
        mcqS.addQuestion(question);

        return "redirect:/admin/questions/list";
    }

    @PostMapping("/questions/{id}/answers")
    public ResponseEntity<String> addNewQuestion(HttpSession session, @PathVariable(name = "id") Integer id,
            @ModelAttribute Answer answer) throws Exception {
        LOGGER.debug("serving POST /admin/questions/{id}/answers");
        mcqS.addNewAnswerToQuestion(answer, id);

        // return "redirect:/admin/questions/" + id;
        return ResponseEntity.ok("");
    }

    @GetMapping("/questions/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("question", mcqS.getOne(id));
        model.addAttribute("answer", new Answer());
        model.addAttribute("levelsList", ls.getLevelsList());
        model.addAttribute("categoriesList", cs.getCategories());

        return "pages/admin/edit-question";
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok("{\"message\":\"deleted\"}");
    }

    /**
     * Returns a html table with all questions. Coupled with java script
     * JSTable, allows very quick questions manipulation.
     * 
     * @param session
     * @param locale
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/questions")
    public ResponseEntity<String> getQuestions(final HttpSession session, final Locale locale,
            final HttpServletRequest request, final HttpServletResponse response) {

        LOGGER.debug("serving GET /admin/questions");
        Map<String, Object> variables = new HashMap<>();
        variables.put("questions", mcqS.getAllQuestions());
        IWebContext context = new SpringWebContext(request, response, servletContext, locale, variables,
                applicationContext);

        return ResponseEntity.ok(templateEngine.process("fragments/questions/list", context));
    }

    @GetMapping("/questionnaires")
    public ResponseEntity<String> getQuestionnaires(final HttpSession session, final Locale locale,
            final HttpServletRequest request, final HttpServletResponse response) {

        LOGGER.debug("serving GET /admin/questions");
        Map<String, Object> variables = new HashMap<>();
        variables.put("questionnaires", qs.getAllQuestionnaires());
        IWebContext context = new SpringWebContext(request, response, servletContext, locale, variables,
                applicationContext);

        return ResponseEntity.ok(templateEngine.process("fragments/questionnaires/list", context));
    }
}
