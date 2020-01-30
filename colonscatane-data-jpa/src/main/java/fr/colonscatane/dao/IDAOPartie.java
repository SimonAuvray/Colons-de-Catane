package fr.colonscatane.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.colonscatane.modele.Partie;

public interface IDAOPartie extends JpaRepository<Partie, Integer>{
	
	@Query("select p from Partie p left join p.lstJoueurs j where p.id=?1")
	public Partie findByIdFetchingJoueurs(Integer id);
}
