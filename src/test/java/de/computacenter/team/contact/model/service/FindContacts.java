package de.computacenter.team.contact.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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


public class FindContacts {

	@Mock
	private ContactRepository mockRepository;

	@InjectMocks
	private ContactService contactService;

	@BeforeEach
	public void setUps() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindContacts_Basic() {

		//given
		when(mockRepository.findAll()).thenReturn(new ArrayList<Contact>());
		//When 
		List<Contact> result = contactService.findContacts(); 
		//Then
		assertEquals(0, result.size());

	}

	@Test
	public void testFindContacts_When_data_exist() {

		//given
		List<Contact> contacts = new ArrayList<Contact>() ;
		Contact contact = new Contact("Testmann", "Tester", "t.tester@outlook.com", "004917635351634");
		contacts.add(contact);
		when(mockRepository.findAll()).thenReturn(contacts);
		//When 
		List<Contact> result = contactService.findContacts(); 
		//Then
		assertEquals(1, result.size());
		assertTrue(result.contains(contact));
		assertEquals(contact, result.get(0));

	}

	@Test
	public void testFindContact_basic() {

		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(contact));
		//When 
		Contact actuel = contactService.findContactById(contactId); 
		//Then

		assertEquals(contact, actuel);
	}

	@Test
	public void testFindContact_when_contact_not_exist() {

		//given
		String contactId = UUID.randomUUID().toString();

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(null));
		String expect = ErrorConstantValue.CONTACT_NOT_EXIST;
		//When 
		RuntimeException actuel = assertThrows(RuntimeException.class, 
				() -> contactService.findContactById(contactId)); 
		//Then

		assertEquals(expect, actuel.getMessage());
	}

	@Test
	public void testFindContact_email_basic() {

		//given
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(contact));
		//When 
		Contact actuel = contactService.findContactByEmail(contact.getEmail()); 
		//Then

		assertEquals(contact, actuel);
	}

	@Test
	public void testFindContact_when_contact_email_not_exist() {

		//given
		
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(null));
		String expect = ErrorConstantValue.CONTACT_NOT_EXIST_WITH_EMAIL;
		
		//When 
		RuntimeException actuel = assertThrows(RuntimeException.class, 
				() -> contactService.findContactByEmail(contact.getEmail())); 
		//Then

		assertEquals(expect, actuel.getMessage());
	}
	
	@Test
	public void testFindContact_phone_basic() {

		//given
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository.findByPhone(contact.getPhone()))
		.thenReturn(Optional.ofNullable(contact));
		//When 
		Contact actuel = contactService.findContactByPhone(contact.getPhone()); 
		//Then

		assertEquals(contact, actuel);
	}

	@Test
	public void testFindContact_when_contact_phone_not_exist() {

		//given
		
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository.findByPhone(contact.getPhone()))
		.thenReturn(Optional.ofNullable(null));
		
		String expect = ErrorConstantValue.CONTACT_NOT_EXIST_WITH_PHONE;
		
		//When 
		RuntimeException actuel = assertThrows(RuntimeException.class, 
				() -> contactService.findContactByPhone(contact.getPhone())); 
		//Then

		assertEquals(expect, actuel.getMessage());
	}
	
	@Test
	public void testFindContact_fullName_basic() {

		//given
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository
				.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname()))
		.thenReturn(Optional.ofNullable(contact));
		//When 
		Contact actuel = contactService.findContactsByFirstnameAndLastname(
				contact.getFirstname(), contact.getLastname()); 
		//Then

		assertEquals(contact, actuel);
	}

	@Test
	public void testFindContact_when_contact_fullName_not_exist() {

		//given
		
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository
				.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname()))
		.thenReturn(Optional.ofNullable(null));
		
		String expect = ErrorConstantValue.CONTACT_NOT_EXIST_WITH_NAME;
		
		//When 
		RuntimeException actuel = assertThrows(RuntimeException.class, 
				() -> contactService.findContactsByFirstnameAndLastname(
						contact.getFirstname(), contact.getLastname())); 
		//Then

		assertEquals(expect, actuel.getMessage());
	}
}
