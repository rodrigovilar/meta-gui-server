package com.nanuvem.metagui.server.entitytype;

import static com.nanuvem.metagui.server.entitytype.TestHelper.entityType;
import static com.nanuvem.metagui.server.entitytype.TestHelper.get;
import static com.nanuvem.metagui.server.entitytype.TestHelper.getDate;
import static com.nanuvem.metagui.server.entitytype.TestHelper.instance;
import static com.nanuvem.metagui.server.entitytype.TestHelper.objectToMap;
import static com.nanuvem.metagui.server.entitytype.TestHelper.post;
import static com.nanuvem.metagui.server.entitytype.TestHelper.propertyType;
import static com.nanuvem.metagui.server.entitytype.TestHelper.put;
import static com.nanuvem.metagui.server.entitytype.TestHelper.delete;
import static com.nanuvem.metagui.server.entitytype.TestHelper.getObjectFromResult;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

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
import org.springframework.test.web.servlet.MvcResult;

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
		DomainModelContainer.setEntityManagerFactory(applicationContext.getBean(EntityManagerFactory.class));
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

		get(mockMvc, "/api/" + "customer").andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeWithPropertiesGetEntities() throws Exception {
		int id = (int) DomainModelContainer.deploy(CustomerDetails.class);

		get(mockMvc, "/entities").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(entityType(0, id, "CustomerDetails"));

		get(mockMvc, "/api/" + "customerDetails").andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntity() throws Exception {
		int id = (int) DomainModelContainer.deploy(CustomerDetails.class);

		get(mockMvc, "/entities/" + id).andExpect(status().isOk())
				.andExpect(entityType(id, "CustomerDetails"))
				.andExpect(propertyType(0, "id", PropertyTypeType.integer))
				.andExpect(propertyType(1, "ssn", PropertyTypeType.string))
				.andExpect(propertyType(2, "name", PropertyTypeType.string))
				.andExpect(propertyType(3, "birthdate", PropertyTypeType.date))
				.andExpect(propertyType(4, "credit", PropertyTypeType.real));

		get(mockMvc, "/api/" + "customerDetails").andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOperationalCRUD() throws Exception {
		DomainModelContainer.deploy(CustomerDetails.class);

		CustomerDetails customer = new CustomerDetails();
		String name = "FooName";
		String ssn = null;
		Date birthdate = getDate(2015, 01, 01, 0, 0, 0);
		int credit = 0;
		customer.setName(name);
		customer.setSsn(ssn);
		customer.setBirthdate(birthdate);
		customer.setCredit(credit);
		
		Map<String, Object> instanceMap = objectToMap(customer);
		instanceMap.put("birthdate", birthdate.getTime());
		
		MvcResult result = post(mockMvc, "/api/" + "customerDetails", customer).andExpect(status().isCreated())
				.andExpect(instance(instanceMap)).andReturn();
		customer = getObjectFromResult(result, CustomerDetails.class);
		
		name = "OtherFooName";
		customer.setName(name);
		
		instanceMap = objectToMap(customer);
		
		put(mockMvc, "/api/" + "customerDetails/" + customer.getId(), customer).andExpect(status().isCreated())
		.andExpect(instance(instanceMap));
		
		get(mockMvc, "/api/" + "customerDetails/" + customer.getId()).andExpect(status().isOk()).andExpect(
				instance(instanceMap));
		
		delete(mockMvc, "/api/" + "customerDetails/" + customer.getId()).andExpect(status().isOk());
		
		get(mockMvc, "/api/" + "customerDetails").andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));
		
		get(mockMvc, "/api/" + "customerDetails/" + customer.getId()).andExpect(status().isNotFound());
		
	}

}
