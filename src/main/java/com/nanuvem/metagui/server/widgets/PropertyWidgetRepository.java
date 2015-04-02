package com.nanuvem.metagui.server.widgets;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.PropertyWidget;
import com.nanuvem.metagui.server.api.Widget;

public interface PropertyWidgetRepository extends JpaRepository<PropertyWidget, Long> {

	public List<Widget> findByName(String name);
	
}
