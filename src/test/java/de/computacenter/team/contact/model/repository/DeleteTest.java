package de.computacenter.team.contact.model.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;

import de.computacenter.team.contact.model.entities.Contact;

@DataJpaTest
@TestPropertySource(locations = "classpath:application_test.properties")
public class DeleteTest {

	@Autowired
	private ContactRepository contactRepository;
	
	@BeforeEach
	public void setup() {
		contactRepository.deleteAll();
	}
	
	@Test
	public void test_delete_basic() {
		//given
		Contact contact = contactRepository.save(new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636"));
		//When
		 contactRepository.delete(contact);
	}
	
	@Test
	public void test_delete_when_contact_not_exist() {
		//given
		Contact contact = new Contact("Mandy", "Hausmann",
				"m.hausmann@outlook.de", "004917635351636");
		//When
		 contactRepository.delete(contact);
	}
	
	@Test
	public void test_delete_when_contact_is_null() {
		//given
		String expected = "Entity must not be null!";
		//When
		InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () ->
		contactRepository.delete(null));
		
		// then
		assertTrue(exception.getMessage().contains(expected));
	}
	/**
	 * IllegalArgumentException: Entity must not be null!
	 */
}
