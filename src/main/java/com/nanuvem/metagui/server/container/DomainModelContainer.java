package com.nanuvem.metagui.server.container;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.EntityType;


@SuppressWarnings("unchecked")
@Component
public class DomainModelContainer {

	private static Map<String, JpaRepository<?, ?>> repositories = new HashMap<String, JpaRepository<?, ?>>();

	@Autowired
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		DomainModelContainer.applicationContext = applicationContext;

	}

	public static List<Integer> deploy(Class<?>... classes) {
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Class<?> clazz : classes) {
			ids.add(deployEntities(clazz));
		}

		for (Class<?> clazz : classes) {
			deployRelationships(clazz);
		}

		return ids;
	}
	
	private static <T> int deployEntities(Class<T> clazz) {
		
		EntityType entityType = clazz.getAnnotation(EntityType.class);
		String resource = entityType.resource();
		Class<? extends JpaRepository<?, ?>> repositoryType = entityType.repositoryType();
		
		JpaRepository<?, ?> repository = (JpaRepository<?, ?>) applicationContext.getBean(repositoryType);

		EntityTypeRepository entityTypeRepository = getEntityTypeRepository();
		EntityTypeDomain entityTypeDomain = EntityTypeDomain.entityTypeFromClass(resource, clazz);

		entityTypeDomain = entityTypeRepository.save(entityTypeDomain);
		repositories.put(resource, repository);
		return entityTypeDomain.getId();
	}

	private static <T> void deployRelationships(Class<T> clazz) {
		
	}

	public static EntityTypeRepository getEntityTypeRepository() {
		return applicationContext.getBean(EntityTypeRepository.class);
	}

	public static Iterable<EntityTypeDomain> getDomains() {
		return getEntityTypeRepository().findAll();
	}

	public static EntityTypeDomain getDomain(Integer id) {
		return getEntityTypeRepository().findOne(id);
	}

	public static EntityTypeDomain getDomain(String resource) {
		EntityTypeDomain entityTypeDomain = 
				getEntityTypeRepository().findByResource(resource);
		return entityTypeDomain;
	}

	public static <T> T saveInstance(String resource, T instance) {
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories
				.get(resource);
		return repository.saveAndFlush(instance);
	}

	public static <T> T saveInstance(Object id, String resource, T instance) {
		setId(id, instance);
		return saveInstance(resource, instance);
	}

	private static <T> void setId(Object id, T instance) {
		Class<?> instanceClazz = instance.getClass();
		
		try {
			for (Field field : instanceClazz.getFields()) {
				if (field.getAnnotation(Id.class) != null) {
					field.set(instance, id);
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static <T> List<T> getInstances(String resource) {
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories
				.get(resource);
		return repository.findAll();
	}

	public static <T> T getInstance(String resource, Long id) {
		JpaRepository<T, Long> repository = (JpaRepository<T, Long>) repositories
				.get(resource);
		T instance = repository.findOne(id);
		return instance;
	}
	
	public static boolean deleteInstance(String resource, Long id) {
		JpaRepository<?, Long> repository = (JpaRepository<?, Long>) repositories
				.get(resource);
		if(!repository.exists(id)){
			return false;
		}
		repository.delete(id);
		return true;
	}

	public static void clear() {
		getEntityTypeRepository().deleteAll();
		repositories.clear();
	}

}
