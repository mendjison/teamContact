package de.computacenter.team.contact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import de.computacenter.team.contact.model.entities.Contact;

public interface IContactController {

	ResponseEntity<List<Contact>> findContacts();
	ResponseEntity<Contact> getContact(String contactId);
	ResponseEntity<Contact> saveContact(Contact contact);
	ResponseEntity<Contact> updateContact(String contactId, Contact contact);
	ResponseEntity<Boolean>  deleteContact(String contactId);
	ResponseEntity<Contact> getContractByEmail(String email);
	ResponseEntity<List<Contact>> getContractByFirstname(String firstname);
	ResponseEntity<List<Contact>> getContractByLastname(String lastname);
	ResponseEntity<Contact> getContractByFirstnameAndLastname(String firstname, String lastname);
	
}
