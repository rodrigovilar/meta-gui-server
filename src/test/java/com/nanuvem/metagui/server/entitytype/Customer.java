package com.nanuvem.metagui.server.entitytype;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.nanuvem.metagui.server.api.EntityType;

@Entity
@EntityType(resource="customer", repositoryType=CustomerRepository.class)
public class Customer {

	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
