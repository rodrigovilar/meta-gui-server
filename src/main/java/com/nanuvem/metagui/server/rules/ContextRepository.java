package com.nanuvem.metagui.server.rules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.Context;

public interface ContextRepository extends
		JpaRepository<Context, String> {

}
