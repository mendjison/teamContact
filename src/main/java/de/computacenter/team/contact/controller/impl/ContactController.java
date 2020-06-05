package de.computacenter.team.contact.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.computacenter.team.contact.controller.IContactController;
import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.service.IContactService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/contacts")
//@Api(value = "Contact")
public class ContactController implements IContactController{

	@Qualifier("ContactService")
	private IContactService iContactService;
	
	private static final Logger logger = LogManager.getLogger(ContactController.class);
	
	
	
	public ContactController(IContactService iContactService) {
		super();
		this.iContactService = iContactService;
	}

	@GetMapping
	//@ApiOperation(value = "findContacts", response = ResponseEntity.class)
	@Override
	public ResponseEntity<List<Contact>> findContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			contacts = iContactService.findContacts();
			logger.info("findContacts: find successful");
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("findContacts: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(contacts);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<Contact> getContact(@PathVariable(name = "id") String contactId) {
		Contact contact = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			contact = iContactService.findContactById(contactId);
			logger.info("getContact: find successful");
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("getContact: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(contact);
	}

	@PostMapping
	@Override
	public ResponseEntity<Contact> saveContact(@RequestBody Contact contact) {
		Contact result = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			result = iContactService.saveContact(contact);
			logger.info("saveContact: save successfull");
		}
		catch (Exception e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("saveContact: "+e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return ResponseEntity.accepted().headers(httpHeaders).body(result);
	}

	@PutMapping("/{contactId}")
	@Override
	public ResponseEntity<Contact> updateContact(@PathVariable(name = "contactId") String contactId, 
			@RequestBody Contact contact) {
		Contact result = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			result = iContactService.updateContact(contactId, contact);
			logger.info("updateContact: update Sucessfull");
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("updateContact: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(result);
	}

	@DeleteMapping("/{contactId}")
	@Override
	public ResponseEntity<Boolean> deleteContact(@PathVariable(name = "contactId") String contactId) {
		boolean result = false;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			iContactService.deleteContact(contactId);
			logger.info("deleteContact: delete successfull");
			result = true;
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("deleteContact: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(result);
	}

	@GetMapping("/email")
	@Override
	public ResponseEntity<Contact> getContractByEmail(@RequestParam(name = "email") String email) {

		Contact contact = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			contact = iContactService.findContactByEmail(email);
			logger.info("getContractByEmail: find successfull" );
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("getContractByEmail: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(contact);
	}

	@GetMapping("/firstname")
	@Override
	public ResponseEntity<List<Contact>> getContractByFirstname(@RequestParam(name = "firstname") String firstname) {
		List<Contact> contacts = new ArrayList<Contact>();
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			contacts = iContactService.findContactsByFirstname(firstname);
			logger.info("getContractByFirstname: find successfull ");
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("getContractByFirstname:" + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(contacts);
	}

	@GetMapping("/lastname")
	@Override
	public ResponseEntity<List<Contact>> getContractByLastname(@RequestParam(name ="lastname") String lastname) {
		List<Contact> contacts = new ArrayList<Contact>();
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			contacts = iContactService.findContactsByLastname(lastname);
			logger.info("getContractByLastname: find successfull ");
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("getContractByLastname: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(contacts); 
	}

	@GetMapping("/fullname")
	@Override
	public ResponseEntity<Contact> getContractByFirstnameAndLastname(@RequestParam(name = "lastname") String firstname, 
			@RequestParam(name = "lastname") String lastname) {
		Contact contact = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			contact = iContactService.findContactsByFirstnameAndLastname(firstname, lastname);
			logger.info("getContractByFirstnameAndLastname: find successfull");
		} catch (RuntimeException e) {
			httpHeaders.add("errorMessage", e.getMessage());
			logger.error("getContractByFirstnameAndLastname: " + e.getMessage());
			throw new RuntimeException(e.getMessage(),e);
		}
		return ResponseEntity.accepted().headers(httpHeaders).body(contact);
	}

}
