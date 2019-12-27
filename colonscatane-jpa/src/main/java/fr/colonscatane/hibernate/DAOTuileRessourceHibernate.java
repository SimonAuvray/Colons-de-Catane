package fr.colonscatane.hibernate;

import java.util.List;

import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TuileRessource;

public class DAOTuileRessourceHibernate extends DAOPositionPlateauHibernate{
	
	public void delete(TuileRessource entity) {
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
		PositionPlateau laPositionASupprimer = new TuileRessource();
		
		laPositionASupprimer.setId(id);
		delete(laPositionASupprimer);
	}
}
