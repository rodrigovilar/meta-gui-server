package com.nanuvem.metagui.server.entitytype;

import static com.nanuvem.metagui.server.entitytype.TestHelper.entityType;
import static com.nanuvem.metagui.server.entitytype.TestHelper.get;
import static com.nanuvem.metagui.server.entitytype.TestHelper.instance;
import static com.nanuvem.metagui.server.entitytype.TestHelper.objectToMap;
import static com.nanuvem.metagui.server.entitytype.TestHelper.post;
import static com.nanuvem.metagui.server.entitytype.TestHelper.propertyType;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Date;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nanuvem.metagui.server.MetaGuiEntryPoint;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.controller.MetadataEntityTypeController;
import com.nanuvem.metagui.server.controller.OperationalController;
import com.nanuvem.metagui.server.controller.PropertyTypeType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MetaGuiEntryPoint.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeployEntityTypeRestTest {

	MockMvc mockMvc;

	@InjectMocks
	MetadataEntityTypeController metadataController;

	@InjectMocks
	OperationalController operationalController;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(metadataController,
				operationalController).build();
		
		DomainModelContainer.setApplicationContext(applicationContext);
	}

	@After
	public void tearDown() {
		DomainModelContainer.clear();
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntities() throws Exception {
		int id = (int) DomainModelContainer.deploy(Customer.class);

		get(mockMvc, "/entities").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(entityType(0, id, "Customer"));

		get(mockMvc, "/api/" + id).andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeWithPropertiesGetEntities() throws Exception {
		int id = (int) DomainModelContainer.deploy(CustomerDetails.class);

		get(mockMvc, "/entities").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(entityType(0, id, "CustomerDetails"));

		get(mockMvc, "/api/" + id).andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntity() throws Exception {
		int id = (int) DomainModelContainer.deploy(CustomerDetails.class);

		get(mockMvc, "/entities/" + id).andExpect(status().isOk())
				.andExpect(entityType(id, "CustomerDetails"))
				.andExpect(propertyType(0, "ssn", PropertyTypeType.string))
				.andExpect(propertyType(1, "name", PropertyTypeType.string))
				.andExpect(propertyType(2, "birthdate", PropertyTypeType.date))
				.andExpect(propertyType(3, "credit", PropertyTypeType.real));

		get(mockMvc, "/api/" + id).andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOperationalCRUD() throws Exception {
		int id = (int) DomainModelContainer.deploy(CustomerDetails.class);

		CustomerDetails customer = new CustomerDetails();
		String name = "FooName";
		String ssn = "00000000000";
		Date birthdate = new Date();
		int credit = 0;
		customer.setName(name);
		customer.setSsn(ssn);
		customer.setBirthdate(birthdate);
		customer.setCredit(credit);
		
		Map<String, Object> instanceMap = objectToMap(customer);
		instanceMap.put("birthdate", birthdate.getTime());
		
		post(mockMvc, "/api/" + id, customer).andExpect(status().isCreated())
				.andExpect(instance(instanceMap));
		//
		// TODO operationalController.create(id,
		// "{\"name\":\"John\", \"ssn\":\"123\"}");
		// assertEquals("[{\"name\":\"John\",\"ssn\":\"123\",\"credit\":0.0}]",
		// operationalController.getAll(id));
	}

}
