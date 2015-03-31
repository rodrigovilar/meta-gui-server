package com.nanuvem.metagui.server.entitytype;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.nanuvem.metagui.server.annotations.EntityType;
import com.nanuvem.metagui.server.repository.CustomerDetailsRepository;

@EntityType(resource = "customerDetails", repository=CustomerDetailsRepository.class)
@Entity
public class CustomerDetails {

	@GeneratedValue 
	@Id
	private int id;
	private String ssn;
	private String name;
	private Date birthdate;
	private double credit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

}
