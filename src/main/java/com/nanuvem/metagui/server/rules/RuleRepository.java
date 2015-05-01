package com.nanuvem.metagui.server.rules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long>{

	public List<Rule> findByVersionGreaterThan(Long version);
	
}
