package fr.colonscatane.dao;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.colonscatane.modele.Utilisateur;

@Primary
public interface IDAOUtilisateur<T extends Utilisateur> extends JpaRepository<T, Integer>{
	
	public Optional<T> findByUsernameAndPassword(String username, String password);
	
	public Optional<T> findByUsername(String username);
	
	
	
	public Optional<T> findByNom(String nom);

}
