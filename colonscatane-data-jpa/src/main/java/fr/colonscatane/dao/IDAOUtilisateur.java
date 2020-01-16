package fr.colonscatane.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.colonscatane.modele.Utilisateur;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer>{
	
	public Utilisateur findByUsernameAndPassword(String username, String password);

}
