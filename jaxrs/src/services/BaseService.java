package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class BaseService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("application");
	
	protected EntityManager getEntityManager() {
		
		return emf.createEntityManager();
		
	}
}
