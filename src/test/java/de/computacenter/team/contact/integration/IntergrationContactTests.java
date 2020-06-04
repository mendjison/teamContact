package de.computacenter.team.contact.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.repository.ContactRepository;



@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_test.properties")
public class IntergrationContactTests {

	
	@Autowired
	private TestRestTemplate testRestTemplate;
	private static final String URL = "/api/contacts";
	@Autowired
	private ContactRepository contactRepository;
	
	@BeforeEach
	public void setup() {
		contactRepository.deleteAll();
	}
	
	@Test
	public void findAllTest() {
		ResponseEntity<List<Contact>> result = 
				testRestTemplate.exchange(URL,
		                HttpMethod.GET,null ,new ParameterizedTypeReference<List<Contact>>() {});
		assertEquals(0, result.getBody().size());
	}
	
	@Test
	public void saveTest() {
		//given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Jeans", 
				"Takmann", "j.takmann@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		//When
		ResponseEntity<Contact> result = 
				testRestTemplate.
				postForEntity(URL, contact, Contact.class);
		//Then
		assertNotNull(result.getBody());
		assertEquals(contact.getFirstname(), result.getBody().getFirstname());
	}
	
	@Test
	public void updateTest() {
		//given
		ResponseEntity<Contact> responseEntity = 
				testRestTemplate.
				postForEntity(
						URL, 
						new Contact("Jeans", 
								"Takmann", "j.takmann@yahoo.de", "004917635351632"),
						Contact.class);
		
		Contact contact = responseEntity.getBody();
		contact.setEmail("siegen.land@yahoo.de");
		HttpEntity<Contact> requestEntity = new HttpEntity<>(contact);
		
		//When
		ResponseEntity<Contact> result = 
				testRestTemplate.
				exchange(URL+"/"+ contact.getContactId(), HttpMethod.PUT, requestEntity, Contact.class);
		
		//Then
		assertNotNull(result.getBody());
		assertEquals(contact.getFirstname(), result.getBody().getFirstname());
	}
	
	@Test
	public void deleteTest() {
		//given
		ResponseEntity<Contact> responseEntity = 
				testRestTemplate.
				postForEntity(URL,new Contact("Jeans", 
						"Takmann", "j.takmann@yahoo.de", "004917635351632"), Contact.class);
		
		Contact contact = responseEntity.getBody();
		
		//When
		  testRestTemplate.delete(URL+"/"+ contact.getContactId());
		
		//Then
		
	}
}
