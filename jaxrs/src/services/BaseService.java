package services;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


public abstract class BaseService {

	protected EntityManager getEntityManager() {
		
		return Persistence.createEntityManagerFactory("application").createEntityManager();
		
	}
}
