package com.nanuvem.metagui.server.rules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.PropertyTypeRule;

public interface PropertyTypeRuleRepository extends
		JpaRepository<PropertyTypeRule, Long> {

}
