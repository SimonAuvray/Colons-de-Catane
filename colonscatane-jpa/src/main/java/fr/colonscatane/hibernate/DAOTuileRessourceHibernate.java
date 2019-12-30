package fr.colonscatane.hibernate;

import java.util.List;

import javax.persistence.Query;

import fr.colonscatane.modele.PositionPlateau;
import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypeTuile;

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
	
	
	public List<TuileRessource> findByRessource (TypeTuile type) {
		try {
			Query myQuery = em.createQuery("select p from PositionPlateau p where typeRessource =:type ");
			
			myQuery.setParameter("type", type);
			
			List<TuileRessource> mesPos = myQuery.getResultList();
			
			for (PositionPlateau p : mesPos) {
				System.out.println("La classe : "+p.getClass().getSimpleName());}
				
				
				
				
				
				return mesPos;
			
			}
			
			catch (Exception e) {
				System.out.println("erreur");
				e.printStackTrace();
				return null;
			}
			
			
			
		}
}
