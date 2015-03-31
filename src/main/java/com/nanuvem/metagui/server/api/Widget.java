package com.nanuvem.metagui.server.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Widget {

	@GeneratedValue
	@Id
	private Long id;
	
}
