package com.nanuvem.metagui.server.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static <T extends EntityType, R extends JpaRepository<T, Long>> long deploy(String resource,
			Class<T> clazz, Class<R> repositoryType) {

		JpaRepository<?, ?> repository = (JpaRepository<?, ?>) applicationContext
				.getBean(repositoryType);

		EntityTypeRepository entityTypeRepository = getEntityTypeRepository();
		EntityTypeDomain entityType = EntityTypeDomain
				.entityTypeFromClass(resource, clazz);

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
		List<EntityTypeDomain> entitiesTypeDomain = getEntityTypeRepository()
				.findByResource(resource);
		if (entitiesTypeDomain.size() > 0) {
			return entitiesTypeDomain.get(0);
		}
		return null;
	}

	public static <T extends EntityType> T saveInstance(String resource, T instance) {
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories
				.get(resource);
		return repository.saveAndFlush(instance);
	}

	public static <T extends EntityType> List<T> getInstances(String resource) {
		JpaRepository<T, ?> repository = (JpaRepository<T, ?>) repositories
				.get(resource);
		return repository.findAll();
	}

	public static <T extends EntityType> T getInstance(String resource, Long id) {
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
