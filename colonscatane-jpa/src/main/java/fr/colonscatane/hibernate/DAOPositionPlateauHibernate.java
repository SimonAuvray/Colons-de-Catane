package fr.colonscatane.hibernate;

import java.util.List;

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
	public PositionPlateau findByType(PositionPlateau type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
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
