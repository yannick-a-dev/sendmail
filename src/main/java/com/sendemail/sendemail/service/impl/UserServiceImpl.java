package com.sendemail.sendemail.service.impl;

import org.springframework.stereotype.Service;

import com.sendemail.sendemail.entities.Confirmation;
import com.sendemail.sendemail.entities.User;
import com.sendemail.sendemail.repository.ConfirmationRepositoty;
import com.sendemail.sendemail.repository.UserRepository;
import com.sendemail.sendemail.service.EmailService;
import com.sendemail.sendemail.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ConfirmationRepositoty confirmationRepositoty;
    private final EmailService emailService;
	@Override
	public User saveUser(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		user.setEnabled(false);
		userRepository.save(user);
		
		Confirmation confirmation = new Confirmation(user);
		confirmationRepositoty.save(confirmation);
		
		/*TODO send email to user with token*/
		emailService.sendMimeMailMessageWithAttachments(user.getName(), user.getEmail(), confirmation.getToken());
		//emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), confirmation.getToken());
		emailService.sendMimeMessageWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken());
		emailService.sendHtmlEmail(user.getName(), user.getEmail(), confirmation.getToken());
		emailService.sendHtmlEmailWithEmbeddedFIles(user.getName(), user.getEmail(), confirmation.getToken());
		return user;
	}

	@Override
	public Boolean verifyToken(String token) {
		Confirmation confirmation = confirmationRepositoty.findByToken(token);
		User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
		user.setEnabled(true);
		userRepository.save(user);
		//confirmationRepository.delete(confirmation);
		return Boolean.TRUE;
	}

}
