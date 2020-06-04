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

public class DeleteContact {

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

		//When 
		contactService.deleteContact(contactId); 
		//Then

	}

	@Test
	public void updateContacts_when_contactId_not_exist() {

		//given
		String contactId = UUID.randomUUID().toString();

		when(mockRepository.findByContactId(contactId))
		.thenReturn(Optional.ofNullable(null));
		
		String expected = ErrorConstantValue.CONTACT_NOT_EXIST;

		//When 
		RuntimeException exception = assertThrows(RuntimeException.class, 
				() -> contactService.deleteContact(contactId));
		//Then
		assertEquals(expected, exception.getMessage());
	}
}
