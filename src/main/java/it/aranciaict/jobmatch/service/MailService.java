package it.aranciaict.jobmatch.service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import io.github.jhipster.config.JHipsterProperties;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;
import it.aranciaict.jobmatch.service.dto.email.AppliedJobEmailDTO;
import it.aranciaict.jobmatch.service.dto.email.PromoteJobOffersEmailDTO;
import it.aranciaict.jobmatch.service.dto.email.AbstractEmailDTO;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(MailService.class);

	/** The Constant USER. */
	private static final String USER = "user";

	/** The Constant EMAIL_OBJECT. */
	private static final String EMAIL_OBJECT = "obj";

	/** The Constant BASE_URL. */
	private static final String BASE_URL = "baseUrl";

	/** The j hipster properties. */
	private final JHipsterProperties jHipsterProperties;

	/** The java mail sender. */
	private final JavaMailSender javaMailSender;

	/** The message source. */
	private final MessageSource messageSource;

	/** The template engine. */
	private final SpringTemplateEngine templateEngine;

	/**
	 * Instantiates a new mail service.
	 *
	 * @param jHipsterProperties the j hipster properties
	 * @param javaMailSender     the java mail sender
	 * @param messageSource      the message source
	 * @param templateEngine     the template engine
	 */
	public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
			MessageSource messageSource, SpringTemplateEngine templateEngine) {

		this.jHipsterProperties = jHipsterProperties;
		this.javaMailSender = javaMailSender;
		this.messageSource = messageSource;
		this.templateEngine = templateEngine;
	}

	/**
	 * Send email.
	 *
	 * @param to         the to
	 * @param subject    the subject
	 * @param content    the content
	 * @param isHtml     the is html
	 * @param attachment the attachment
	 */
	@Async
	public void sendEmail(String to, String subject, String content, boolean isHtml, DocumentDTO attachment) {
		sendEmail(new String[] { to }, subject, content, attachment != null, isHtml, attachment);
	}

	/**
	 * Send email.
	 *
	 * @param to          the to
	 * @param subject     the subject
	 * @param content     the content
	 * @param isMultipart the is multipart
	 * @param isHtml      the is html
	 * @param attachment  the attachment
	 */
	@Async
	public void sendEmail(String[] to, String subject, String content, boolean isMultipart, boolean isHtml,
			DocumentDTO attachment) {
		log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart,
				isHtml, to, subject, content);

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart,
					StandardCharsets.UTF_8.name());

			if(to.length > 1) {
				message.setBcc(to);
			} else {
				message.setTo(to);
			}
			message.setFrom(jHipsterProperties.getMail().getFrom());
			message.setSubject(subject);
			message.setText(content, isHtml);
			if (attachment != null) {
				try {
					message.addAttachment(
							composeAttachmentName(attachment.getName(), attachment.getContentContentType()),
							new ByteArrayDataSource(attachment.getContent(), attachment.getContentContentType()));
				} catch (MessagingException e) {
					log.debug("Failed to add an attachment in email");
					e.printStackTrace();
				}
			}

			javaMailSender.send(mimeMessage);
			log.debug("Sent email to User '{}'", Arrays.toString(to));
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.warn("Email could not be sent to user '{}'", to, e);
			} else {
				log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
			}
		}
	}

	/**
	 * Send email from template.
	 *
	 * @param user         the user
	 * @param templateName the template name
	 * @param titleKey     the title key
	 */
	@Async
	public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
		Locale locale = Locale.forLanguageTag(user.getLangKey());
		Context context = new Context(locale);
		context.setVariable(USER, user);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process(templateName, context);
		String subject = messageSource.getMessage(titleKey, null, locale);
		sendEmail(user.getEmail(), subject, content, true, null);

	}

	/**
	 * Send email from template.
	 *
	 * @param              <T> the generic type
	 * @param obj          the obj
	 * @param templateName the template name
	 * @param titleKey     the title key
	 */
	@Async
	public <T extends AbstractEmailDTO> void sendEmailFromTemplateToSingleContact(T obj, String templateName, String titleKey) {
		Locale locale = Locale.forLanguageTag(obj.getDefaultLanguageKey());
		Context context = new Context(locale);
		context.setVariable(EMAIL_OBJECT, obj);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process(templateName, context);
		String subject = messageSource.getMessage(titleKey, null, locale);
		sendEmail(obj.getFirstContact().getEmail(), subject, content, true, obj.getAttachment());
	}

	/**
	 * Send email from template.
	 *
	 * @param              <T> the generic type
	 * @param obj          the obj
	 * @param templateName the template name
	 * @param titleKey     the title key
	 */
	@Async
	public <T extends AbstractEmailDTO> void sendEmailFromTemplate(T obj, String templateName, String titleKey) {
		Locale locale = Locale.forLanguageTag(obj.getDefaultLanguageKey());
		Context context = new Context(locale);
		context.setVariable(EMAIL_OBJECT, obj);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process(templateName, context);
		String subject = messageSource.getMessage(titleKey, null, locale);
		sendEmail(obj.getEmails().toArray(new String[obj.getEmails().size()]), subject, content, obj.getAttachment() != null, true, obj.getAttachment());
	}

	/**
	 * Send activation email.
	 *
	 * @param user the user
	 */
	@Async
	public void sendActivationEmail(User user) {
		log.debug("Sending activation email to '{}'", user.getEmail());
		sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
	}

	/**
	 * sendAppliedToOfferEmail.
	 *
	 * @param appliedEmailDTO the appliedEmailDTO
	 */
	@Async
	public void sendAppliedToOfferEmail(AppliedJobEmailDTO appliedEmailDTO) {
		log.debug("Sending Applied To Offer Email to '{}'", appliedEmailDTO.getFirstContact().getEmail());
		sendEmailFromTemplateToSingleContact(appliedEmailDTO, "mail/appliedToJobOffer", "email.appliedToJobOffer.title");
	}

	/**
	 * sendAppliedToCompanyEmail.
	 *
	 * @param appliedEmailDTO the appliedEmailDTO
	 */
	@Async
	public void sendAppliedToCompanyEmail(AppliedJobEmailDTO appliedEmailDTO) {
		log.debug("Sending Applied To Company Email to '{}'", appliedEmailDTO.getFirstContact().getEmail());
		sendEmailFromTemplateToSingleContact(appliedEmailDTO, "mail/appliedToCompany", "email.appliedToCompany.title");
	}

	/**
	 * sendPromoteJobOffersEmail.
	 *
	 * @param promoteJobOffersEmailDTO the promoteJobOffersEmailDTO
	 */
	@Async
	public void sendPromoteJobOffersEmail(PromoteJobOffersEmailDTO promoteJobOffersEmailDTO) {
		log.debug("Sending Promote Job Offers Email to '{}'", promoteJobOffersEmailDTO.getEmails());
		sendEmailFromTemplate(promoteJobOffersEmailDTO, "mail/promoteJobOffers", "email.promoteJobOffers.title");
	}

	/**
	 * Send creation email.
	 *
	 * @param user the user
	 */
	@Async
	public void sendCreationEmail(User user) {
		log.debug("Sending creation email to '{}'", user.getEmail());
		sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
	}

	/**
	 * Send password reset mail.
	 *
	 * @param user the user
	 */
	@Async
	public void sendPasswordResetMail(User user) {
		log.debug("Sending password reset email to '{}'", user.getEmail());
		sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
	}

	/**
	 * Compose attachment name.
	 *
	 * @param name     the name
	 * @param mimeType the mime type
	 * @return the string
	 */
	private String composeAttachmentName(String name, String mimeType) {
		if (mimeType.equals("application/pdf")) {
			return name.concat(".pdf");
		} else if (mimeType.equals("application/docx")) {
			return name.concat(".docx");
		}

		return name;
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @return the jHipsterProperties
	 */
	public JHipsterProperties getjHipsterProperties() {
		return jHipsterProperties;
	}

	/**
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @return the templateEngine
	 */
	public SpringTemplateEngine getTemplateEngine() {
		return templateEngine;
	}
}
