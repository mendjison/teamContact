package de.computacenter.team.contact.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.computacenter.team.contact.model.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>{

	Optional<Contact> findByContactId(String contactId);
	List<Contact> findByFirstname(String firstname);
	List<Contact> findByLastname(String lastname);
	Optional<Contact>  findByFirstnameAndLastname(String firstname, String lastname);
	Optional<Contact>  findByEmail(String email);
	Optional<Contact>  findByPhone(String phone);
} 
