package de.computacenter.team.contact.model.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "contacts", uniqueConstraints={@UniqueConstraint(columnNames={"firstname", "lastname"})})
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(updatable = false, nullable = false)
	private String contactId;
	@Column(nullable = false, length = 50)
	private String firstname;
	@Column(nullable = false, length = 50)
	private String lastname;
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	@Column(nullable = false, unique = true, length = 17)
	private String phone;
	/*
	 * @ElementCollection
	 * 
	 * @CollectionTable(name="Contact_Adress",
	 * joinColumns=@JoinColumn(name="memberId"))
	 */
	@Embedded
	private Address address;

	public Contact() {}

	public Contact(String contactId, String firstname, String lastname, String email, String phone,
			Address address) {
		super();
		this.contactId = contactId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public Contact( String firstname, String lastname, String email, String phone, Address address) {
		this(UUID.randomUUID().toString(), firstname, lastname, email, phone, address);
	}

	public Contact( String firstname, String lastname, String email, String phone) {
		this(UUID.randomUUID().toString(), firstname, lastname, email, phone, new Address());
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Contact) {
			Contact contact = (Contact) object;
			
			if(!this.getContactId().equals(contact.getContactId()))
				return false;
			
			if(!this.getFirstname().equals(contact.getFirstname()))
				return false;
			
			if(!this.getLastname().equals(contact.getLastname()))
				return false;
			
			if(!this.getEmail().equals(contact.getEmail()))
				return false;
			
			if(!this.getPhone().equals(contact.getPhone()))
				return false;
			
			if((this.getAddress() == null && contact.getAddress() != null) || 
					(this.getAddress() != null && contact.getAddress() == null))
				return false;
			
			if(this.getAddress() == null && contact.getAddress() == null)
				return true;
			
			if(!this.getAddress().equals(contact.getAddress()))
				return false;
			
			return true;
		}
		return false;
	}
	
}
