package com.nanuvem.metagui.server.entitytype;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.nanuvem.metagui.server.annotations.EntityType;
import com.nanuvem.metagui.server.annotations.PropertyType;
import com.nanuvem.metagui.server.repository.CustomerRepository;

@EntityType(resource = "customer", repository=CustomerRepository.class)
@Entity
public class Customer {

	@Id
	@PropertyType(ignore=true)
	private Long id;
}
