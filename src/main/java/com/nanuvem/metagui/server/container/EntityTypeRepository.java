package com.nanuvem.metagui.server.container;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nanuvem.metagui.server.container.EntityTypeDomain;

public interface EntityTypeRepository extends JpaRepository<EntityTypeDomain, Integer> {

	public EntityTypeDomain findByResource(String resource);
	
	public EntityTypeDomain findByName(String name);
	
}
