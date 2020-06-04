package de.computacenter.team.contact.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import de.computacenter.team.contact.model.entities.Contact;

@DataJpaTest
@TestPropertySource(locations = "classpath:application_test.properties")
public class FindByEmailTests {

	@Autowired
	private ContactRepository contactRepository;

	@BeforeEach
	public void setup() {
		contactRepository.deleteAll();
	}
	
	@Test
	public void test_findByEmail_basic() {
		//given
		Contact contact = contactRepository.save(new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636"));
		//When
		Contact result = contactRepository.findByEmail(contact.getEmail())
				.orElse(null);
		//Then
		assertEquals(contact, result);
	}
	
	@Test
	public void test_findByEmail_When_Email_is_null() {
		//When
		Contact result = contactRepository.findByEmail(null)
				.orElse(null);

		//Then
		assertNull(result);	
	}
	
	@Test
	public void test_findByEmail_When_Email_not_exist() {
		//When
		Contact result = contactRepository.findByEmail("k.mendji@yahoo.com")
				.orElse(null);

		//Then
		assertNull(result);	
	}
	
	@Test
	public void test_findByEmail_When_Email_is_leer() {
		//When
		Contact result = contactRepository.findByEmail("")
				.orElse(null);

		//Then
		assertNull(result);	
	}
}
