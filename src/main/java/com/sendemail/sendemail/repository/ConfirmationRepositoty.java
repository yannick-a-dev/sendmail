package com.sendemail.sendemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sendemail.sendemail.entities.Confirmation;

@Repository
public interface ConfirmationRepositoty extends JpaRepository<Confirmation, Long> {

	Confirmation findByToken(String token);
}
