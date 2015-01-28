package com.capstone.sm.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;


@Entity
public class Patient 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    private  String firstname;
	private  String lastname;
	private  String dateofbirth;
	private  String medicalrecordid;
	private  String username;
	private  String password;
	private  byte[] image;
	private  long doctorid;
	private String contenttype;

	
	@JsonIgnore
	private String dataUrl;
	
	public Patient() {
	}

	public Patient(String firstname, String lastname, String dateofbirth, String medicalrecordid, String username, 
			String password, byte[] image, long doctorid, String contenttype) 
	{
		super();
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateofbirth;
		this.medicalrecordid = medicalrecordid;
		this.username = username;
		this.password = password;
		this.image = image;
		this.doctorid = doctorid;
		this.contenttype = contenttype;
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
	
	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	
	public String getMedicalrecordid() {
		return medicalrecordid;
	}

	public void setMedicalrecordid(String medicalrecordid) {
		this.medicalrecordid = medicalrecordid;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public long getDoctorid() {
		return doctorid;
	}
	
	public void setDoctorid(long doctorid) {
		this.doctorid = doctorid;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	

	@JsonProperty
	public String getDataUrl() {
		return dataUrl;
	}

	@JsonIgnore
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	
	/**
	 * Two Patients will generate the same hashcode if they have exactly the same
	 * values for their name, url, and duration.
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(username);
	}

	/**
	 * Two Videos are considered equal if they have exactly the same values for
	 * their name, url, and duration.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Patient) {
			Patient other = (Patient) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(username, other.username);
		} else {
			return false;
		}
	}

}
