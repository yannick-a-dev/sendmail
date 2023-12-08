package com.sendemail.sendemail.controlleur;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sendemail.sendemail.entities.HttpResponse;
import com.sendemail.sendemail.entities.User;
import com.sendemail.sendemail.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControlleur {

	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<HttpResponse> createUser(@RequestBody User user){
		User newUser = userService.saveUser(user);
		return ResponseEntity.created(URI.create("")).body(
				  HttpResponse.builder()
				     .timeStamp(LocalDateTime.now().toString())
				     .data(Map.of("user", newUser))
				     .message("User created")
				     .status(HttpStatus.CREATED)
				     .statusCode(HttpStatus.CREATED.value())
				     .build()
				);
	}
	
	@GetMapping
	public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam("token") String token){
		Boolean isSuccess = userService.verifyToken(token);
		return ResponseEntity.ok().body(
				  HttpResponse.builder()
				     .timeStamp(LocalDateTime.now().toString())
				     .data(Map.of("Success", isSuccess))
				     .message("Account Verified")
				     .status(HttpStatus.OK)
				     .statusCode(HttpStatus.OK.value())
				     .build()
				);
	}
}
