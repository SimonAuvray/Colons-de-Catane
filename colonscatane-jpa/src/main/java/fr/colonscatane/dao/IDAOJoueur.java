package fr.colonscatane.dao;

import fr.colonscatane.modele.Couleur;
import fr.colonscatane.modele.Joueur;

public interface IDAOJoueur extends IDAO <Joueur, Couleur> {

	public void deleteAll ();

	Joueur findById(Couleur id);

	Joueur save(Joueur entity);

	void delete(Joueur entity);

	void deleteById(Couleur id);
	
}
