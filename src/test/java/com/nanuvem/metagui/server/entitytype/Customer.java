package com.nanuvem.metagui.server.entitytype;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.nanuvem.metagui.server.annotations.EntityType;
import com.nanuvem.metagui.server.annotations.PropertyType;

@EntityType(resource = "customer")
@Entity
public class Customer {

	@Id
	@PropertyType(ignore=true)
	private Long id;
}
