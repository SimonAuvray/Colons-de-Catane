package fr.colonscatane.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import fr.colonscatane.modele.Joueur;
import fr.colonscatane.modele.ROLE;

public interface IDAOJoueur extends IDAOUtilisateur<Joueur> {
	@Query("Select j from Joueur j left join fetch j.occupations o where j.id=?1")
	public Optional<Joueur> findByIdFetchingPosition(int id);
	public Optional<List<Joueur>> findByRole( ROLE role);
}
