package com.nanuvem.metagui.server.rules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.EntityRule;

public interface EntityRuleRepository extends JpaRepository<EntityRule, Long>,
		CommomRuleRepository<EntityRule> {

}
