package com.techstack.pms.dao.jpa.interceptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import com.techstack.component.spring.context.SpringApplicationContextAware;

/**
 * 仿照OpenEntityManagerInViewFilter实现
 * 
 * @author asus
 * 
 */
public class OpenEntityManagerInterceptor implements MethodInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(OpenEntityManagerInterceptor.class);

	public static final String DEFAULT_ENTITY_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory";

	private String entityManagerFactoryBeanName;

	private String persistenceUnitName;

	private volatile EntityManagerFactory entityManagerFactory;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		EntityManagerFactory emf = lookupEntityManagerFactory();
		boolean participate = false;

		if (TransactionSynchronizationManager.hasResource(emf)) {
			// Do not modify the EntityManager: just set the participate flag.
			participate = true;
		} else {
			logger.debug("Opening JPA EntityManager in OpenEntityManagerInViewFilter");
			try {
				EntityManager em = createEntityManager(emf);
				TransactionSynchronizationManager.bindResource(emf,new EntityManagerHolder(em));
			} catch (PersistenceException ex) {
				throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
			}
		}

		try {
			Object result = invocation.proceed();
			return result;
		} finally {
			if (!participate) {
				EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(emf);
				logger.debug("Closing JPA EntityManager in OpenEntityManagerInViewFilter");
				EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
			}
		}
	}

	public void setEntityManagerFactoryBeanName(
			String entityManagerFactoryBeanName) {
		this.entityManagerFactoryBeanName = entityManagerFactoryBeanName;
	}

	protected String getEntityManagerFactoryBeanName() {
		return this.entityManagerFactoryBeanName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	protected String getPersistenceUnitName() {
		return this.persistenceUnitName;
	}
	
	protected EntityManagerFactory lookupEntityManagerFactory() {
		if (this.entityManagerFactory == null) {
			//WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			ApplicationContext ctx = SpringApplicationContextAware.getApplicationContext();
			String emfBeanName = getEntityManagerFactoryBeanName();
			String puName = getPersistenceUnitName();
			if (StringUtils.hasLength(emfBeanName)) {
				this.entityManagerFactory = ctx.getBean(emfBeanName, EntityManagerFactory.class);
			}
			else if (!StringUtils.hasLength(puName) && ctx.containsBean(DEFAULT_ENTITY_MANAGER_FACTORY_BEAN_NAME)) {
				this.entityManagerFactory = ctx.getBean(DEFAULT_ENTITY_MANAGER_FACTORY_BEAN_NAME, EntityManagerFactory.class);
			}
			else {
				// Includes fallback search for single EntityManagerFactory bean by type.
				this.entityManagerFactory = EntityManagerFactoryUtils.findEntityManagerFactory(ctx, puName);
			}
		}
		return this.entityManagerFactory;
	}
	
	protected EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

}
