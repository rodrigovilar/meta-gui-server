package com.nanuvem.metagui.server.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.annotations.EntityType;

@SuppressWarnings("unchecked")
@Component
public class DomainModelContainer {

	private static Map<String, JpaRepository<?, ?>> repositories = new HashMap<String, JpaRepository<?,?>>();
	
	@Autowired
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		DomainModelContainer.applicationContext = applicationContext;

	}
	
	public static <T> long deploy(Class<T> clazz) {
		EntityType annotation = clazz.getAnnotation(EntityType.class);
		String resource = annotation.resource();
		Class<?> repositoryType = annotation.repository();
		
		JpaRepository<?, ?> repository = 
				(JpaRepository<?, ?>) applicationContext.getBean(repositoryType);
		
		
		EntityTypeRepository entityTypeRepository = getEntityTypeRepository();
		EntityTypeDomain entityType = 
				EntityTypeDomain.entityTypeFromClass(clazz);
		
		entityType = entityTypeRepository.save(entityType);
		repositories.put(resource, repository);
		return entityType.getId();
	}

	public static EntityTypeRepository getEntityTypeRepository() {
		return applicationContext.getBean(EntityTypeRepository.class);
	}

	public static Iterable<EntityTypeDomain> getDomains() {
		return getEntityTypeRepository().findAll();
	}

	public static EntityTypeDomain getDomain(Long id) {
		return getEntityTypeRepository().findOne(id);
	}
	
	public static EntityTypeDomain getDomain(String resource) {
		List<EntityTypeDomain> entitiesTypeDomain = getEntityTypeRepository().findByResource(resource);
		if(entitiesTypeDomain.size() > 0) {
			return entitiesTypeDomain.get(0);
		}
		return null;
	}

	public static <T> T addInstance(String resource, T instance) {
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories.get(resource);
		return repository.save(instance);
	}

	public static <T> List<T> getInstances(String resource) {
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories.get(resource);
		return repository.findAll();
	}
	
	public static <T, U extends Serializable> T updateInstance(String resource, T newInstance, U id) {
		JpaRepository<T, U> repository = (JpaRepository<T, U>) repositories.get(resource);
		T instance = repository.findOne(id);
		if(instance != null)
			return repository.save(newInstance);
		return null;
	}

	public static void clear() {
		getEntityTypeRepository().deleteAll();
		repositories.clear();
	}


}
