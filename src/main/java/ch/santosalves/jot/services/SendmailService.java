package ch.santosalves.jot.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ch.santosalves.jot.controllers.JotController.ResponseAnswer;
import ch.santosalves.jot.dtos.JotSession;

@Service
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class SendmailService {
    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Value("${mail.user}")
    private String smtpUsername;

    @Value("${mail.password}")
    private String smtpPassword;

    @Value("${mail.smtp.from}")
    private String smtpFrom;

    @Value("${jot.fullhost}")
    private String jotHostName;

    @Value("${jot.pin.validity.days}")
    private int pinValidity;

    @Value("${jot.hr.distribution.mail}")
    private String hrEmail;

    public void sendResultsEmail(JotSession session, boolean success, int right, double average, List<ResponseAnswer> raList) {
        // Recipient's email ID needs to be mentioned.
        String subject = "Congratulations, you successfuly achieved the JOT assessment.";

        String fullname = session.getFullName();
        String email = session.getEmail();
        String pin = "" + session.getPin();
        List<String> asList = Arrays.asList(email);
        asList.addAll(Arrays.asList(hrEmail.split(",")));
        
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Dear " + fullname + ",</p>");
        sb.append("<p>");
        if(success) {
            sb.append("We inform you that you successfuly achieved the assessment.<br/>Shortly we will come back to you with the next step.");
        }else{
            sb.append("We inform that you, unfortunately, <b>didn't achieved</b> the assessment.<br/> Below you test results.");
            sb.append("<p>You answered correctly to "+right+" questions over " + session.getAnswers().size() + " available.</p>");
//            sb.append("<p><table><tr><th>Question</th><th>Possible Answers</th><th>Your Answer</th><th>Expected Answer</th></tr>");
//            raList.forEach(res->sb.append("<tr><td>"+res.getQuestion()+"</td><td>"+res.getResponses().stream().collect(Collectors.joining("<br>"))+"</td><td>"+res.getResponse()+"</td><td>"+res.getExplanation()+"</td></tr>"));            
//            sb.append("</table></p>");
        }
        sb.append("</p>");
        sb.append("<p>Best regards,</p>");
        sb.append("<p>Utopix Team</p>");
        sb.append("</p>");
        

        sendHtmlEmail(fullname, asList, subject, pin, sb.toString());
    }

    public void sendApplicationMail(String fullname, String email, String pin) {
        // Recipient's email ID needs to be mentioned.
        List<String> asList = Arrays.asList(email);
        asList.addAll(Arrays.asList(hrEmail.split(",")));
        
        String subject = "Self-assessment Session created";

        StringBuilder sb = new StringBuilder();
        sb.append("<p>Dear " + fullname + ",</p>");
        sb.append("<p>");
        sb.append("A self-assessment session was created for you on our Java Online Test environment.<br/>");
        sb.append("Please go to <a href=\"" + jotHostName
                + "/jot/\" >JOT environment authentication page</a> and input your email and the pin (see below)<br/>");
        sb.append("</p>");
        sb.append("<p>");
        sb.append("Email : " + email + "<br/>");
        sb.append("Pin : " + pin + "<br/>");
        sb.append("</p>");
        sb.append("<p>");
        sb.append("Please not this assessment is time limited: you have " + (pinValidity > 1 ? pinValidity + " days" : pinValidity + " day") + " to complete the test upon reception of this email.");
        sb.append("<p>Best regards,</p>");
        sb.append("<p>Utopix Team</p>");
        sb.append("</p>");

        sendHtmlEmail(fullname, asList, subject, pin, sb.toString());
    }

    private void sendHtmlEmail(String fullname, List<String> to, String subject, String pin, String content) {

        // Sender's email ID needs to be mentioned
        String from = smtpFrom;

        // Assuming you are sending email from localhost
        String host = smtpHost;

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", smtpUsername);
        properties.setProperty("mail.password", smtpPassword);
        //properties.setProperty("mail.smtp.starttls.enable", "true");
        //properties.setProperty("mail.smtp.auth", "true");
        //properties.setProperty("mail.smtp.socketFactory.port", "587");
        //properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.setProperty("mail.smtp.socketFactory.fallback", "false");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            to.stream().forEach(toM -> {
                try {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(toM));
                } catch (AddressException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (MessagingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(content, "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
