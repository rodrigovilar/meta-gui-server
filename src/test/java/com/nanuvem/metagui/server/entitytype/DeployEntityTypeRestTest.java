package com.nanuvem.metagui.server.entitytype;

import static com.nanuvem.metagui.server.util.TestHelper.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.nanuvem.metagui.server.api.Cardinality.One;
import static com.nanuvem.metagui.server.api.Cardinality.Many;

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
	}

	@After
	public void tearDown() {
		DomainModelContainer.clear();
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntities() throws Exception {
		int id = DomainModelContainer.deploy(Customer.class).get(0);

		get(mockMvc, "/entities").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(entityType(0, id, "Customer"));

		get(mockMvc, "/api/" + "customer").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testCompositionRelationship() throws Exception {
		List<Integer> ids = DomainModelContainer.deploy(Client.class,
				Dependent.class);
		int idClient = ids.get(0);
		int idDependent = ids.get(1);

		get(mockMvc, "/entities").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(entityType(0, idClient, "Client"))
				.andExpect(entityType(1, idDependent, "Dependent"));

		get(mockMvc, "/entities/" + idClient).andExpect(status().isOk())
				.andExpect(entityType(idClient, "Client"))
				.andExpect(propertyType(0, "id", PropertyTypeType.integer))
				.andExpect(propertyType(1, "name", PropertyTypeType.string))
				.andExpect(relationshipType(0, "dependents", "Dependent", idDependent, One, Many));

		get(mockMvc, "/entities/" + idDependent).andExpect(status().isOk())
				.andExpect(entityType(idDependent, "Dependent"))
				.andExpect(propertyType(0, "id", PropertyTypeType.integer))
				.andExpect(propertyType(1, "name", PropertyTypeType.string))
				.andExpect(propertyType(2, "age", PropertyTypeType.integer))
				.andExpect(relationshipType(0, "client", "Client", idClient, Many, One));

		get(mockMvc, "/api/" + "clients").andExpect(status().isOk()).andExpect(
				jsonPath("$", hasSize(0)));

		get(mockMvc, "/api/" + "dependents").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeWithPropertiesGetEntities() throws Exception {
		int id = DomainModelContainer.deploy(CustomerDetails.class).get(0);

		get(mockMvc, "/entities").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(entityType(0, id, "CustomerDetails"));

		get(mockMvc, "/api/" + "customerDetails").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOneEntityTypeGetEntity() throws Exception {
		int id = DomainModelContainer.deploy(CustomerDetails.class).get(0);

		get(mockMvc, "/entities/" + id).andExpect(status().isOk())
				.andExpect(entityType(id, "CustomerDetails"))
				.andExpect(propertyType(0, "id", PropertyTypeType.integer))
				.andExpect(propertyType(1, "ssn", PropertyTypeType.string))
				.andExpect(propertyType(2, "name", PropertyTypeType.string))
				.andExpect(propertyType(3, "birthdate", PropertyTypeType.date))
				.andExpect(propertyType(4, "credit", PropertyTypeType.real));

		get(mockMvc, "/api/" + "customerDetails").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@DirtiesContext
	@Test
	public void testOperationalCRUD() throws Exception {
		DomainModelContainer.deploy(CustomerDetails.class);

		String name = "FooName";
		String ssn = null;
		Date birthdate = getDate(2015, 01, 01, 0, 0, 0);
		CustomerDetails customer = createCustomerDetails(name, ssn, birthdate,
				0);

		Map<String, Object> instanceMap = objectToMap(customer);
		instanceMap.remove("id");
		instanceMap.put("birthdate", birthdate.getTime());

		MvcResult result = post(mockMvc, "/api/" + "customerDetails", customer)
				.andExpect(status().isCreated())
				.andExpect(instance(instanceMap)).andReturn();
		customer = getObjectFromResult(result, CustomerDetails.class);

		name = "OtherFooName";
		customer.setName(name);

		instanceMap = objectToMap(customer);
		instanceMap.put("id", customer.getId().intValue());
		instanceMap.put("birthdate", birthdate.getTime());

		result = put(mockMvc, "/api/" + "customerDetails/" + customer.getId(),
				customer).andExpect(status().isCreated())
				.andExpect(instance(instanceMap)).andReturn();
		customer = getObjectFromResult(result, CustomerDetails.class);

		get(mockMvc, "/api/" + "customerDetails/" + customer.getId())
				.andExpect(status().isOk()).andExpect(instance(instanceMap));

		delete(mockMvc, "/api/" + "customerDetails/" + customer.getId())
				.andExpect(status().isOk());

		get(mockMvc, "/api/" + "customerDetails").andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		get(mockMvc, "/api/" + "customerDetails/" + customer.getId())
				.andExpect(status().isNotFound());

	}

	private CustomerDetails createCustomerDetails(String name, String ssn,
			Date birthdate, int credit) {
		CustomerDetails customer = new CustomerDetails();
		customer.setName(name);
		customer.setSsn(ssn);
		customer.setBirthdate(birthdate);
		customer.setCredit(credit);
		return customer;
	}

}
