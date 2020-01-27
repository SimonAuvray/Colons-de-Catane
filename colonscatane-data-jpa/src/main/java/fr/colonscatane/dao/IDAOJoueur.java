package fr.colonscatane.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;

public interface IDAOJoueur extends JpaRepository<Joueur, Integer>{
	
	
	
	public Optional<List<Joueur>> findByRole( ROLE role);
	
	public Optional<Joueur> findByNom (String nom);

}
