package de.computacenter.team.contact.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.service.impl.ContactService;


@WebMvcTest
public class SaveContactControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ContactService contactService;
	@Autowired 
	private ObjectMapper mapper;
	
	@Test
	public void testSaveContact_basic() throws Exception {
		//Given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		String contactJson = mapper.writeValueAsString(contact);
		
		when(contactService.saveContact(contact))
		.thenReturn(contact);
		
		RequestBuilder builder = MockMvcRequestBuilders
				.post("/api/contacts")
				.content(contactJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		String mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(202))
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertEquals(contactJson, mvcResult);
		}
	
	//@Test
	public void testSaveContact_When_RuntimeException() throws Exception {
		//Given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester",
				"t.tester@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		String contactJson = mapper.writeValueAsString(contact);
		
		when(contactService.saveContact(contact))
		.thenThrow(NestedServletException.class);
		
		RequestBuilder builder = MockMvcRequestBuilders
				.post("/api/contacts")
				.content(contactJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		String mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(500))
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		}
	
	@Test
	public void testDeleteeContact_basic() throws Exception {
		//Given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		
		doNothing().when(contactService).deleteContact(contactId);
		
		RequestBuilder builder = MockMvcRequestBuilders
				.delete("/api/contacts/" + contactId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		String mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(202))
				.andReturn()
				.getResponse()
				.getContentAsString();
		}
	
	@Test
	public void testFindContact_basic() throws Exception {
		//Given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		when(contactService.findContactById(contactId))
		.thenReturn(contact);
		
		RequestBuilder builder = MockMvcRequestBuilders
				.get("/api/contacts/"+contactId)
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		String mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(202))
				.andExpect(MockMvcResultMatchers.jsonPath("$.contactId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.contactId").value(contactId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(contact.getFirstname()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(contact.getLastname()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(contact.getEmail()))
				.andReturn()
				.getResponse()
				.getContentAsString();
		}
	
	@Test
	public void testFindContactsWhenReturnNotElement() throws Exception {
		//Given
		when(contactService.findContacts())
		.thenReturn(Arrays.asList());
		
		RequestBuilder builder = MockMvcRequestBuilders
				.get("/api/contacts")
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		String mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(202))
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertEquals("[]", mvcResult);
	}
	
	@Test
	public void testFindContactsWhenReturnOneElement() throws Exception {
		//Given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		when(contactService.findContacts())
		.thenReturn(Arrays.asList(contact));
		
		RequestBuilder builder = MockMvcRequestBuilders
				.get("/api/contacts")
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		MvcResult mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(202))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].contactId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].contactId").value(contactId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].phone").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].address").exists())
				.andReturn();

	}
	
	@Test
	public void testUpdateContact_basic() throws Exception {
		//Given
		String contactId = UUID.randomUUID().toString();
		Contact contact = new Contact("Testmann", "Tester", 
				"t.tester@yahoo.de", "004917635351632");
		contact.setContactId(contactId);
		String contactJson = mapper.writeValueAsString(contact);
		
		when(contactService.updateContact(contactId,contact))
		.thenReturn(contact);
		
		RequestBuilder builder = MockMvcRequestBuilders
				.put("/api/contacts/" + contactId)
				.content(contactJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		//When
		String mvcResult = mockMvc.perform(builder)
				.andExpect(status().is(202))
				.andReturn()
				.getResponse()
				.getContentAsString();
		}
	
}
