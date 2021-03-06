package com.nanuvem.metagui.server.entitytype;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.nanuvem.metagui.server.api.EntityType;
import com.nanuvem.metagui.server.api.RelationshipType;

@Entity
@EntityType(resource="clients", repositoryType=ClientRepository.class)
public class Client {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@RelationshipType(composition=true)
	@OneToMany(mappedBy="client")
	private List<Dependent> dependents;

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dependent> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependent> dependents) {
		this.dependents = dependents;
	}

}
