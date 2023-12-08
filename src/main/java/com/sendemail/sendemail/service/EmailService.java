package com.sendemail.sendemail.service;

public interface EmailService {

	void sendSimpleMailMessage(String name, String to, String token);
	void sendMimeMailMessageWithAttachments(String name, String to, String token);
	void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
	void sendHtmlEmail(String name, String to, String token);
	void sendHtmlEmailWithEmbeddedFIles(String name, String to, String token);
}
