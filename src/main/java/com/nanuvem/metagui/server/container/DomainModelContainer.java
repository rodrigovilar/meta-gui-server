package com.nanuvem.metagui.server.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.annotations.EntityType;

@SuppressWarnings("unchecked")
@Component
public class DomainModelContainer {

	private static Map<EntityTypeDomain, JpaRepository<?, ?>> repositories = new HashMap<EntityTypeDomain, JpaRepository<?,?>>();
	
	@Autowired
	private static ApplicationContext applicationContext;

	@Autowired
	private static EntityManagerFactory entityManagerFactory;

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		DomainModelContainer.applicationContext = applicationContext;

	}

	public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		DomainModelContainer.entityManagerFactory = entityManagerFactory;
	}
	
	public static <T> long deploy(Class<T> clazz) {
		EntityType annotation = clazz.getAnnotation(EntityType.class);
		String resource = annotation.resource();

		JpaRepository<T, ?> repository = new SimpleJpaRepository<T,Serializable>(clazz, entityManagerFactory.createEntityManager());
		
		EntityTypeRepository entityTypeRepository = getEntityTypeRepository();

		EntityTypeDomain entityType = EntityTypeDomain
				.entityTypeFromClass(clazz);
		entityType = entityTypeRepository.save(entityType);
		repositories.put(entityType, repository);
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

	public static <T> void addInstance(Long entityTypeId, T instance) {
		EntityTypeDomain entityTypeDomain = getEntityTypeRepository().findOne(entityTypeId);
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories.get(entityTypeDomain);
		repository.save(instance);
	}

	public static <T> List<T> getInstances(Long entityTypeId) {
		EntityTypeDomain entityTypeDomain = getEntityTypeRepository().findOne(entityTypeId);
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories.get(entityTypeDomain);
		return repository.findAll();
	}

	public static void clear() {
		getEntityTypeRepository().deleteAll();
		repositories.clear();
	}


}
