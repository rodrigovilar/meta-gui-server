package com.nanuvem.metagui.server.entitytype;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.nanuvem.metagui.server.api.EntityType;

@Entity
@EntityType(resource="dependents", repositoryType=DependentRepository.class)
public class Dependent {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private Integer age;
	
	@ManyToOne
	private Client client;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
