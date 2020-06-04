package de.computacenter.team.contact.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.repository.ContactRepository;
import de.computacenter.team.contact.model.service.impl.ContactService;

public class SaveContactTest {

	@Mock
	private ContactRepository mockRepository;

	@InjectMocks
	private ContactService contactService;
	@BeforeEach
	public void setUps() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveContacts_Basic() {

		//given
		Contact expected = new Contact("Testmann", "Tester", "t.tester@outlook.com", "004917635351634");

		when(mockRepository.findByEmail(expected.getEmail()))
		.thenReturn(Optional.ofNullable(null));
		when(mockRepository.findByPhone(expected.getPhone()))
		.thenReturn(Optional.ofNullable(null));
		when(mockRepository.findByFirstnameAndLastname(expected.getFirstname(), expected.getLastname()))
		.thenReturn(Optional.ofNullable(null));
		when(mockRepository.save(expected)).thenReturn(expected);
		//When 
		Contact actual = contactService.saveContact(expected); 
		//Then
		assertEquals(expected, actual);

	}

	@Test
	public void saveContacts_when_firstname_is_null() {

		//given
		Contact contact = new Contact(null, "Tester", "t.tester@outlook.com", "004917635351634");
		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.FIRSTNAME_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());

	}

	@Test
	public void saveContacts_when_firstname_is_leer() {

		//given
		Contact contact = new Contact("", "Tester", "t.tester@outlook.com", "004917635351634");

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.FIRSTNAME_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());

	}

	@Test
	public void saveContacts_when_lastname_is_null() {

		//given
		Contact contact = new Contact("Testmann", null, "t.tester@outlook.com", "004917635351634");

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.LASTNAME_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_lastname_is_leer() {

		//given
		Contact contact = new Contact("Testmann", "", "t.tester@outlook.com", "004917635351634");

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.LASTNAME_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_email_is_null() {

		//given
		Contact contact = new Contact("Testmann", "Tester", null, "004917635351634");

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.EMAIL_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_email_is_leer() {

		//given
		Contact contact = new Contact("Testmann", "Tester", "", "004917635351634");

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.EMAIL_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_phone_is_null() {

		//given
		Contact contact = new Contact("Testmann", "Tester", "t.tester@outlook.com", null);

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.PHONE_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_phone_is_leer() {

		//given
		Contact contact = new Contact("Testmann", "Tester", "t.tester@outlook.com", "");

		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.PHONE_NOT_NULL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_email_already_Exist() {

		//given
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@outlook.com", "004917635351634");
		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(new Contact("Testmanddn",
				"Tessster", "t.tester@outlook.com", "00417635351634")));
		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_EMAIL;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_phone_already_Exist() {

		//given
		Contact contact = new Contact("Testmann", "Tester", "t.tester@outlook.com",
				"004917635351634");
		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(null));

		when(mockRepository.findByPhone(contact.getPhone()))
		.thenReturn(Optional.ofNullable(new Contact("Testmanddn", "Tessster", "t.testedr@outlook.com", "004917635351634")));
		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_PHONE;
		//Then
		assertEquals(expected, actual.getMessage());
	}

	@Test
	public void saveContacts_when_fullName_already_Exist() {

		//given
		Contact contact = new Contact("Testmann", "Tester", "t.tester@outlook.com",
				"004917635351634");
		when(mockRepository.findByEmail(contact.getEmail()))
		.thenReturn(Optional.ofNullable(null));

		when(mockRepository.findByPhone(contact.getPhone()))
		.thenReturn(Optional.ofNullable(null));

		when(mockRepository.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname()))
		.thenReturn(Optional.ofNullable(new Contact()));
		//When 
		RuntimeException actual = assertThrows(RuntimeException.class, () -> 
		contactService.saveContact(contact));
		String expected = ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_NAME;
		//Then
		assertEquals(expected, actual.getMessage());
	}
}
