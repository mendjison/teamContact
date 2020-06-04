package de.computacenter.team.contact.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import de.computacenter.team.contact.model.entities.Contact;

@DataJpaTest
@TestPropertySource(locations = "classpath:application_test.properties")
public class SaveTest {

	@Autowired
	private ContactRepository contactRepository;
	
	@BeforeEach
	public void setup() {
		contactRepository.deleteAll();
	}
	
	@Test
	public void test_save_basic() {
		//given
		Contact contact = new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636");
		//When
		Contact result = contactRepository.save(contact);
		
		//Then
		assertNotNull(result.getContactId());
		assertNotNull(result.getAddress());
		assertEquals(contact.getFirstname(), result.getFirstname());
		assertEquals(contact.getLastname(), result.getLastname());
		assertEquals(contact.getEmail(), result.getEmail());
		assertEquals(contact.getPhone(), result.getPhone());
	}
	
	@Test
	public void test_save_When_id_is_fix() {
		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636");
		contact.setContactId(contactId);
		//When
		Contact result = contactRepository.save(contact);
		
		//Then
		assertNotNull(result.getContactId());
		assertEquals(contactId, result.getContactId());
	}
}
