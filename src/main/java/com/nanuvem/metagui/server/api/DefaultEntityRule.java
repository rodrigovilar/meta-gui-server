package com.nanuvem.metagui.server.api;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class DefaultEntityRule extends Rule {

	@ManyToOne
	private EntityWidget widget;

	public EntityWidget getWidget() {
		return widget;
	}

	public void setWidget(EntityWidget widget) {
		this.widget = widget;
	}
	
}
