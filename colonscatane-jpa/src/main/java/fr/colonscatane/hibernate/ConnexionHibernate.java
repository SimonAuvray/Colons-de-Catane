package fr.colonscatane.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnexionHibernate {
	protected static EntityManagerFactory emf = null;
	protected EntityManager em = null;
	
	
	public ConnexionHibernate() {
		if (emf == null) { //Création de EMF si non existant
			emf = Persistence.createEntityManagerFactory("ColonCatane");
		}
		
		if (emf != null) { //Création de EM pour chaque instance
			this.em = emf.createEntityManager();
		}
	}
	
	public static void close() {
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}
}
