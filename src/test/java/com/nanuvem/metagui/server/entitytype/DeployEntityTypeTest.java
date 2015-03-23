package com.nanuvem.metagui.server.entitytype;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nanuvem.metagui.server.MetaGuiEntryPoint;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.controller.EntityTypeRest;
import com.nanuvem.metagui.server.controller.MetadataEntityTypeController;
import com.nanuvem.metagui.server.controller.OperationalController;
import com.nanuvem.metagui.server.controller.PropertyTypeType;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MetaGuiEntryPoint.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeployEntityTypeTest {

	@Autowired
	MetadataEntityTypeController controller;

	@Autowired
	OperationalController operationalController;


	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntities() {
		DomainModelContainer.deploy(Customer.class);
		
		EntityTypeRest expected = TestHelper.createEntityTypeRest("Customer");
		List<EntityTypeRest> entities = controller.getEntities();
		
		assertEquals(1, entities.size());
		assertEquals(expected, entities.get(0));
		
		assertEquals("[]", operationalController.getAll());
	}
	
	@DirtiesContext
	@Test
	public void testOneEntityTypeWithPropertiesGetEntities() {
		DomainModelContainer.deploy(CustomerDetails.class);
		
		EntityTypeRest expected = TestHelper.createEntityTypeRest("Customer");
		List<EntityTypeRest> entities = controller.getEntities();
		
		assertEquals(1, entities.size());
		assertEquals(expected, entities.get(0));

		assertEquals("[]", operationalController.getAll());
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntity() {
		long id = DomainModelContainer.deploy(CustomerDetails.class);
		
		EntityTypeRest expected = TestHelper.createEntityTypeRest("Customer");
		TestHelper.addPropertyTypeRest(expected, "name", PropertyTypeType.string);
		TestHelper.addPropertyTypeRest(expected, "ssn", PropertyTypeType.string);
		TestHelper.addPropertyTypeRest(expected, "birthdate", PropertyTypeType.date);
		TestHelper.addPropertyTypeRest(expected, "credit", PropertyTypeType.real);
		
		EntityTypeRest entity = controller.getEntity(id);
		
		assertEquals(expected, entity);
		
		assertEquals("[]", operationalController.getAll());
	}

	@DirtiesContext
	@Test
	public void testOperationalCRUD() {
		DomainModelContainer.deploy(CustomerDetails.class);
		
		operationalController.create("{\"name\":\"John\", \"ssn\":\"123\"}");
		assertEquals("{\"name\":\"John\", \"ssn\":\"123\"}", operationalController.getAll());
	}

}
