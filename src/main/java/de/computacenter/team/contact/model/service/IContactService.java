package de.computacenter.team.contact.model.service;

import java.util.List;

import de.computacenter.team.contact.model.entities.Contact;

public interface IContactService {

	Contact saveContact(Contact contact);
	Contact updateContact(String contactId, Contact contact);
	void deleteContact(String contactId);
	List<Contact> findContacts();
	Contact findContactById(String contactId);
	Contact findContactByEmail(String email);
	Contact findContactByPhone(String phone);
	List<Contact> findContactsByFirstname(String firstname);
	List<Contact> findContactsByLastname(String lastname);
	Contact findContactsByFirstnameAndLastname(String firstname, String lastname);
}
