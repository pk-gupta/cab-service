package com.marlabs.cab.service.common.email;

import java.util.concurrent.Future;

public interface EmailService {
	
	public Future<String> sendSimpleMessage(String to, String subject, String text);
	
	//public Future<String> sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template);
	
	public Future<String> sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
}
