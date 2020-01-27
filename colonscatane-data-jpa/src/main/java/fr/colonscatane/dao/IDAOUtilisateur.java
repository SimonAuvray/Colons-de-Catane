package fr.colonscatane.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;
import fr.colonscatane.modele.Utilisateur;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer>{
	
	public Optional<Utilisateur> findByUsernameAndPassword(String username, String password);
	
	public Optional<Utilisateur> findByUsername(String username);
	
	
	
	public Optional<Utilisateur> findByNom(String nom);

}
