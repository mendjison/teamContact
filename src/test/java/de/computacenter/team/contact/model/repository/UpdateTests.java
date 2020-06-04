package de.computacenter.team.contact.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import de.computacenter.team.contact.model.entities.Address;
import de.computacenter.team.contact.model.entities.Contact;

@DataJpaTest
@TestPropertySource(locations = "classpath:application_test.properties")
public class UpdateTests {

	@Autowired
	private ContactRepository contactRepository;
	
	@BeforeEach
	public void setup() {
		contactRepository.deleteAll();
	}
	
	@Test
	public void test_save_basic() {
		//given
		Contact contact = contactRepository.save(new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636"));
		Address address = new Address("Engsbachstrasse", "57A", 57076, "Siegen");
		//When
		contact.setAddress(address);
		Contact result = contactRepository.save(contact);
		
		//Then
		assertNotNull(result.getContactId());
		assertNotNull(result.getAddress());
		assertEquals(contact.getFirstname(), result.getFirstname());
		assertEquals(contact.getLastname(), result.getLastname());
		assertEquals(contact.getEmail(), result.getEmail());
		assertEquals(contact.getPhone(), result.getPhone());
		assertTrue(result.getAddress().equals(contact.getAddress()));
		assertEquals(contact.getAddress(), result.getAddress());
		assertEquals(contact.getContactId(), result.getContactId());
	}
}
