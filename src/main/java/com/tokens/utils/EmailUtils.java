package com.tokens.utils;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class EmailUtils {

	@Value("${smtpHostName}")
	static String smtpHostName;
	
	@Value("${smtpPort}")
	static String smtpPort;

	@Value("${fromEmailAddress}")
	static String fromEmailAddress;
	
	@Value("${fromEmailAddressPassword}")
	static String fromEmailAddressPassword;
	
	@Value("${toEmailAddress}")
	static String toEmailAddress;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	
	public static int sendAlertEmail(String alertLevel, String subject, String emailBody) {

		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		try {
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmailAddress, fromEmailAddressPassword);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromEmailAddress);
			message.addRecipients(javax.mail.Message.RecipientType.TO, toEmailAddress);
			message.setSubject(alertLevel+"1" + subject);
			message.setText(emailBody);

			Transport.send(message);
			logger.info("email sent successfully");

		} 
		catch (MessagingException ex) {
			logger.error("Exception while sending an email " + ex.getMessage());
		}
		catch (Exception ex) {
			logger.error("Exception while sending email" + ex.getMessage());
		}
		return 0;

	}
}
