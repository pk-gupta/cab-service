package com.marlabs.cab.service.common.email;

import java.io.File;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.marlabs.cab.service.common.constant.Constants;

@Service
public class EmailServiceImpl  implements EmailService {
	
		private static Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	
		@Autowired
		private JavaMailSender emailSender;

		@Async
		@Override
	    public Future<String> sendSimpleMessage(String to, String subject, String text) {
	    	logger.info("Sending E-mail to " + to );
	    	String emailStatus = null;
			try {
	
				MimeMessage message = emailSender.createMimeMessage();
			//	message.setContent(someHtmlMessage, "text/html; charset=utf-8");
				message.setFrom(new InternetAddress("mfleet-noreply@marlabs.com"));
				MimeMessageHelper helper;
				helper = new MimeMessageHelper(message);
				helper.setSubject(subject);
				helper.setTo(to);
				helper.setText(text,true);
				emailSender.send(message);
				emailStatus = Constants.EMAIL_STATUS_SUCCESS;
		        logger.info("Sending E-mail : " + emailStatus);
			} catch (MessagingException | MailException exception) {
				emailStatus = Constants.EMAIL_STATUS_FAILED;
				logger.info("Sending E-mail : FAILED ->" + exception.getMessage() + " due to " + exception.getCause());
			}

	        return new AsyncResult<>(emailStatus);
	    }

	   /* 
	    @Async
	    @Override
	    public Future<String> sendSimpleMessageUsingTemplate(String to, String subject,SimpleMailMessage template) {
	        String text = String.format(template.getText());  
	        sendSimpleMessage(to, subject, text);
	    }*/

		@Async
	    @Override
	    public Future<String> sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
			logger.info("Sending E-mail with Attachment to " + to );
			String emailStatus = null;
	        try {
	            MimeMessage message = emailSender.createMimeMessage();
	            // pass 'true' to the constructor to create a multipart message
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);

	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(text);

	            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
	            helper.addAttachment("Attachment", file);

	            emailSender.send(message);
	            emailStatus = Constants.EMAIL_STATUS_SUCCESS;
	            logger.info("Sending E-mail with Attachment : " + emailStatus);
	            
	        } catch (MessagingException | MailException exception) {
	        	emailStatus = Constants.EMAIL_STATUS_FAILED;
	        	logger.info("Sending E-mail with Attachment : FAILED ->" +  exception.getMessage()  + " due to " + exception.getCause());
	        	//throw new EmailNotificationException("E-Mail notification failed for " + to + " due to " + exception.getCause());
	        }
	        
	        return new AsyncResult<>(emailStatus);
	    }

}
