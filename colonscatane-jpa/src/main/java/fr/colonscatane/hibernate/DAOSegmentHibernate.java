package fr.colonscatane.hibernate;

import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.Segment;

public class DAOSegmentHibernate extends DAOPositionPlateauHibernate{
	
	public void delete(PositionPlateau entity) {
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
		PositionPlateau laPositionASupprimer = new Segment();
		
		laPositionASupprimer.setId(id);
		delete(laPositionASupprimer);
	}

}
