package fr.colonscatane.hibernate;

import fr.colonscatane.modele.Coin;
import fr.colonscatane.modele.PositionPlateau;


public class DAOCoinHibernate extends DAOPositionPlateauHibernate{
	
	public void delete(Coin entity) {
		try {
			em.getTransaction().begin(); 
			em.remove(em.merge(entity));
			em.getTransaction().commit(); 
		}
		
		catch (Exception e) {
			System.out.println("Erreur lors de la suppression");
			e.printStackTrace();
			em.getTransaction().rollback(); 
			
		}
	}
	
	public void deleteById(Integer id) {
		PositionPlateau laPositionASupprimer = new Coin();
		
		laPositionASupprimer.setId(id);
		delete(laPositionASupprimer);
	}


}
