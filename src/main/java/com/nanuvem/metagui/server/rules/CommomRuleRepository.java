package com.nanuvem.metagui.server.rules;

import java.util.List;

import com.nanuvem.metagui.server.api.Rule;

public interface CommomRuleRepository<T extends Rule> {

	public List<T> findByVersionGreaterThan(Long version);
	
}
