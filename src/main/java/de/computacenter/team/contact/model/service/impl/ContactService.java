package de.computacenter.team.contact.model.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import de.computacenter.team.contact.model.entities.Address;
import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.repository.ContactRepository;
import de.computacenter.team.contact.model.service.IContactService;
import de.computacenter.team.contact.model.service.ErrorConstantValue;

@Service("ContactService")
public class ContactService implements IContactService{

	private ContactRepository contactRepository;
	private static final Logger logger = LogManager.getLogger(ContactService.class);

	public ContactService(ContactRepository contactRepository) {
		super();
		this.contactRepository = contactRepository;
	}

	@Override
	public Contact saveContact(Contact contact) {
		isContactValide(contact);
		logger.info("ContactService.saveContact: save-contact is valid");
		contactAlreadyExist(contact);
		logger.info("ContactService.saveContact: save-contact not exist");
		if(contact.getContactId() == null || contact.getContactId().equals(""))
			contact.setContactId(UUID.randomUUID().toString());
		if(contact.getAddress() == null) {
			contact.setAddress(new Address());
		}else {
			if("".equals(contact.getAddress().getStreet()))
				contact.getAddress().setStreet(null);
			if("".equals(contact.getAddress().getCountry()))
				contact.getAddress().setCountry(null);
			if("".equals(contact.getAddress().getCity()))
				contact.getAddress().setCity(null);
		}
		Contact saveContact = contactRepository.save(contact);
		logger.info("ContactService.saveContact: save contact successful");
		return saveContact;
	}

	@Override
	public Contact updateContact(String contactId, Contact contact) {
		isContactValide(contact);
		logger.info("ContactService.updateContact: update-contact is valid");

		Optional<Contact> optional = contactRepository.findByContactId(contactId);
		if(!optional.isPresent()) {
			logger.error("ContactService.updateContact: " + ErrorConstantValue.CONTACT_NOT_EXIST);
			throw new RuntimeException(ErrorConstantValue.CONTACT_NOT_EXIST);
		}

		Optional<Contact> optionalEmail = contactRepository.findByEmail(contact.getEmail());
		if(optionalEmail.isPresent() 
				&& !optionalEmail.get().getContactId().equals(contactId )) {
			logger.error("ContactService.updateContact: " + ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_EMAIL);
			throw new RuntimeException(ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_EMAIL);
		}
			

		Optional<Contact> optionalFullName = contactRepository
				.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname());
		if(optionalFullName.isPresent() 
				&& !optionalFullName.get().getContactId().contentEquals(contactId )) {
			logger.error("ContactService.updateContact: " + ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_NAME);
			throw new RuntimeException(ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_NAME);
		}
			
		
		Optional<Contact> optionalContactPhone = contactRepository
				.findByPhone(contact.getPhone());
		if(optionalContactPhone.isPresent() 
				&& !optionalContactPhone.get().getContactId().contentEquals(contactId )) {
			logger.error("ContactService.updateContact: " + ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_PHONE);
			throw new RuntimeException(ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_PHONE);
		}

		contact.setContactId(contactId);

		return contactRepository.save(contact);
	}

	@Override
	public void deleteContact(String contactId) {
		Optional<Contact> optional = contactRepository.findByContactId(contactId);
		if(!optional.isPresent()) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_NOT_EXIST);
			throw new RuntimeException(ErrorConstantValue.CONTACT_NOT_EXIST);
		}

		contactRepository.delete(optional.get());

	}

	@Override
	public List<Contact> findContacts() {
		return contactRepository.findAll();
	}

	@Override
	public Contact findContactById(String contactId) {
		Optional<Contact> optional = contactRepository.findByContactId(contactId);
		if(!optional.isPresent()) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_NOT_EXIST);
			throw new RuntimeException(ErrorConstantValue.CONTACT_NOT_EXIST);
		}
		return optional.get();
	}

	@Override
	public Contact findContactByEmail(String email) {
		Contact contact = contactRepository.findByEmail(email).orElse(null);
		if(contact == null) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_NOT_EXIST_WITH_EMAIL);
			throw new RuntimeException(ErrorConstantValue.CONTACT_NOT_EXIST_WITH_EMAIL);
		}
		return contact;
	}
	
	@Override
	public Contact findContactByPhone(String phone) {
		
		Contact contact = contactRepository.findByPhone(phone).orElse(null);
		if(contact == null) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_NOT_EXIST_WITH_PHONE);
			throw new RuntimeException(ErrorConstantValue.CONTACT_NOT_EXIST_WITH_PHONE);
		}
		return contact;
	}

	@Override
	public List<Contact> findContactsByFirstname(String firstname) {
		return contactRepository.findByFirstname(firstname);
	}

	@Override
	public List<Contact> findContactsByLastname(String lastname) {
		return contactRepository.findByFirstname(lastname);
	}

	@Override
	public Contact findContactsByFirstnameAndLastname(String firstname, String lastname) {
		Optional<Contact> optional = contactRepository.findByFirstnameAndLastname(firstname, lastname);
		if(!optional.isPresent()) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_NOT_EXIST_WITH_NAME);
			throw new RuntimeException(ErrorConstantValue.CONTACT_NOT_EXIST_WITH_NAME);
		}
			
		return optional.get();
	}

	private void isContactValide(Contact contact) {
		if(contact.getFirstname() == null || contact.getFirstname().equals("")) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.FIRSTNAME_NOT_NULL);
			throw new RuntimeException(ErrorConstantValue.FIRSTNAME_NOT_NULL);
		}
		if(contact.getLastname() == null || contact.getLastname().equals("")) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.LASTNAME_NOT_NULL);
			throw new RuntimeException(ErrorConstantValue.LASTNAME_NOT_NULL);
		}
			
		if(contact.getEmail() == null || contact.getEmail().equals("")) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.EMAIL_NOT_NULL);
			throw new RuntimeException(ErrorConstantValue.EMAIL_NOT_NULL);
		}
			
		if(contact.getPhone() == null || contact.getPhone().equals("")) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.PHONE_NOT_NULL);
			throw new RuntimeException(ErrorConstantValue.PHONE_NOT_NULL);
		}
			
	}

	private void contactAlreadyExist(Contact contact) {
		if(contactRepository.findByEmail(contact.getEmail()).isPresent()) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_EMAIL);
			throw new RuntimeException(ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_EMAIL);
		}

		if(contactRepository.findByPhone(contact.getPhone()).isPresent()) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_PHONE);
			throw new RuntimeException(ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_PHONE);
		}
			
		if(contactRepository
				.findByFirstnameAndLastname(contact.getFirstname(), contact.getLastname()).isPresent()) {
			logger.error("ContactService.deleteContact: " + ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_NAME);
			throw new RuntimeException(ErrorConstantValue.CONTACT_ALREADY_EXIST_WITH_NAME);
		}
			
	}
}
