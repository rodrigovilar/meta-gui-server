package com.nanuvem.metagui.server.entitytype;

import java.util.Date;

import javax.persistence.Entity;

import com.nanuvem.metagui.server.api.EntityType;

@Entity
public class CustomerDetails extends EntityType{

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
