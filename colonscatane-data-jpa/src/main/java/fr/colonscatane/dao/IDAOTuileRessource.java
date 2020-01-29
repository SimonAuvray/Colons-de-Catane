package fr.colonscatane.dao;

import java.util.List;
import java.util.Optional;

import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypeTuile;

public interface IDAOTuileRessource extends IDAOPositionPlateau<TuileRessource> {

	public List<TuileRessource> findByTypeRessource(TypeTuile typeRessource);
	
	public Optional<List<TuileRessource>> findByNumero(int numero);

}
