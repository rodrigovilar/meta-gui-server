package com.nanuvem.metagui.server.context;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.Context;
import java.lang.String;
import java.util.List;

public interface ContextRepository extends
		JpaRepository<Context, String> {

	public List<Context> findByName(String name);
	
}
