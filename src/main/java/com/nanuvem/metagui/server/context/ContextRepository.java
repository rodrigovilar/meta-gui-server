package com.nanuvem.metagui.server.context;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.Context;

public interface ContextRepository extends
		JpaRepository<Context, String> {

	public Context findByName(String name);
	
}
