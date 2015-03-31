package com.nanuvem.metagui.server.rules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.DefaultEntityRule;

public interface DefaultEntityRuleRepository extends
		JpaRepository<DefaultEntityRule, Long> {

}
