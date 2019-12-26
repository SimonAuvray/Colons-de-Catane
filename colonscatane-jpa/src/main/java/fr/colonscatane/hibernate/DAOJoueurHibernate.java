package fr.colonscatane.hibernate;

import java.util.List;

import fr.colonscatane.dao.IDAOJoueur;
import fr.colonscatane.modele.Couleur;
import fr.colonscatane.modele.Joueur;



public class DAOJoueurHibernate extends ConnexionHibernate implements IDAOJoueur {

	@Override
	public List<Joueur> findAll() {
		return em.createQuery("select j from Joueur j", Joueur.class)
		.getResultList();
	}

	@Override
	public Joueur findById(Couleur id) {
		return em.find(Joueur.class, id);
	}

	@Override
	public Joueur save(Joueur entity) {
		em.getTransaction().begin(); 
		
		try {
			if (entity.getCouleur() == null) { 
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
	public void delete(Joueur entity) {
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

	@Override
	public void deleteById(Couleur id) {
		Joueur leJoueurASupprimer = new Joueur();
		
		leJoueurASupprimer.setCouleur(id);
		delete(leJoueurASupprimer);
	}

	@Override
	public void deleteAll() {
		try {
		deleteById(Couleur.ROUGE);
		deleteById(Couleur.NOIR);
		deleteById(Couleur.ROUGE);
		deleteById(Couleur.VERT);
		}
		catch (Exception e) {
		}
	}
		
		
}


