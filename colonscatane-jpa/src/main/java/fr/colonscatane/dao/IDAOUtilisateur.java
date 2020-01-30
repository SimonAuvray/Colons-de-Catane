package fr.colonscatane.dao;

import fr.colonscatane.exception.NoUserFoundException;
import fr.colonscatane.modele.Utilisateur;

public interface IDAOUtilisateur extends IDAO<Utilisateur, Integer>{
	
	
	public Utilisateur findByIdentifiants (String username, String password) throws Exception;

	
	
}
