package com.sendemail.sendemail.service.impl;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sendemail.sendemail.service.EmailService;
import com.sendemail.sendemail.utils.EmailUtils;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private static final String TEXT_HTML_ENCODING = "text/html";
	private static final String EMAIL_TEMPLATE = "emailtemplate";
	private static final String UTF_8_ENCODING = "UTF-8";
	private static final String NEW_USER_ACCOUNT_VERIFICATION = null;
    private final TemplateEngine templateEngine;
    
	@Value("${spring.mail.verify.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String fromEmail;
	private final JavaMailSender emailSender;

	@Override
	@Async
	public void sendSimpleMailMessage(String name, String to, String token) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setText(EmailUtils.getEmailMessage(name, host, token));
			emailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	@Async
	public void sendMimeMailMessageWithAttachments(String name, String to, String token) {
		try {
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(EmailUtils.getEmailMessage(name, host, token));
			// Add attachments
			FileSystemResource fort = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource dog = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource homework = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			helper.addAttachment(fort.getFilename(), fort);
			helper.addAttachment(dog.getFilename(), dog);
			helper.addAttachment(homework.getFilename(), homework);
			emailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	@Async
	public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
		try {
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(EmailUtils.getEmailMessage(name, host, token));
			// Add attachments
			FileSystemResource fort = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource dog = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource homework = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			helper.addInline(getContentId(fort.getFilename()), fort);
			helper.addInline(getContentId(dog.getFilename()), dog);
			helper.addInline(getContentId(homework.getFilename()), homework);
			emailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	@Async
	public void sendHtmlEmail(String name, String to, String token) {
		try {
			Context context = new Context();
			/*context.setVariable("name", name);
			context.setVariable("url", EmailUtils.getVerificationUrl(host, token));
			*/
			context.setVariables(Map.of("name", name, "url", EmailUtils.getVerificationUrl(host, token)));
			String text = templateEngine.process(EMAIL_TEMPLATE, context);
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(text, true);
			FileSystemResource fort = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource dog = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource homework = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			helper.addInline(getContentId(fort.getFilename()), fort);
			helper.addInline(getContentId(dog.getFilename()), dog);
			helper.addInline(getContentId(homework.getFilename()), homework);
			emailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	@Async
	public void sendHtmlEmailWithEmbeddedFIles(String name, String to, String token) {
		
		try {
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			//helper.setText(text, true);
			Context context = new Context();
			context.setVariables(Map.of("name", name, "url", EmailUtils.getVerificationUrl(host, token)));
			String text = templateEngine.process(EMAIL_TEMPLATE, context);
			/*FileSystemResource fort = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource dog = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			FileSystemResource homework = new FileSystemResource(
					new File(System.getProperty("user.home") + "/Downloads/images/1.jpg"));
			helper.addInline(getContentId(fort.getFilename()), fort);
			helper.addInline(getContentId(dog.getFilename()), dog);
			helper.addInline(getContentId(homework.getFilename()), homework);
			*/
			// add HTML email body
			MimeMultipart mimeMultipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(text, TEXT_HTML_ENCODING);
			mimeMultipart.addBodyPart(messageBodyPart);
			
			//add images to the email body
			BodyPart imageBodyPart = new MimeBodyPart();
			DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/images/1.jpg");
			imageBodyPart.setDataHandler(new DataHandler(dataSource));
			imageBodyPart.setHeader("Content-ID", "image");
			mimeMultipart.addBodyPart(imageBodyPart);
			message.setContent(mimeMultipart);
			emailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	private MimeMessage getMimeMessage() {
		return emailSender.createMimeMessage();
	}

	private String getContentId(String filename) {
		return "<" + filename + ">";
	}
}
