package de.computacenter.team.contact.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.repository.ContactRepository;
import de.computacenter.team.contact.model.service.impl.ContactService;


public class UpdateContact {

	@Mock
	private ContactRepository mockRepository;

	@InjectMocks
	private ContactService contactService;

	@BeforeEach
	public void setUps() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void updateContacts_Basic() {

		//given
		String contactId = UUID.randomUUID().toString();
		Contact expected = new Contact("Testmann", "Tester", "t.tester@outlook.com", "004917635351634");

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(expected));

		when(mockRepository.findByEmail(expected.getEmail()))
		.thenReturn(Optional.ofNullable(null));
		when(mockRepository.findByPhone(expected.getPhone()))
		.thenReturn(Optional.ofNullable(null));
		when(mockRepository.findByFirstnameAndLastname(expected.getFirstname(), expected.getLastname()))
		.thenReturn(Optional.ofNullable(null));
		when(mockRepository.save(expected)).thenReturn(expected);
		//When 
		Contact actual = contactService.updateContact(contactId, expected); 
		//Then
		assertEquals(expected, actual);
	}

	@Test
	public void updateContacts_when_contact_not_exist() {

		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", "t.tester@outlook.com", "004917635351634");
		contact.setContactId(contactId);

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(null));

		//When 
		RuntimeException  exception = assertThrows(RuntimeException.class, () -> 
		contactService.updateContact(contactId, contact)); 
		String expected = ErrorConstantValue.CONTACT_NOT_EXIST;
		//Then
		assertEquals(expected, exception.getMessage());
	}

	@Test
	public void updateContacts_when_contact_already_exist_with_email() {

		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		contact.setContactId(contactId);

		Contact existedContact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		existedContact.setContactId("6864sd5f3ef45re4f5");

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(contact));

		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(existedContact));
		
		//When 
		RuntimeException  exception = assertThrows(RuntimeException.class, () -> 
		contactService.updateContact(contactId, contact)); 
		String expected = ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_EMAIL; 
		
		//Then
		assertEquals(expected, exception.getMessage());
	}
	
	@Test
	public void updateContacts_when_contact_already_exist_with_fullName() {

		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		contact.setContactId(contactId);

		Contact existedContact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		existedContact.setContactId("6864sd5f3ef45re4f5");

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(contact));

		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(null));
		
		when(mockRepository.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname()))
		.thenReturn(Optional.ofNullable(existedContact));
		
		//When 
		RuntimeException  exception = assertThrows(RuntimeException.class, () -> 
		contactService.updateContact(contactId, contact)); 
		String expected = ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_NAME; 
		
		//Then
		assertEquals(expected, exception.getMessage());
	}
	
	@Test
	public void updateContacts_when_contact_already_exist_with_Phone() {

		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		contact.setContactId(contactId);

		Contact existedContact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		existedContact.setContactId("6864sd5f3ef45re4f5");

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(contact));

		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(null));
		
		when(mockRepository.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname()))
		.thenReturn(Optional.ofNullable(null));
		
		when(mockRepository.findByPhone(contact.getPhone()))
		.thenReturn(Optional.ofNullable(existedContact));
		
		//When 
		RuntimeException  exception = assertThrows(RuntimeException.class, () -> 
		contactService.updateContact(contactId, contact)); 
		String expected = ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_PHONE; 
		
		//Then
		assertEquals(expected, exception.getMessage());
	}
}
