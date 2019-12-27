package fr.colonscatane.hibernate;

import java.util.List;

import javax.persistence.Query;

import fr.colonscatane.dao.IDAOPositionPlateau;
import fr.colonscatane.modele.PositionPlateau;

public class DAOPositionPlateauHibernate extends ConnexionHibernate implements IDAOPositionPlateau {

	@Override
	public List<PositionPlateau> findAll() {
		return em.createQuery("select j from Joueur j", PositionPlateau.class)
				.getResultList();
			}

	@Override
	public PositionPlateau findById(Integer id) {
		return em.find(PositionPlateau.class, id);
	}

	@Override
	public PositionPlateau save(PositionPlateau entity) {
		em.getTransaction().begin(); 
		
		try {
			if (entity.getId() == 0) { 
				em.persist(entity);
			}
			
			else {
				entity = em.merge(entity);
			}
		
			em.getTransaction().commit(); 
		}
		
		catch (Exception e) {
			System.out.println("Erreur lors de la sauvegarde");
			
			e.printStackTrace();
			em.getTransaction().rollback(); 
			
		}
		
		return entity;
	}


	



	@Override
	public PositionPlateau findByXY(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PositionPlateau> findByType(int type) {
		// TODO Auto-generated method stub
		return em.createQuery("select p from PositionPlateau p ", PositionPlateau.class).getResultList();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
		try {
			em.getTransaction().begin();
			Query query1 = em.createQuery("DELETE FROM LIENS");
			query1.executeUpdate();
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().rollback();
		}
		
		try {
			em.getTransaction().begin();
			Query query2 = em.createQuery("DELETE FROM PositionPlateau");			
			query2.executeUpdate();
			em.getTransaction().commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		try {
			em.getTransaction().begin();
			Query query3 = em.createNativeQuery("ALTER TABLE position_plateau AUTO_INCREMENT=1");
			query3.executeUpdate();
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().rollback();
		}
	}

	@Override
	public void delete(PositionPlateau entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
