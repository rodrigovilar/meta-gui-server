package com.nanuvem.metagui.server.rules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.PropertyRule;

public interface PropertyRuleRepository extends
		JpaRepository<PropertyRule, Long>, CommomRuleRepository<PropertyRule> {

}
