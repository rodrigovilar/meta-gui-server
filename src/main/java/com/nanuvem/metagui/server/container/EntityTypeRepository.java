package com.nanuvem.metagui.server.container;

import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.String;
import com.nanuvem.metagui.server.container.EntityTypeDomain;
import java.util.List;

public interface EntityTypeRepository extends JpaRepository<EntityTypeDomain, Long> {

	public List<EntityTypeDomain> findByResource(String resource);
	
}
