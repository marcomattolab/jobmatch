package it.aranciaict.jobmatch.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.spring5.SpringTemplateEngine;

import io.github.jhipster.config.JHipsterProperties;
import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.config.Constants;
import it.aranciaict.jobmatch.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class MailServiceIntTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class MailServiceIntTest {

    /** The j hipster properties. */
    @Autowired
    private JHipsterProperties jHipsterProperties;

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    /** The template engine. */
    @Autowired
    private SpringTemplateEngine templateEngine;

    /** The java mail sender. */
    @Spy
    private JavaMailSenderImpl javaMailSender;

    /** The message captor. */
    @Captor
    private ArgumentCaptor<MimeMessage> messageCaptor;

    /** The mail service. */
    private MailService mailService;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        mailService = new MailService(jHipsterProperties, javaMailSender, messageSource, templateEngine);
    }

    /**
     * Test send email.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendEmail() throws Exception {
        mailService.sendEmail("john.doe@example.com", "testSubject", "testContent", false, null);
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent()).isInstanceOf(String.class);
        assertThat(message.getContent().toString()).isEqualTo("testContent");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }

    /**
     * Test send html email.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendHtmlEmail() throws Exception {
        mailService.sendEmail("john.doe@example.com", "testSubject", "testContent", true, null);
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent()).isInstanceOf(String.class);
        assertThat(message.getContent().toString()).isEqualTo("testContent");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

//    /**
//     * Test send multipart email.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testSendMultipartEmail() throws Exception {
//        mailService.sendEmail("john.doe@example.com", "testSubject", "testContent", false, null);
//        verify(javaMailSender).send(messageCaptor.capture());
//        MimeMessage message = messageCaptor.getValue();
//        MimeMultipart mp = (MimeMultipart) message.getContent();
//        MimeBodyPart part = (MimeBodyPart) ((MimeMultipart) mp.getBodyPart(0).getContent()).getBodyPart(0);
//        ByteArrayOutputStream aos = new ByteArrayOutputStream();
//        part.writeTo(aos);
//        assertThat(message.getSubject()).isEqualTo("testSubject");
//        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
//        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
//        assertThat(message.getContent()).isInstanceOf(Multipart.class);
//        assertThat(aos.toString()).isEqualTo("\r\ntestContent");
//        assertThat(part.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
//    }

//    /**
//     * Test send multipart html email.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testSendMultipartHtmlEmail() throws Exception {
//        mailService.sendEmail("john.doe@example.com", "testSubject", "testContent", true, null);
//        verify(javaMailSender).send(messageCaptor.capture());
//        MimeMessage message = messageCaptor.getValue();
//        MimeMultipart mp = (MimeMultipart) message.getContent();
//        MimeBodyPart part = (MimeBodyPart) ((MimeMultipart) mp.getBodyPart(0).getContent()).getBodyPart(0);
//        ByteArrayOutputStream aos = new ByteArrayOutputStream();
//        part.writeTo(aos);
//        assertThat(message.getSubject()).isEqualTo("testSubject");
//        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
//        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
//        assertThat(message.getContent()).isInstanceOf(Multipart.class);
//        assertThat(aos.toString()).isEqualTo("\r\ntestContent");
//        assertThat(part.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
//    }

    /**
     * Test send email from template.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendEmailFromTemplate() throws Exception {
        User user = new User();
        user.setLogin("john");
        user.setEmail("john.doe@example.com");
        user.setLangKey("en");
        mailService.sendEmailFromTemplate(user, "mail/testEmail", "email.test.title");
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("test title");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent().toString()).isEqualToNormalizingNewlines("<html>test title, http://127.0.0.1:8080, john</html>\n");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    /**
     * Test send activation email.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendActivationEmail() throws Exception {
        User user = new User();
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setLogin("john");
        user.setEmail("john.doe@example.com");
        mailService.sendActivationEmail(user);
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    /**
     * Test creation email.
     *
     * @throws Exception the exception
     */
    @Test
    public void testCreationEmail() throws Exception {
        User user = new User();
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setLogin("john");
        user.setEmail("john.doe@example.com");
        mailService.sendCreationEmail(user);
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    /**
     * Test send password reset mail.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendPasswordResetMail() throws Exception {
        User user = new User();
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setLogin("john");
        user.setEmail("john.doe@example.com");
        mailService.sendPasswordResetMail(user);
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    /**
     * Test send email with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendEmailWithException() throws Exception {
        doThrow(MailSendException.class).when(javaMailSender).send(any(MimeMessage.class));
        mailService.sendEmail("john.doe@example.com", "testSubject", "testContent", false, null);
    }

}
