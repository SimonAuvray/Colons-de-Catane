package fr.colonscatane.dao;

import fr.colonscatane.modele.Couleur;
import fr.colonscatane.modele.Joueur;

public interface IDAOJoueur extends IDAO <Joueur, Couleur> {

	public void deleteAll ();
	
}
