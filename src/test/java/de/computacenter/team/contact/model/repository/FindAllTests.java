package de.computacenter.team.contact.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import de.computacenter.team.contact.model.entities.Contact;


@DataJpaTest
@TestPropertySource(locations = "classpath:application_test.properties")
public class FindAllTests {

	@Autowired
	private ContactRepository contactRepository;
	
	@BeforeEach
	public void setup() {
		contactRepository.deleteAll();
	}
	
	@Test
	public void testFindAll_basic() {
		
		//When
		List<Contact> contacts = contactRepository.findAll();
		
		//Then
		assertEquals(0, contacts.size());
	}
	
	@Test
	public void testFindAll_when_two_contacts() {
		//given
		Contact contact = new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636");
		contactRepository.save(contact);
		//When
		List<Contact> contacts = contactRepository.findAll();
		
		//Then
		assertEquals(1, contacts.size());
	}
}
