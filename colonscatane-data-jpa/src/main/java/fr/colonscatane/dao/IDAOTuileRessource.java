package fr.colonscatane.dao;

import java.util.List;

import fr.colonscatane.modele.TuileRessource;
import fr.colonscatane.modele.TypeTuile;

public interface IDAOTuileRessource extends IDAOPositionPlateau<TuileRessource> {

	List<TuileRessource> findByTypeRessource(TypeTuile typeRessource);

}
