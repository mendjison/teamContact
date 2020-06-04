package de.computacenter.team.contact.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(length = 50)
	private String street;
	@Column(length = 4)
	private String housNo;
	@Column(columnDefinition = "Integer(6) default 0")
	private int poBox;
	@Column(length = 50)
	private String city;
	@Column(length = 50)
	private String country;
	public Address() {}
	public Address(String street, String housNo, int poBox, String city, String country) {
		super();
		this.street = street;
		this.housNo = housNo;
		this.poBox = poBox;
		this.city = city;
		this.country = country;
	}
	
	public Address(String street, String housNo, int poBox, String city) {
		this(street, housNo, poBox, city, "Deutschland");
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHousNo() {
		return housNo;
	}
	public void setHousNo(String housNo) {
		this.housNo = housNo;
	}
	public int getPoBox() {
		return poBox;
	}
	public void setPoBox(int poBox) {
		this.poBox = poBox;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean isEquals = false;
		if(object instanceof Address) {
			Address address = (Address) object;
			
			if(this.getStreet() == null && address.getStreet() == null)
				isEquals = true;
			else {
				if((this.getStreet() != null && address.getStreet() == null) || (this.getStreet() == null && address.getStreet() != null))
					isEquals = false;
				if(!this.getStreet().equals(address.getStreet()))
					return false;
			}
			
			
			if(this.getCity() == null && address.getCity() == null) {
				isEquals = true;
			}
			else {
				if((this.getCity() != null && address.getCity() == null) || (this.getCity() == null && address.getCity() != null))
					return false;
				if(!this.getCity().equals(address.getCity()))
					return false;
			}
			
			if(this.getCountry() == null && address.getCountry() == null) {
				isEquals = true;
			}
			else {
				if((this.getCountry() != null && address.getCountry() == null) || (this.getCountry() == null && address.getCountry() != null))
					return false;
				if(!this.getCountry().equals(address.getCountry()))
					return false;
			}
			
			if(this.getHousNo() == null && address.getHousNo() == null) {
				isEquals = true;
			}
			else {
				if((this.getHousNo() != null && address.getHousNo() == null) || (this.getHousNo() == null && address.getHousNo() != null))
					return false;
				if(!this.getHousNo().equals(address.getHousNo()))
					return false;
			}
			
			if(this.getPoBox()!= address.getPoBox())
				return false;
			
			return true;
		}
		return false;
	}
}
