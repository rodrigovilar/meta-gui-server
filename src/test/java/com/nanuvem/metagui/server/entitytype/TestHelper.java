package com.nanuvem.metagui.server.entitytype;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nanuvem.metagui.server.controller.EntityTypeRest;
import com.nanuvem.metagui.server.controller.PropertyTypeRest;
import com.nanuvem.metagui.server.controller.PropertyTypeType;

public class TestHelper {

	public static EntityTypeRest createEntityTypeRest(long id, String name) {
		EntityTypeRest expected = new EntityTypeRest();
		expected.setName(name);
		expected.setId(id);
		return expected;
	}

	public static void addPropertyTypeRest(EntityTypeRest expected, String name,
			PropertyTypeType type) {
		PropertyTypeRest prop = new PropertyTypeRest();
		prop.setName(name);
		prop.setType(type);
		expected.getProperties().add(prop);
	}

	public static void equals(ResultActions actions, long id, String name) throws Exception {
		actions.andExpect(jsonPath("$[0].id").value(id))
			   .andExpect(jsonPath("$[0].name").value(name));
	}

	public static ResultActions get(MockMvc mockMvc, String url) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON));
	}
	
	public static ResultActions post(MockMvc mockMvc, String url, Object instance) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.post(url)
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(instance)));
	}
	
	public static ResultActions put(MockMvc mockMvc, String url, Object instance) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.put(url)
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(instance)));
	}
	
	public static ResultActions delete(MockMvc mockMvc, String url) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.delete(url)
				.accept(MediaType.APPLICATION_JSON));
	}

	public static ResultMatcher entityType(final int position, final int id, final String name) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				jsonPath("$[" + position + "].id").value(id).match(result);
				jsonPath("$[" + position + "].name").value(name).match(result);
			}
		};
	}

	public static ResultMatcher entityType(final int id, final String name) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				jsonPath("$.id").value(id).match(result);
				jsonPath("$.name").value(name).match(result);
			}
		};
	}

	public static ResultMatcher propertyType(final int propertyTypePosition, final String name,
			final PropertyTypeType type) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				jsonPath("$.properties[" + propertyTypePosition + "].name").value(name).match(result);
				jsonPath("$.properties[" + propertyTypePosition + "].type").value(type.name()).match(result);
			}
		};
	}
	
	public static ResultMatcher instance(final Map<String, Object> instanceMap) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				for(String key : instanceMap.keySet()) {
					Object value = instanceMap.get(key);
					jsonPath("$." + key).value(value).match(result);
				}
			}
		};
	}
	
	public static Map<String, Object> objectToMap(Object obj) throws Exception {
	    Map<String, Object> result = new HashMap<String, Object>();
	    BeanInfo info = Introspector.getBeanInfo(obj.getClass());
	    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
	        Method reader = pd.getReadMethod();
	        if (reader != null && !pd.getName().equals("class"))
	            result.put(pd.getName(), reader.invoke(obj));
	    }
	    return result;
	}
	
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(year, month, day, hour, minute, second);
		return cal.getTime(); 
	}
	
	public static <T> T getObjectFromResult(MvcResult result, Class<T> type) throws JsonSyntaxException, UnsupportedEncodingException {
		return new Gson().fromJson(result.getResponse().getContentAsString(), type);
	}

}
