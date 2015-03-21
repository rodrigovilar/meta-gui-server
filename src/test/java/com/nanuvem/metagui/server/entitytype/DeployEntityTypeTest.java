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
import com.nanuvem.metagui.server.controller.EntityType;
import com.nanuvem.metagui.server.controller.MetadataEntityTypeController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MetaGuiEntryPoint.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeployEntityTypeTest {

	@Autowired
	MetadataEntityTypeController controller;



	@DirtiesContext
	@Test
	public void testOneEntityType() {
		DomainModelContainer.deploy(Customer.class);
		
		EntityType expected = new EntityType();
		expected.setName("Customer");
		
		List<EntityType> entities = controller.getEntities();
		
		assertEquals(1, entities.size());
		assertEquals(expected, entities.get(0));

	}


}
